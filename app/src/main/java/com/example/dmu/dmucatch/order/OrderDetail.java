package com.example.dmu.dmucatch.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.order.func_catch.CatchVo;

public class OrderDetail extends AppCompatActivity {
    ConnectDB connectDB = new ConnectDB();
    CatchVo cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        final Intent it = getIntent(); /*데이터 수신*/

        String catch_seq = it.getStringExtra("catch_seq");
        connectDB.selectCatch(catch_seq);
        System.out.println("캐치 vo 확인 :"+cv.getStrCatchData());;
        TextView vocontent = findViewById(R.id.vocontent);
        vocontent.setText(cv.getStrCatchData());

    }
}
