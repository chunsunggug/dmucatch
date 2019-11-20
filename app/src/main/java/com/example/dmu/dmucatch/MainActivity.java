package com.example.dmu.dmucatch;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmu.dmucatch.member.Userinfo;
import com.example.dmu.dmucatch.member.user.account.LoginUser;
import com.example.dmu.dmucatch.member.user.mypage.MypageAdjust;
import com.example.dmu.dmucatch.notice.CenterActivity;
import com.example.dmu.dmucatch.notice.EventActivity;
import com.example.dmu.dmucatch.notice.MyCardActivity;
import com.example.dmu.dmucatch.notice.MyLikeActivity;
import com.example.dmu.dmucatch.notice.MyOrderActivity;
import com.example.dmu.dmucatch.notice.NoticeActivity;
import com.example.dmu.dmucatch.order.func_catch.CatchWaitUser;
import com.example.dmu.dmucatch.utility.LayoutUtil;
import com.example.dmu.dmucatch.utility.NumUtil;
import com.example.dmu.dmucatch.utility.StringUtil;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MapView.CurrentLocationEventListener,
        MapReverseGeoCoder.ReverseGeoCodingResultListener{
    NavigationView navigationView;

    EditText person,money;
    final Userinfo userinfo = new Userinfo();
    final NumUtil numUtil = new NumUtil();
    final StringUtil strUil = new StringUtil();
    final LayoutUtil layoutUtil = new LayoutUtil();
    ImageView lock;
    ImageView profile;
    private MapView mMapView;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mMapView = (MapView) findViewById(R.id.map_view);
        //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setCurrentLocationEventListener(this);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        person = (EditText)findViewById(R.id.personEdit);
        money  =  (EditText)findViewById(R.id.moneyEdit);

        ImageButton pUpBtn = findViewById(R.id.pUpBtn);
        pUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setText(Integer.toString(numUtil.upPerson(Integer.parseInt(person.getText().toString()))));
            }
        }); // 클릭 시 인원 수 증가 || onclick plus person one.

        ImageButton pDownBtn = findViewById(R.id.pDownBtn);
        pDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setText(Integer.toString(numUtil.dnPerson(Integer.parseInt(person.getText().toString()))));
            }
        }); // 클릭 시 인원 수 감소 || onclick minus person one.

        ImageButton mUpBtn = findViewById(R.id.mUpBtn);
        mUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money.setText(Integer.toString(numUtil.upMoney(Integer.parseInt(money.getText().toString()))));
            }
        }); // 클릭 시 500원 증가 || onclick plus 500won.

        ImageButton mDownBtn = findViewById(R.id.mDownBtn);
        mDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money.setText(Integer.toString(numUtil.dnMoney(Integer.parseInt(money.getText().toString()))));
            }
        }); // 클릭 시 500원 감소 || onclick minus 500won.


        // 클릭 시 캐치 기능 구현    || onclick do catch
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userinfo.getId()!=null){

                    Intent catchIntent = new Intent(getApplicationContext(), CatchWaitUser.class);

                    // deliver to CatchWaitUser || 값 전달
                    catchIntent.putExtra("userId", userinfo.getId());
                    catchIntent.putExtra("person", person.getText().toString());
                    catchIntent.putExtra("money", money.getText().toString());

                    startActivity(catchIntent);

                }else {
                    mvLogin();
                }
            }
        });


        System.out.println("========= 이메일 ========== "+userinfo.getId());
        System.out.println("=========  이름 ========== "+userinfo.getName());


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        // 로그인 상태면 로그아웃 버튼 활성화
        if(userinfo.getId() != null){
            layoutUtil.navVisible(navigationView , R.id.nav_logout);
        }

        navigationView.setNavigationItemSelectedListener(this);


        //View nav_header_view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        View nav_header_view = navigationView.getHeaderView(0);

        if(userinfo.getId() == null || userinfo.getName() == null) {

            ImageView lock = (ImageView) nav_header_view.findViewById(R.id.navHeadImage);
            lock.setImageResource(R.drawable.lock);
            TextView nav_header_id_TitleText = (TextView) nav_header_view.findViewById(R.id.navHeadTitle);
            TextView nav_header_id_subText = (TextView) nav_header_view.findViewById(R.id.navHeadSubTitle);
            nav_header_id_TitleText.setText("로그인을 해주세요");
            nav_header_id_TitleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                    startActivity(intent);
                    finish();
                }
            });


            nav_header_id_subText.setText("");

        } else{
            ImageView profile = (ImageView) nav_header_view.findViewById(R.id.navHeadImage);
            profile.setImageResource(R.drawable.profile);
            TextView nav_header_id_TitleText = (TextView) nav_header_view.findViewById(R.id.navHeadTitle);
            TextView nav_header_id_subText = (TextView) nav_header_view.findViewById(R.id.navHeadSubTitle);
            nav_header_id_TitleText.setText(userinfo.getId() + "");
            nav_header_id_subText.setText(userinfo.getName() +"님");
        }

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
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myPage) {
            if(userinfo.getId()!=null){
                Intent homeIntent = new Intent(this, MypageAdjust.class);
                homeIntent.putExtra("userInfo",userinfo);
                startActivity(homeIntent);
            }else {
                mvLogin();
            }

        } /*else if (id == R.id.nav_myLike) {
            if(userinfo.getId()!=null){
                doIntent(new Intent(this, MyLikeActivity.class));
            }else {
                mvLogin();
            }

        } */else if (id == R.id.nav_myOrder) {
            if(userinfo.getId()!=null){
                doIntent(new Intent(this, MyOrderActivity.class));
            }else {
                mvLogin();
            }

        } else if (id == R.id.nav_myCard) {
            if(userinfo.getId()!=null){
                doIntent(new Intent(this, MyCardActivity.class));
            }else {
                mvLogin();
            }

        }else if (id == R.id.nav_notice) {
            doIntent(new Intent(this, NoticeActivity.class));
        }else if (id == R.id.nav_event) {
            doIntent(new Intent(this, EventActivity.class));
        }else if (id == R.id.nav_center) {
            doIntent(new Intent(this, CenterActivity.class));
        }
        else if (id == R.id.nav_logout) {
            if(userinfo.getId()!=null){
                userinfo.setId(null);
                userinfo.setName(null);
                userinfo.setClass_code(null);
                userinfo.setAddress(null);
                userinfo.setPhone(null);
                userinfo.setUser_seq(0);
                layoutUtil.navInvisible(navigationView , id);   // 로그아웃 안보이게 변경 || do logout invisible
                doIntent(new Intent(getApplicationContext(), MainActivity.class));
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void mvLogin(){
        Toast.makeText(MainActivity.this, "로그인 후 이용해주세요..", Toast.LENGTH_SHORT).show();
        Intent homeIntent = new Intent(this, LoginUser.class);
        startActivity(homeIntent);
        finish();
    }

    private void doIntent(Intent tempIntent){
        startActivity(tempIntent);
        //finish();
    }









    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }


    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }




    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }



    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
