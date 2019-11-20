package com.example.dmu.dmucatch.member.user.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmu.dmucatch.MainActivity;
import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.db_connect.StringUrl;
import com.example.dmu.dmucatch.member.Userinfo;
import com.example.dmu.dmucatch.utility.DaumAddressApi;
import com.example.dmu.dmucatch.utility.DaumAddressVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MypageAdjust extends AppCompatActivity {
    TextView idView, pwdView, nameView, phoneView, addressView;
    StringUrl url = new StringUrl();
    ConnectDB connectDB = new ConnectDB();
    public static final int DAUMADDRESS_REQUEST_CODE = 101;
    final DaumAddressVo tempAddress = new DaumAddressVo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_adjust_user_activity);
        Intent intent = getIntent();
        final Userinfo userInfo = (Userinfo) intent.getSerializableExtra("userInfo");
        System.out.println("------------test----------:"+userInfo.getId());
        System.out.println("------------test----------:"+userInfo.getUser_seq());
        idView = findViewById(R.id.profId);
        nameView = findViewById(R.id.profName);
        pwdView = findViewById(R.id.profPwd);
        phoneView = findViewById(R.id.profPhone);
        addressView = findViewById(R.id.nowprofAddress);

        String user_address = connectDB.AddressCheck(url.userAddress,userInfo.getId());

        String address = setData(user_address);
        System.out.println("유저 주소 체크 마이페이지 : "+address);

        idView.setText(userInfo.getId());
        nameView.setText(userInfo.getName());

        addressView.setText(address);


        addressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DaumAddressApi.class);
                startActivityForResult(intent, DAUMADDRESS_REQUEST_CODE);
            }
        });

        Button modyBtn = findViewById(R.id.modiBtn);
        modyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = idView.getText().toString();
                String Pwd = pwdView.getText().toString();
                if(Pwd.length()<3){
                    Pwd="no";
                }
                String Name = nameView.getText().toString();
                String Phone = phoneView.getText().toString();
                //String Address = address.getText().toString();
                String ZipCode = tempAddress.getZipCode();
                String Address = tempAddress.getAddress();
                String BuidName = tempAddress.getBuidName();

/*
                if(Pwd==null||Pwd.equals("")||Pwd.equals("null")){
                    Pwd=(String) userInfo.getPwd();
                }
               */
                if(Phone==null||Phone.equals("")||Phone.equals("null")){
                    Phone=(String) userInfo.getPhone();
                }
                if(Address==null||Address.equals("")||Address.equals("null")){
                    Address=(String) userInfo.getAddress();
                }


                if(connectDB.modifyMember(url.userModify, Id, Pwd, Name, Phone, ZipCode, Address, BuidName)){
                    Toast.makeText(getApplicationContext(), "회원수정에 성공하였습니다.", Toast.LENGTH_LONG).show();
                    userInfo.setId(null);
                    userInfo.setName(null);
                    userInfo.setAddress(null);
                    userInfo.setClass_code(null);
                    userInfo.setPhone(null);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "회원수정에 실패하였습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button memberOutBtn = findViewById(R.id.memberoutBtn);
        memberOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCheck(userInfo);
            }
        });

    }

    private void deleteCheck(final Userinfo userInfo)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("회원 탈퇴!!");
        builder.setMessage("탈퇴이후 복구 불가능 할 수 있습니다.");
        builder.setPositiveButton("네,그래도 탈퇴 하겠습니다.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String Id = idView.getText().toString();
                        if(connectDB.deletToDB(url.userDel, Id)){
                            Toast.makeText(getApplicationContext(),"회원탈퇴되었습니다.",Toast.LENGTH_LONG).show();
                            userInfo.setId(null);
                            userInfo.setName(null);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "회원탈퇴에 실패하였습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.setNegativeButton("아니오,탈퇴 안하겠습니다.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Main Activity", "onActivityResult : " + resultCode);
        switch (requestCode){
            case DAUMADDRESS_REQUEST_CODE: // Third레이아웃으로 보냈던 요청
                if (resultCode == RESULT_OK) { // 결과가 OK

                    System.out.println("===집코드 : " + data.getStringExtra("zipCode")+ "=====주소 : "+data.getStringExtra("address")+"====빌딩 : "+data.getStringExtra("buidName"));
                    addressView.setText(String.format("%s %s",data.getStringExtra("address"), data.getStringExtra("buidName")));
                    System.out.println("==========주소 받음 : "+addressView.getText());
                    // 주소 받고 값 삽입
                }
                break;
        }
    }

    private String setData(String json_address){

        String TAG_JSON="address"; // Json Tag
        String TAG_add_data = "add_data";


        try {
            JSONObject jsonObject = new JSONObject(json_address);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // select data count and insert to array , 로그인이라서 어차피 데이터 1개

            if(jsonArray.length()>0){

                JSONObject item = jsonArray.getJSONObject(0);
                String addr = item.getString(TAG_add_data);
                return addr;
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
