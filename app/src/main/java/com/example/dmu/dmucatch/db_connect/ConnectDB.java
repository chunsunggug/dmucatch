package com.example.dmu.dmucatch.db_connect;

import com.example.dmu.dmucatch.member.Userinfo;
import com.example.dmu.dmucatch.order.func_catch.CatchVo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    int i;
    final String gradeCode[] = new StringDBCode().userGrade;
    final StringUrl strUrl = new StringUrl();

    public String selectToJson(String url, String[] phpVar, String[] typeData) {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            for (i = 0; i < phpVar.length; i++) {
                nameValuePairs.add(new BasicNameValuePair(phpVar[i], typeData[i]));
            }

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            return responsedata;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "fail";
        }
    }

    public String selectNoticeToJson(String url, String cate, String cateValue) {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair(cate, cateValue));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            return responsedata;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "fail";
        }
    }

    public String findNoticeToJson(String url, String cate, String cateValue,String findWord) {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair(cate, cateValue));
            nameValuePairs.add(new BasicNameValuePair("Fd",findWord));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            return responsedata;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "fail";
        }
    }

    public String selectMyOrderToJson(String url, String user_seq) {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("seq", user_seq));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            return responsedata;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "fail";
        }
    }

    public String findMyOrderToJson(String url, String user_seq,String findWord) {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("seq", user_seq));
            nameValuePairs.add(new BasicNameValuePair("Fd",findWord));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            return responsedata;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return "fail";
        }
    }

    public  String AddressCheck(String url,String Id){
        System.out.println("주소 체크 Id: "+Id);
        try{
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("Id",Id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println("리턴값 확인 : "+responsedata);
            return responsedata;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public  String getStoreAddress(String url, String store_seq){
        System.out.println("catch store_seq: "+ store_seq);
        try{
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("store_seq",store_seq));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println("리턴값 확인 : "+responsedata);
            return responsedata;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean signUpMember(String link, String Id, String Pwd, String Name, String Phone, String ZipCode, String Address, String BuidName) {
        try {
            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
            data += "&" + URLEncoder.encode("Pwd", "UTF-8") + "=" + URLEncoder.encode(Pwd, "UTF-8");
            data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
            data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");
            data += "&" + URLEncoder.encode("ClassCode", "UTF-8") + "=" + URLEncoder.encode(gradeCode[0], "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                StringUrl stringUrl = new StringUrl();

                insertAddress(stringUrl.setAddress, Id, ZipCode, Address, BuidName);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }

    public boolean modifyAddress(String link,int user_seq,String ZipCode,String Address,String BuidName){
        System.out.println("커넥트 디비 어드레스 확인:"+Address);
        try {

            String data = URLEncoder.encode("UserSeq", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(user_seq), "UTF-8");
            data += "&" + URLEncoder.encode("ZipCode", "UTF-8") + "=" + URLEncoder.encode(ZipCode, "UTF-8");
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8");
            data += "&" + URLEncoder.encode("BuidName", "UTF-8") + "=" + URLEncoder.encode(BuidName, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }

    public boolean modifyMember(String link, String Id, String Pwd, String Name, String Phone, String ZipCode, String Address, String BuidName) {
          System.out.println("커넥트 디비 어드레스 확인:"+Address);
          System.out.println("커넥트 디비 링크와 아이디 확인 : "+link+"--------"+Id+"--------"+Name+"--------"+Phone+"--------"+Pwd);
        try {

/*
            data += "&" + URLEncoder.encode("ZipCode", "UTF-8") + "=" + URLEncoder.encode(ZipCode, "UTF-8");
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8");
            data += "&" + URLEncoder.encode("BuidName", "UTF-8") + "=" + URLEncoder.encode(BuidName, "UTF-8");
*/

            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
            data += "&" + URLEncoder.encode("Pwd", "UTF-8") + "=" + URLEncoder.encode(Pwd, "UTF-8");
            data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
            data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }

    public boolean deletToDB(String link, String Id) {
        System.out.println("커넥트 디비 링크와 아이디 확인 : "+link+"--------"+Id);
        try {
            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }


    public boolean insertAddress(String link, String Id, String ZipCode, String Address, String BuildName) {
        try {
            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
            data += "&" + URLEncoder.encode("ZipCode", "UTF-8") + "=" + URLEncoder.encode(ZipCode, "UTF-8");
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8");
            data += "&" + URLEncoder.encode("BuildName", "UTF-8") + "=" + URLEncoder.encode(BuildName, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("주소 추가에 실패했습니다. : " + e.getMessage());
            return false;
        }
    }
    public boolean selectCatch(String catch_seq){
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(strUrl.selectCatch);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catch_seq));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            String [] tagNm = {"catch_yn" , "isyn_flag"};

            String [] catch_seq_arr = jsonToStringArr(responsedata, "catchDatas", tagNm);
            System.out.println(catch_seq_arr.toString());

            if("Y".equalsIgnoreCase(catch_seq_arr[0]) && "Y".equalsIgnoreCase(catch_seq_arr[1])) {    // catch_yn : Y , isyn_flag :Y

                // CATCH_LIST DATA
                String [] catchTagNm = {"store_seq", "menu_price"};
                CatchVo.setJsonCatch(responsedata, catchTagNm);

                // STORE_MEMBER
                String JsonStoreNm = getStoreAddress(strUrl.getStoreNm , CatchVo.catchStoreSeq);
                catchTagNm = new String[]{"store_name"};
                CatchVo.setJsonCatch(JsonStoreNm, catchTagNm);

                // ADDLIST_STORE
                String JsonStoreAdd = getStoreAddress(strUrl.getStoreAdd , CatchVo.catchStoreSeq);
                catchTagNm = new String[]{"add_data"};
                CatchVo.setJsonCatch(JsonStoreAdd, catchTagNm);

                // CATCHED_MENU_LIST DATA
                String jsonMenuList = getCatchMenuList(catch_seq);
                catchTagNm = new String[]{"menu_name", "menu_person"};
                CatchVo.setJsonCatch(jsonMenuList, catchTagNm);
                // 메뉴 리스트 배열 초기화 때문에 마지막에 둬야함.

                System.out.println("Success Catched");
                return true;
            }
            else if("N".equalsIgnoreCase(catch_seq_arr[0]) && "Y".equalsIgnoreCase(catch_seq_arr[1])){  // catch_yn : N , isyn_flag :Y
                System.out.println("Failed Catched");
                return false;
            }
            else{
                System.out.println("데이터 에러 확인 : "+catch_seq_arr[0]+"///"+catch_seq_arr[1]+"////"+catch_seq_arr.length);
                System.out.println("Data Error");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return false;
        }
    }

    public boolean selectIsCatched(String catch_seq){
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(strUrl.selectCatch);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catch_seq));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            String [] tagNm = {"catch_yn"};

            String [] catch_seq_arr = jsonToStringArr(responsedata, "catchDatas", tagNm);
            System.out.println(catch_seq_arr.toString());

            if("Y".equalsIgnoreCase(catch_seq_arr[0])) {    // catch_yn : Y
                System.out.println("Success Catched");
                return true;
            }
            else if("N".equalsIgnoreCase(catch_seq_arr[0])){  // catch_yn : N
                System.out.println("Failed Catched");
                return false;
            }
            else{
                System.out.println("selectIsCatched 에러 확인 : "+catch_seq_arr[0]+"////"+catch_seq_arr.length);
                System.out.println("Data Error");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return false;
        }
    }

    public boolean selectCatchInfo(String catch_seq){   // 캐치 완료 후 캐치 정보 받는 코드
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(strUrl.selectCatch);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catch_seq));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            String [] tagNm = {"catch_yn" , "menu_yn" , "isyn_flag"};

            String [] catch_seq_arr = jsonToStringArr(responsedata, "catchDatas", tagNm);
            System.out.println(catch_seq_arr.toString());

            if("Y".equalsIgnoreCase(catch_seq_arr[0]) && "Y".equalsIgnoreCase(catch_seq_arr[1]) && "Y".equalsIgnoreCase(catch_seq_arr[2])) {    // catch_yn : Y , menu_yn : Y , isyn_flag :Y

                // CATCH_LIST DATA
                String [] catchTagNm = {"store_seq", "menu_price"};
                CatchVo.setJsonCatch(responsedata, catchTagNm);

                // STORE_MEMBER
                String JsonStoreNm = getStoreAddress(strUrl.getStoreNm , CatchVo.catchStoreSeq);
                catchTagNm = new String[]{"store_name"};
                CatchVo.setJsonCatch(JsonStoreNm, catchTagNm);

                // ADDLIST_STORE
                String JsonStoreAdd = getStoreAddress(strUrl.getStoreAdd , CatchVo.catchStoreSeq);
                catchTagNm = new String[]{"add_data"};
                CatchVo.setJsonCatch(JsonStoreAdd, catchTagNm);

                // CATCHED_MENU_LIST DATA
                String jsonMenuList = getCatchMenuList(catch_seq);
                catchTagNm = new String[]{"menu_name", "menu_person"};
                CatchVo.setJsonCatch(jsonMenuList, catchTagNm);
                // 메뉴 리스트 배열 초기화 때문에 마지막에 둬야함.

                System.out.println("Success Catched");
                return true;
            }
            else if("Y".equalsIgnoreCase(catch_seq_arr[0]) && "N".equalsIgnoreCase(catch_seq_arr[1]) && "Y".equalsIgnoreCase(catch_seq_arr[2])){  // catch_yn : N , menu_yn : N , isyn_flag :Y
                System.out.println("Failed Menu Selected : " + catch_seq_arr[1]);
                return false;
            }
            else{
                System.out.println("데이터 에러 확인 : "+catch_seq_arr[0]+"///"+catch_seq_arr[2]+"////"+catch_seq_arr.length);
                System.out.println("Data Error");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return false;
        }
    }

    public void getCatchSeq(String user_seq){
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(strUrl.getCatchSeq);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("user_seq", user_seq));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            String [] tagNm = {"catch_seq"};

            String [] catch_seq = jsonToStringArr(responsedata, "get_catch_seq", tagNm);
            System.out.println(catch_seq);
            CatchVo.catchSeq = catch_seq[0];

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public String getCatchMenuList(String catch_seq){
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(strUrl.getCatchMenuList);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catch_seq));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responseJsonMenuList = httpclient.execute(httppost, responseHandler);

            return responseJsonMenuList;

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        return null;
    }

    private String [] jsonToStringArr(String jsonString, String Tag , String [] tagNm){

        String TAG_JSON = Tag; // Json Tag
        // Json 구분자

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // Tag 로 구분
            String [] data = new String[tagNm.length];

            for(int j = 0; j < jsonArray.length(); j++){    // json array

                JSONObject item = jsonArray.getJSONObject(i);

                for(int i=0; i<tagNm.length; i++){          // json data parsing
                    data[i] = item.getString(tagNm[i]);
                }

            }

            return data;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deletCatch(String ID) {
        System.out.println("디비 안 id 체크 : "+ID);
        try {
            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");

            URL url = new URL(strUrl.deleteCatch);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }

        }catch (Exception e){
            System.out.println("catch start failed : " + e.getMessage());
            return false;
        }
    }

    public boolean insertCatch(String ID, String numPerson, String numMoney) {
        try {
            String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
            data += "&" + URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(numPerson, "UTF-8");
            data += "&" + URLEncoder.encode("money", "UTF-8") + "=" + URLEncoder.encode(numMoney, "UTF-8");

            URL url = new URL(strUrl.insertCatch);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴


/*
         JSONObject jsonObject = new JSONObject(phpEcho);


            if(jsonObject.getString("s_f_flag").equals("succeed") || jsonObject.getString("s_f_flag").equals("succee")){
                CatchVo.catchSeq = jsonObject.getString("catch_seq");
                System.out.println("catch ssssseq : " + CatchVo.catchSeq);
                return true;
            }
            else{
                return false;
            }
*/

            if(phpEcho.equalsIgnoreCase("succeed")||phpEcho.equalsIgnoreCase("succee")){
                return true;
            }
            else{
                return false;
            }

        }catch (Exception e){
            System.out.println("catch start failed : " + e.getMessage());
            return false;
        }
    }

    public void stopCatch() {
        try {

            URL url = new URL(strUrl.stopCatch);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자" + phpEcho);

        }catch (Exception e){
            System.out.println("catch start failed : " + e.getMessage());
        }
    }

    public boolean noticeSubmit(String link,String Id,String Cate, String Title, String Content) {
        System.out.println("커넥트 디비는 도착했니?");
        try {
            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
            data += "&" + URLEncoder.encode("Cate", "UTF-8") + "=" + URLEncoder.encode(Cate, "UTF-8");
            data += "&" + URLEncoder.encode("Title", "UTF-8") + "=" + URLEncoder.encode(Title, "UTF-8");
            data += "&" + URLEncoder.encode("Content", "UTF-8") + "=" + URLEncoder.encode(Content, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }

    public boolean modifyNotice(String link, String seq, String title, String content) {
        System.out.println("커넥트 디비 링크와 seq 확인 : "+seq+"--------"+title+"--------"+content);
        try {


            String data = URLEncoder.encode("seq", "UTF-8") + "=" + URLEncoder.encode(seq, "UTF-8");
            data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
            data += "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }

    public boolean noticeDel(String link, String seq) {
        System.out.println("커넥트 디비 링크와 아이디 확인 : "+link+"--------"+seq);
        try {
            String data = URLEncoder.encode("seq", "UTF-8") + "=" + URLEncoder.encode(seq, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }

    public boolean addReadNum(String link,String seq) {
        System.out.println("커넥트 디비는 도착했니 seq:"+seq);
        try {

            String data = URLEncoder.encode("seq", "UTF-8") + "=" + URLEncoder.encode(seq, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }


}
