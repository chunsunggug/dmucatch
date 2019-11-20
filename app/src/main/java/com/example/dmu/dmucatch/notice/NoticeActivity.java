package com.example.dmu.dmucatch.notice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.dmu.dmucatch.utility.StringUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    Userinfo userinfo;
    Button addNotice;
    ImageButton findNotice;
    private String cate="공지";
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
        setContentView(R.layout.activity_notice);
        setTitle("공 지 사 항");

        addNotice =(Button) findViewById(R.id.add_Notice_btn);
        if(TextUtils.isEmpty(userinfo.getClass_code())){
            hide();
        }else if(userinfo.getClass_code().equals("30")){
            look();
        }else{
            hide();
        }

        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), AddNotice.class);
                it.putExtra("Cate",cate);
                it.putExtra("kind","add");
                startActivity(it);
            }
        });


        System.out.println("php 진입 전 cate : "+cate);
        new Thread(new Runnable() {
            public void run() {
                list();
            }
        }).start();

        findNotice=(ImageButton) findViewById(R.id.search_notice_btn);
        findNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText findText = findViewById(R.id.search_notice);
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

    @Override
    public void onRestart()
    {

        super.onRestart();
        finish();
        startActivity(getIntent());

    }



    private void list() {
        System.out.println("php 진입 전 값 모두 확인 :"+"cate:"+cate);
        mJsonString = connectdb.selectNoticeToJson(url.noticelist, "cate", cate); // login url, php 테그 배열, EditText 변수 값 배열
        if(strUtil.isFailed(mJsonString)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(mJsonString);
                    Toast.makeText(NoticeActivity.this, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("json : "+mJsonString);
                    setDate(); // getJsonData
                    Toast.makeText(NoticeActivity.this, "List Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void list(String fd) {
        System.out.println("php 진입 전 값 모두 확인 :"+"cate:"+cate+"/검색어 : "+fd);
        mJsonString = connectdb.findNoticeToJson(url.noticesearch, "cate", cate,fd); // login url, php 테그 배열, EditText 변수 값 배열
        if(strUtil.isFailed(mJsonString)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(mJsonString);
                    Toast.makeText(NoticeActivity.this, "검색 하신 제목/내용이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("json : "+mJsonString);
                    setDate(); // getJsonData
                    Toast.makeText(NoticeActivity.this, "List Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void setDate(){
        System.out.println("setDate:"+mJsonString);

        String TAG_JSON="noticeList"; // Json Tag
        String TAG_SEQ = "noti_seq";
        String TAG_CATE = "cate";
        String TAG_ID = "user_id";
        String TAG_TITLE ="title";
        String TAG_CONTENT = "content";
        String TAG_READNUM = "readnum";
        String TAG_WRITEDATE = "writedate";

        listView = findViewById(R.id.noticeList);     //리스트 뷰 불러오기

        sAdapter = new SimpleAdapter(this,listData,android.R.layout.simple_list_item_activated_2,
                new String[] {"title","writedate"},new int[] {android.R.id.text1,android.R.id.text2}){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLUE);

                return view;
            }

        };







        listView.setAdapter(sAdapter);


        try{
            JSONObject jsonObject = new JSONObject(String.valueOf(mJsonString));
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

           final NoticeInfo nio = new NoticeInfo(jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                int noti_seq = item.getInt(TAG_SEQ);
                String cate = item.getString(TAG_CATE);
                String id = item.getString(TAG_ID);
                String title = item.getString(TAG_TITLE);
                String content = item.getString(TAG_CONTENT);
                int readnum = item.getInt(TAG_READNUM);
                Date date =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.getString(TAG_WRITEDATE));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd HH:mm");
                nio.setNoti_seq(noti_seq,i);
                nio.setCate(cate,i);
                nio.setUser_id(id,i);
                nio.setTitle(title,i);
                nio.setContent(content,i);
                nio.setReadnum(readnum,i);
                nio.setWritedate(date,i);

                HashMap<String,String> items = new HashMap<>();
                items.put("title",title);
                items.put("writedate","조회수 : "+readnum+"\n"+sdf.format(date));

                listData.add(items);
                sAdapter.notifyDataSetChanged();

                System.out.println(nio.getNoti_seq(i));
                System.out.println("get date :"+nio.getWritedate(i));



            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                    System.out.println("position : "+ position);
                    System.out.println("id : "+ id);
                    System.out.println("nio 겟 seq : "+ nio.getNoti_seq(position));

                    if(strUtil.isNull(Integer.toString(nio.getNoti_seq(position)))){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "조회수에 이상이 생김.", Toast.LENGTH_LONG).show();
                            }
                        });
                    } // null check
                    else {
                        new Thread(new Runnable() {
                            public void run() {
                                    // 쓰레드 도중 토스트 발생하기 위한 메소드 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            System.out.println("실행 확인");
                                        }
                                    });
                                addnum(Integer.toString(nio.getNoti_seq(position)));
                                Intent intent=new Intent(getApplicationContext(),NoticeDetails.class);
                                intent.putExtra("notice_seq",nio.getNoti_seq(position));
                                intent.putExtra("index",position);
                                intent.putExtra("Cate",cate);
                                intent.putExtra("NoticeVO",nio);
                                startActivity(intent);
                            }
                        }).start();
                    }


                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void look() {
        addNotice.setVisibility(View.VISIBLE);
    }
    private void hide() {
        addNotice.setVisibility(View.GONE);
    }
    private void addnum(String seq) {


        if(strUtil.isNull(seq)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "시퀀스 오류", Toast.LENGTH_LONG).show();
                }
            });
        } // null check
        else {
            boolean result=false;
            result=connectdb.addReadNum(url.AddReadnum,seq);
            System.out.println("조회수 증가 여부 확인 : "+result);

        }

    }
}
