package com.example.dmu.dmucatch.notice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmu.dmucatch.R;
import com.example.dmu.dmucatch.db_connect.ConnectDB;
import com.example.dmu.dmucatch.db_connect.StringUrl;
import com.example.dmu.dmucatch.member.Userinfo;
import com.example.dmu.dmucatch.order.OrderDetail;
import com.example.dmu.dmucatch.utility.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderActivity extends AppCompatActivity {

    Userinfo userinfo;
    ImageButton findorder;
    private String mJsonString;
    ConnectDB connectdb = new ConnectDB();
    StringUrl url = new StringUrl();
    StringUtil strUtil = new StringUtil();

    ListView listView;              //리스트 뷰를 리스트 뷰 선언
    SimpleAdapter sAdapter;         //심플아답터 s아답터 선언
    ArrayList<HashMap<String,String>> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        new Thread(new Runnable() {
            public void run() {
                list();
            }
        }).start();

        findorder=(ImageButton) findViewById(R.id.search_search_myorder_btn);
        findorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText findText = findViewById(R.id.search_myorder);
                final String fd = findText.getText().toString();

                System.out.println("검색어 확인 : "+fd);

                if(fd.length()!=0){
                    listData.clear();
                    sAdapter.notifyDataSetChanged();
                    new Thread(new Runnable() {
                        public void run() {
                            list(fd);
                        }
                    }).start();
                }else{
                    listData.clear();
                    sAdapter.notifyDataSetChanged();
                    new Thread(new Runnable() {
                        public void run() {
                            list();
                        }
                    }).start();
                }
            }
        });

    }
    private void list() {
        System.out.println("List 안 시퀀스 확인 : "+userinfo.getUser_seq());
        mJsonString = connectdb.selectMyOrderToJson(url.myOrder, Integer.toString(userinfo.getUser_seq())); // login url, php 테그 배열, EditText 변수 값 배열
        if(strUtil.isFailed(mJsonString)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(mJsonString);
                    TextView noOrder = findViewById(R.id.noOrder);
                    noOrder.setText("캐치 내역이 없습니다.");
                    noOrder.setVisibility(View.VISIBLE);
                    Toast.makeText(MyOrderActivity.this, "캐치 내역이 없습니다. 캐치해보세요~ ", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("json : "+mJsonString);
                    setDate(); // getJsonData
                    Toast.makeText(MyOrderActivity.this, "List Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void list(String fd) {
        System.out.println("php 진입 전 값 모두 확인 :"+"/검색어 : "+fd);
        mJsonString = connectdb.findMyOrderToJson(url.myOrdersearch,Integer.toString(userinfo.getUser_seq()), fd); // login url, php 테그 배열, EditText 변수 값 배열
        if(strUtil.isFailed(mJsonString)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(mJsonString);
                    Toast.makeText(MyOrderActivity.this, "검색 하신 제목/내용이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("json : "+mJsonString);
                    setDate(); // getJsonData
                    Toast.makeText(MyOrderActivity.this, "List Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void setDate(){
        System.out.println("setDate:"+mJsonString);

        String TAG_JSON="myorderList"; // Json Tag
        String TAG_SEQ = "catch_seq";
        String TAG_NAME = "store_name";
        String TAG_TIME = "catched_time";
        String TAG_PERSON ="catch_person";
        String TAG_MONEY = "catch_money";

        listView = findViewById(R.id.myorderList);     //리스트 뷰 불러오기
        sAdapter = new SimpleAdapter(this,listData,android.R.layout.simple_list_item_activated_2,
                new String[] {"name","order_seq"},new int[] {android.R.id.text1,android.R.id.text2}){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.rgb(0,183,0));
                textView.setTextSize(20);
                return view;
            }

        };
        listView.setAdapter(sAdapter);


        try{
            JSONObject jsonObject = new JSONObject(String.valueOf(mJsonString));
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                int noti_seq = item.getInt(TAG_SEQ);
                String name = item.getString(TAG_NAME);
                String time = item.getString(TAG_TIME);
                String person = item.getString(TAG_PERSON);
                String money = item.getString(TAG_MONEY);



                HashMap<String,String> items = new HashMap<>();
                items.put("name",name);
                items.put("order_seq","캐치 번호 : "+Integer.toString(noti_seq)
                        +"\n"+time+"\n"+"신청 인원 : "+person+"\n"+"신청 금액 : "+money);
                items.put("catch_seq",Integer.toString(noti_seq));
                listData.add(items);
                sAdapter.notifyDataSetChanged();
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                    Object vo = (Object) listView.getAdapter().getItem(position);  //리스트뷰의 포지션 내용을 가져옴.
                    HashMap<String ,String > check;
                    check = (HashMap<String, String>) vo;
                    final HashMap<String, String> finalCheck = check;

                    new Thread(new Runnable() {
                        public void run() {
                            // 쓰레드 도중 토스트 발생하기 위한 메소드 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    System.out.println("실행 확인");
                                }
                            });

                            Intent it = new Intent(getApplicationContext(), OrderDetail.class);
                            System.out.println("해시 값 확인:"+ finalCheck.get("catch_seq"));
                            it.putExtra("catch_seq",finalCheck.get("catch_seq"));
                            startActivity(it);
                        }
                    }).start();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
