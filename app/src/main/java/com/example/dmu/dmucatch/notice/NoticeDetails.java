package com.example.dmu.dmucatch.notice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.db_connect.StringUrl;
import com.example.dmu.dmucatch.member.Userinfo;
import com.example.dmu.dmucatch.utility.StringUtil;

public class NoticeDetails extends AppCompatActivity {
    Userinfo userinfo;
    Button NoticeModi,NoticeDel;
    ConnectDB connectdb = new ConnectDB();
    StringUrl url = new StringUrl();
    StringUtil strUtil = new StringUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);


        Intent getit = getIntent();
       final String cate = getit.getStringExtra("Cate");
       final int idx = getit.getIntExtra("index",0);
       final int seq = getit.getIntExtra("notice_seq",0);
       final NoticeInfo nio = (NoticeInfo) getit.getSerializableExtra("NoticeVO");

        System.out.println("유저 인포 :"+userinfo.getClass_code());

        NoticeModi =(Button) findViewById(R.id.noticemodibtn);
        NoticeModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),AddNotice.class);
                it.putExtra("Cate",cate);
                it.putExtra("kind","modify");
                it.putExtra("seq",seq);
                it.putExtra("title",nio.getTitle(idx));
                it.putExtra("content",nio.getContent(idx));
                startActivity(it);

            }
        });

        NoticeDel =(Button) findViewById(R.id.noticedelbtn);
        NoticeDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeDelete(cate,Integer.toString(seq));
            }
        });
        if(TextUtils.isEmpty(userinfo.getClass_code())){
            hide(NoticeModi);
            hide(NoticeDel);
        }else if(userinfo.getClass_code().equals("30")){
            look(NoticeModi);
            look(NoticeDel);
        }else{
            hide(NoticeModi);
            hide(NoticeDel);
        }




        System.out.println("전달 확인 :"+cate+idx+seq);
        System.out.println("notice 인포 :"+nio.getTitle(idx));

        TextView header = findViewById(R.id.mypage8);
        header.setText(cate);
        //System.out.println("idx 타이틀 확인"+nio.getTitle(0));
        TextView title = findViewById(R.id.textView31);
        title.setText(nio.getTitle(idx));
        TextView content = findViewById(R.id.textView32);
        content.setText(nio.getContent(idx));

    }


    private void look(Button btn) {
        btn.setVisibility(View.VISIBLE);
    }
    private void hide(Button btn) {
        btn.setVisibility(View.GONE);
    }

    private void noticeDelete(final String cate, final String seq)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(cate+" 삭제!!");
        builder.setMessage("삭제 이후 복구가 불가능합니다.");
        builder.setPositiveButton("네,그래도 삭제 하겠습니다.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(connectdb.noticeDel(url.noticeDel, seq)){
                            Toast.makeText(getApplicationContext(),cate+"가 삭제되었습니다.",Toast.LENGTH_LONG).show();

                            if(cate.equals("공지")){
                                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        }else{
                            Toast.makeText(getApplicationContext(), "삭제에 실패하였습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.setNegativeButton("아니오,삭제 안하겠습니다.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}
