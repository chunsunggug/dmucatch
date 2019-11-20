package com.example.dmu.dmucatch.member.user.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.dmu.dmucatch.R;

public class FindIdPwdUser extends AppCompatActivity {

    Button findCancel;
    Button findPwd;

    //버튼 클릭시 레이아웃 전환
    private void changeView(int index) {
        // LayoutInflater 초기화.
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        FrameLayout frame = (FrameLayout) findViewById(R.id.frame) ;
        if (frame.getChildCount() > 0) {
            // FrameLayout에서 뷰 삭제.
            frame.removeViewAt(0);
        }


        View view = null ;
        switch (index) {
            case 0 :
                view = inflater.inflate(R.layout.findid_user_activit, frame, false) ;
                break ;
            case 1 :
                view = inflater.inflate(R.layout.findpwd_user_activity, frame, false) ;
                break ;
        }

        // FrameLayout에 뷰 추가.
        if (view != null) {
            frame.addView(view) ;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findidpwd_user_activity);

        //아이디 찾기화면 전환
        Button btnFindID = (Button) findViewById(R.id.btnFindID) ;
        btnFindID.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(0) ;
            }
        });
        //비밀번호 찾기화면 전환
        Button btnFindPwd = (Button) findViewById(R.id.btnFindPwd) ;
        btnFindPwd.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(1) ;
            }
        });

        //찾기 버튼
        findPwd = findViewById(R.id.btnFind);
        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPwdFinish.class);
                startActivity(intent);
            }
        });

        //취소 버튼
        findCancel = findViewById(R.id.findCancel);
        findCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
            }
        });

        //아이디 비밀번호 찾기 눌렀을때 초기화면 아이디 찾기
        changeView(0) ;
    }
}
