package com.example.dmu.dmucatch.member.user.mypage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.notice.ReadyActivity;

public class MypageUser extends AppCompatActivity {
//사용 안하는 액티비티 착각ㄴㄴ 마이페이지는 Adjust
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_user_activity);


        Button mypageadjust = findViewById(R.id.mypageadjust);
        mypageadjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageAdjust.class);
                startActivity(intent);
            }
        });

        Button mpbtn = findViewById(R.id.mypagebtn);
        mpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageUser.class);
                startActivity(intent);
            }
        });
/*
        Button mainbtn = findViewById(R.id.mainbtn);
        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainAppActivity_ljs.class);
                startActivity(intent);
            }
        });
        */

        Button pointbtn = findViewById(R.id.pointbtn);
        pointbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button couponbtn = findViewById(R.id.couponbtn);
        couponbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button orderbtn2 = findViewById(R.id.orderbtn2);
        orderbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button reviewbtn = findViewById(R.id.reviewbtn);
        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button noticebtn = findViewById(R.id.noticebtn);
        noticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button eventbtn = findViewById(R.id.eventbtn);
        eventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button cardbtn = findViewById(R.id.cardbtn);
        cardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button questionbtn = findViewById(R.id.questionbtn);
        questionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button bookmarkbtn = findViewById(R.id.bookmarkbtn);
        bookmarkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button orderbtn = findViewById(R.id.orderbtn);
        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

        Button catchbtn = findViewById(R.id.catchbtn);
        catchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
            }
        });

    }
}
