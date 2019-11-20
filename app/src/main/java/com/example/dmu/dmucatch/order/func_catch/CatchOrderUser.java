package com.example.dmu.dmucatch.order.func_catch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.member.user.mypage.MypageUser;
import com.example.dmu.dmucatch.notice.ReadyActivity;

public class CatchOrderUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    int nearC = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_catch_user_order_activity);

        TextView orderInfo = findViewById(R.id.orderInfo); //최상단 주문 정보 텍스트뷰 ID 파인드
        TextView nearInfo = findViewById(R.id.nearInfo); //최상단 바로 밑 주변 가게 알람 반경 정보 텍스트뷰 ID 파인드

        final RadioGroup nearG =(RadioGroup)findViewById(R.id.nearG);



        RadioButton near1 = findViewById(R.id.near1); //주변 가게 알람 반경 설정 1KM
        RadioButton near3 = findViewById(R.id.near3); //주변 가게 알람 반경 설정 3KM
        RadioButton near5 = findViewById(R.id.near5); //주변 가게 알람 반경 설정 5KM



        EditText orderPeople = findViewById(R.id.orderPeople); //식사 인원 설정 에디트 텍스튜 뷰 ID 파인드
        EditText orderMoney = findViewById(R.id.orderMoney); //사용 예정 금액 설정 에디트 텍스튜 뷰 ID 파인드


        RadioButton orderTake = findViewById(R.id.orderTake); //현장 주문으로 TAKE OUT 종류 버튼 ID 파인드
        orderTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        RadioButton orderDel = findViewById(R.id.orderDel); //배달 주문으로 딜리버리 종류 버튼 ID 파인드
        orderDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        Button payCatch = findViewById(R.id.payCatch); //결제 수단 변경 버튼 ID 파인드
        payCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godel = new Intent(getApplicationContext(), CatchWaitUser.class);
                startActivity(godel);;
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = null;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catch_user_order_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myPage) {
            Intent homeIntent = new Intent(this, MypageUser.class);
            startActivity(homeIntent);

        } /*else if (id == R.id.nav_myLike) {
            Intent homeIntent = new Intent(this, ReadyActivity.class);
            startActivity(homeIntent);

        } */else if (id == R.id.nav_myOrder) {
            Intent homeIntent = new Intent(this, ReadyActivity.class);
            startActivity(homeIntent);

        } else if (id == R.id.nav_myCard) {
            Intent homeIntent = new Intent(this, ReadyActivity.class);
            startActivity(homeIntent);

        }  else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
