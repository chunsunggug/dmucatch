//작성자 전제열 캐치실패
package com.example.dmu.dmucatch.order.func_catch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.order.SearchStore;

public class CatchFailedUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_failed_user_activity);

        TextView CatchFail = findViewById(R.id.textFailed);

        Intent it = getIntent();

        Button btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inMain = new Intent(getApplicationContext(), SearchStore.class);
                startActivity(inMain);
            }
        });
    }


}
