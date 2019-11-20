package com.example.dmu.dmucatch.order.func_catch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.dmu.dmucatch.R;

public class CatchConfirm extends AppCompatActivity {
    TextView strCatchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_confirm_activity);


        strCatchInfo = findViewById(R.id.textCatched);


        strCatchInfo.setText(CatchVo.getStrCatchData());


        Button btnDecline = findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mvCatchWait = new Intent(getApplicationContext(), CatchWaitUser.class);
                setResult(Activity.RESULT_CANCELED,mvCatchWait); // 이전화면 으로 보낼 세팅
                finish();
            }
        });

        Button btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mvCatchWait = new Intent(getApplicationContext(), CatchWaitUser.class);
                setResult(Activity.RESULT_OK,mvCatchWait); // 이전화면 으로 보낼 세팅
                finish();
            }
        });

    }

}
