package com.example.dmu.dmucatch.notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.db_connect.StringUrl;
import com.example.dmu.dmucatch.member.Userinfo;
import com.example.dmu.dmucatch.utility.StringUtil;

public class AddNotice extends AppCompatActivity {
    Userinfo userinfo;
    EditText title_notice,content_notice;
    StringUtil strUtil = new StringUtil();
    ConnectDB connectdb = new ConnectDB(); // db connection 모음
    StringUrl url = new StringUrl(); // url 모음
    private boolean mJsonString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);



        final String cate = getIntent().getStringExtra("Cate");
        final String kind = getIntent().getStringExtra("kind");

        if(kind.equals("add")){
            TextView headerText = findViewById(R.id.cate_notice);
            headerText.setText(cate+" 작성하기");

            title_notice = (EditText)findViewById(R.id.title_notice);
            content_notice = (EditText)findViewById(R.id.content_notice);



            Button submit = findViewById(R.id.write_notice);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "글 쓰기에 성공하였습니다.", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                            submit(cate);
                        }
                    }).start();
                }
            });
        }else{
            final String seq= Integer.toString(getIntent().getIntExtra("seq",0));
            final String title= getIntent().getStringExtra("title");
            final String content= getIntent().getStringExtra("content");
            Button modibtn = findViewById(R.id.write_notice);
            modibtn.setText("수정하기");

            TextView headerText = findViewById(R.id.cate_notice);
            headerText.setText(cate+" 수정하기");

            title_notice = (EditText)findViewById(R.id.title_notice);
            title_notice.setText(title);
            content_notice = (EditText)findViewById(R.id.content_notice);
            content_notice.setText(content);

            modibtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "글 수정에 성공하였습니다.", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                            modify(cate,seq);
                        }
                    }).start();
                }
            });


        }





        Button cancel = findViewById(R.id.write_back);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void submit(String cate) {
        final String title= title_notice.getText().toString();
        final String content= content_notice.getText().toString();


        if(strUtil.isNull(title) || strUtil.isNull(content)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "제목과 내용을 입력하세요.", Toast.LENGTH_LONG).show();
                }
            });
        } // null check
        else {
            if(mJsonString = connectdb.noticeSubmit(url.submit,userinfo.getId(),cate, title, content)){
                if(cate.equals("이벤트")){
                    Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                    startActivity(intent);
                }

            }else{
                Toast.makeText(getApplicationContext(), "글 쓰기에 실패하였습니다.", Toast.LENGTH_LONG).show();
            };

        }

    }

    private void modify(final String cate,final String seq){
        final String title= title_notice.getText().toString();
        final String content= content_notice.getText().toString();

        if(strUtil.isNull(title) || strUtil.isNull(content)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "제목과 내용을 입력하세요.", Toast.LENGTH_LONG).show();
                }
            });
        } // null check
        else {
                    if(mJsonString = connectdb.modifyNotice(url.noticeModi,seq, title, content)){
                        if(cate.equals("이벤트")){
                            Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                            startActivity(intent);
                        }

                    }else{
                        System.out.println("글 수정 실패!!");
                    };
        }
    }
}
