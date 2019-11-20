package com.example.dmu.dmucatch.member.user.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.db_connect.StringUrl;
import com.example.dmu.dmucatch.utility.DaumAddressApi;
import com.example.dmu.dmucatch.utility.DaumAddressVo;
import com.example.dmu.dmucatch.utility.StringUtil;


public class SignUp extends Activity {
    private EditText editTextId;
    private EditText editTextPwd;
    private EditText editTextRePwd;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextAddress;

    StringUtil util = new StringUtil();
    ConnectDB connectDB = new ConnectDB();
    StringUrl url = new StringUrl();
    final DaumAddressVo tempAddress = new DaumAddressVo();

    private DaumAddressApi daumAddressApi = new DaumAddressApi();
    public static final int DAUMADDRESS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        editTextId = (EditText) findViewById(R.id.userid);
        editTextPwd = (EditText) findViewById(R.id.userpwd);
        editTextRePwd = (EditText) findViewById(R.id.userrepwd);
        editTextName = (EditText) findViewById(R.id.username);
        editTextPhone = (EditText) findViewById(R.id.userphone);
        editTextAddress = (EditText) findViewById(R.id.useraddress);

        Button findAddressBtn = (Button) findViewById(R.id.findAddressBtn);
        findAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DaumAddressApi.class);
                startActivityForResult(intent, DAUMADDRESS_REQUEST_CODE);
            }
        });

        Button back =findViewById(R.id.Backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void insert(View view) {
        String Id = editTextId.getText().toString();
        String Pwd = editTextPwd.getText().toString();
        String RePwd = editTextRePwd.getText().toString();
        String Name = editTextName.getText().toString();
        String Phone = editTextPhone.getText().toString();
        //String Address = editTextAddress.getText().toString();
        String ZipCode = tempAddress.getZipCode();
        String Address = tempAddress.getAddress();
        String BuidName = tempAddress.getBuidName();

/*
        //아이디(이메일) 정규식
        if(!util.isEmail(Id))
        {
            Toast.makeText(getApplicationContext(),"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
            return;
        }

        //비밀번호 정규식 - 영대/소문자, 숫자 및 특수문자 조합 비밀번호 8자리이상 20자리 이하
        if(!util.isValidPwd(Pwd))
        {
            Toast.makeText(getApplicationContext(),"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        // 휴대폰 유효성 검사
        if(!util.isPhNumber(Phone))
        {
            Toast.makeText(getApplicationContext(),"올바른 핸드폰 번호가 아닙니다.",Toast.LENGTH_SHORT).show();
            return;
        }
*/

        if(util.isNull(Id) || util.isNull(Pwd) || util.isNull(RePwd) || util.isNull(Name) || util.isNull(Phone) || util.isNull(Address)){
            Toast.makeText(getApplicationContext(), "양식을 작성하여주십시오.", Toast.LENGTH_LONG).show();
        }else {
            if(Pwd.equals(RePwd)) {
                if(connectDB.signUpMember(url.signUp, Id, Pwd, Name, Phone, ZipCode, Address, BuidName)){
                    Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SignUpEnd.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Main Activity", "onActivityResult : " + resultCode);
        switch (requestCode){
            case DAUMADDRESS_REQUEST_CODE: // Third레이아웃으로 보냈던 요청
                if (resultCode == RESULT_OK) { // 결과가 OK

                    System.out.println("===집코드 : " + data.getStringExtra("zipCode")+ "=====주소 : "+data.getStringExtra("address")+"====빌딩 : "+data.getStringExtra("buidName"));
                    editTextAddress.setText(String.format("%s %s",data.getStringExtra("address"), data.getStringExtra("buidName")));
                    System.out.println("==========주소 받음 : "+editTextAddress.getText());
                    // 주소 받고 값 삽입
                }
                break;
        }
    }

}
