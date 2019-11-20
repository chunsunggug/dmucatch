// do catch
package com.example.dmu.dmucatch.order.func_catch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmu.dmucatch.MainActivity;
import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.member.Userinfo;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CatchWaitUser extends AppCompatActivity {
    ConnectDB db = new ConnectDB();
    private static String id;
    ScheduledExecutorService doIsCatched, doCatchInfo;
    Runnable reloaderIsCatched, reloaderGetCatchInfo;
    private int catch_yn; // 1 : success catch , 2 : failed catch   ,   3 : cancle  catch  ,  4 : error catch
    Timer timer = new Timer();
    private int doWhere = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_wait_user_activity);

        final Intent intent = getIntent(); /*데이터 수신*/
        id = intent.getExtras().getString("userId");
        String person = intent.getExtras().getString("person");
        String money = intent.getExtras().getString("money");



        final Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("DB 접근 전 ID check :"+id);
                if(db.deletCatch(id)){
                    catch_yn = 3;
                    if(doWhere == 1){
                        doIsCatched.shutdown();
                    }
                    else{
                        doCatchInfo.shutdown();
                    }
                    finish();
                }else{
                    catch_yn = 4;
                    System.out.println("디비 오류 발생");
                    finish();
                }
            }
        });


        ProgressBar pb = findViewById(R.id.wait_progressBar);
        pb.setIndeterminate(true);
        pb.getIndeterminateDrawable().setColorFilter(Color.rgb(135,154,255),android.graphics.PorterDuff.Mode.MULTIPLY);

        if(db.insertCatch(id, person, money)){
            TextView textView = findViewById(R.id.textCatch);
            textView.setText(person + "명이 먹을 수 있는 " + money + "원 메뉴를 찾고있습니다!!");
            db.getCatchSeq(Integer.toString(Userinfo.getUser_seq()));

            // catched 여부 검사
            reloaderIsCatched = new Runnable() {
                @Override
                public void run() {
                    if(db.selectIsCatched(CatchVo.catchSeq)){
                        doIsCatched.shutdown();
                        doWhere = 2;

                        doCatchInfo = Executors.newSingleThreadScheduledExecutor();
                        doCatchInfo.scheduleAtFixedRate(reloaderGetCatchInfo, 0, 1, TimeUnit.SECONDS);

                        timer.cancel();
                    }
                }
            };

            doIsCatched = Executors.newSingleThreadScheduledExecutor();
            doIsCatched.scheduleAtFixedRate(reloaderIsCatched, 0, 1, TimeUnit.SECONDS);

            // getCatchInfo
            reloaderGetCatchInfo = new Runnable() {
                @Override
                public void run() {
                    if(db.selectCatchInfo(CatchVo.catchSeq)){

                        doCatchInfo.shutdown();
                        catch_yn = 1;

                        Intent mvConfirm = new Intent(getApplicationContext(),CatchConfirm.class);
                        startActivityForResult(mvConfirm,101);

                    }
                }
            };
        }

        else{
            if(db.deletCatch(id)){
                catch_yn = 2;
                finish();
            }else{
                catch_yn = 4;
                finish();
            }
        }

        timer.schedule( new TimerTask()
                        {
                            public void run()
                            {

                                finish();
                            }
                        }
                , 60000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String, String> items = new HashMap<>();

        // requestCode 로 각 intent 구분
        if (requestCode == 101) { // CatchConfirm
            if(resultCode == RESULT_OK){   // 확인 result = -1
                Intent mvHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mvHome);
                finish();
            }else{                  // 취소 result = 0
                catch_yn = 3;
                CatchVo.refreshCatchVo();
                doIsCatched.scheduleAtFixedRate(reloaderIsCatched, 0, 1, TimeUnit.SECONDS);
                timer.schedule( new TimerTask()
                                {
                                    public void run()
                                    {

                                        finish();
                                    }
                                }
                        , 60000);
            }
        }

    }


    @Override
    public void onBackPressed() {
        catch_yn=3;
                finish(); // app 종료 시키기
    }

    @Override
    public void onStop() {
        super.onStop();
        doIsCatched.shutdown();


        switch (catch_yn){  // 1 : success catch , 2 : failed catch   ,   3 : cancle  catch  ,  4 : error catch
            case 1: Toast.makeText(getApplicationContext(),"캐치에 성공했습니다.",Toast.LENGTH_LONG).show(); break;
            case 2: Toast.makeText(getApplicationContext(),"캐치를 찾지 못했습니다.ㅜㅜ",Toast.LENGTH_LONG).show(); break;
            case 3: Toast.makeText(getApplicationContext(),"캐치를 취소했습니다 ㅜㅜ",Toast.LENGTH_LONG).show(); break;
            case 4: Toast.makeText(getApplicationContext(),"오류 발생 고객센터로 연락 바랍니다.",Toast.LENGTH_LONG).show(); break;
            default:Toast.makeText(getApplicationContext(),"캐치를 취소했습니다 ㅜㅜ",Toast.LENGTH_LONG).show(); break;

        }
                finish();

    }

}
