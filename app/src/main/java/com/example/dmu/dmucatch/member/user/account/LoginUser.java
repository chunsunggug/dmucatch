package com.example.dmu.dmucatch.member.user.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dmu.dmucatch.MainActivity;
import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.db_connect.StringUrl;
import com.example.dmu.dmucatch.member.Userinfo;
import com.example.dmu.dmucatch.utility.StringUtil;
import com.example.dmu.dmucatch.utility.UserCheckUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginUser extends AppCompatActivity {

    Button BtnSignIn, BtnSignUp, BtnFindID;
    EditText inputID, inputPW;
    private String mJsonString;
    // DivUser divUser = new DivUser(); // user || store 체크
    ConnectDB connectdb = new ConnectDB(); // db connection 모음
    StringUrl url = new StringUrl(); // url 모음
    StringUtil strUtil = new StringUtil(); // util 모음
    UserCheckUtil userCkUtil = new UserCheckUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BtnSignUp = (Button)findViewById(R.id.btn_signup);
        BtnSignIn = (Button)findViewById(R.id.btn_signin);
        BtnFindID = (Button)findViewById(R.id.findIdPwd) ;
        inputID = (EditText)findViewById(R.id.user_id);
        inputPW = (EditText)findViewById(R.id.user_pw);

        BtnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();}

        });

        BtnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
        //아이디 비밀번호 찾기 이동
        BtnFindID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindIdPwdUser.class);
                startActivity(intent);
            }
        });
    }

    void login() {
        final String id = inputID.getText().toString();
        final String pwd = inputPW.getText().toString();

        if(strUtil.isNull(id) || strUtil.isNull(pwd)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "아이디/비밀번호를 입력하여 주십시오.", Toast.LENGTH_LONG).show();
                }
            });
        } // null check
        else {
            /*  // user check 없앰
            final String phpVar [] = {"userId", "userPwd", "rbCheck"}; // php로 넘길 post 테그 명
            final String typeData [] = {inputID.getText().toString(), inputPW.getText().toString(), rb.getText().toString()}; // EditText값 배열
            */
            final String phpVar [] = {"userId", "userPwd"}; // php로 넘길 post 테그 명
            final String typeData [] = {inputID.getText().toString(), inputPW.getText().toString()}; // EditText값 배열

            mJsonString = connectdb.selectToJson(url.login, phpVar, typeData); // login url, php 테그 배열, EditText 변수 값 배열

            if(userCkUtil.isNoFoundUser(mJsonString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(mJsonString);
                        Toast.makeText(LoginUser.this, "아이디/비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            } // user not found
            else if(strUtil.isFailed(mJsonString)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(mJsonString);
                        Toast.makeText(LoginUser.this, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            } // network || php error
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("json : "+mJsonString);
                        setData(); // getJsonData
                        Toast.makeText(LoginUser.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity((new Intent(LoginUser.this, MainActivity.class)));
                finish();
            } // login successed
        }
    }

    private void setData(){

        String TAG_JSON="catchMembers"; // Json Tag
        String TAG_SEQ = "user_seq";
        String TAG_ID = "user_id";
        String TAG_NAME = "name";
        String TAG_PHONE = "phone";
        String TAG_REP_ADD_SEQ = "rep_add_seq";
        String TAG_CODE = "class_code";
        // Json 구분자

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // select data count and insert to array , 로그인이라서 어차피 데이터 1개

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                int user_seq = Integer.parseInt(item.getString(TAG_SEQ));
                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String phone = item.getString(TAG_PHONE);
                String address = item.getString(TAG_REP_ADD_SEQ);
                String class_cd = item.getString(TAG_CODE);

                Userinfo userInfo = new Userinfo();
                userInfo.setUser_seq(user_seq);
                userInfo.setId(id);
                userInfo.setName(name);
                userInfo.setPhone(phone);
                userInfo.setAddress(address);
                userInfo.setClass_code(class_cd);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void SignUp()
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}