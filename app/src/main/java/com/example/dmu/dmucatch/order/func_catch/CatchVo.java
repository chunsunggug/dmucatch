package com.example.dmu.dmucatch.order.func_catch;

import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class CatchVo {

    // data from CATCH_LIST
    public static String catchSeq;
    public static String catchStoreSeq;
    public static String catchPrice;

    // data from CATCHED_MENU_LIST
    public static String [] catchMenuNm;
    public static String [] menuPerson;

    // data from STORE_MEMBER;
    public static String storeNm;

    // data from ADDLIST_STORE;
    public static String storeAdd;

    public static void setCatchMenuNm(int size) {
        CatchVo.catchMenuNm = new String[size];
    }

    public static void setMenuPerson(int size) {
        CatchVo.menuPerson = new String[size];
    }

    // catch json data parsing
    public static void setJsonCatch(String jsonCatch, String [] TAG_NAME) throws Exception {

        String TAG_JSON="catchDatas"; // Json Tag

        try {
            JSONObject jsonObject = new JSONObject(jsonCatch);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // split with TAG_JSON
            int listLength = jsonArray.length();
            setCatchMenuNm(listLength); // 메뉴 네임 리스트
            setMenuPerson(listLength);  // 메뉴 인분 리스트

            for(int i=0; i < listLength; i++){

                JSONObject item = jsonArray.getJSONObject(i);

                for(int j = 0; j < TAG_NAME.length; j++){

                    String tempData = item.getString(TAG_NAME[j]); // column data

                    if("store_seq".equals(TAG_NAME[j])){
                        CatchVo.catchStoreSeq = tempData;
                    }
                    else if("menu_price".equals(TAG_NAME[j])){
                        CatchVo.catchPrice = tempData;
                    }
                    else if("menu_name".equals(TAG_NAME[j])){
                        CatchVo.catchMenuNm[i] = tempData;
                    }
                    else if("menu_person".equals(TAG_NAME[j])){
                        CatchVo.menuPerson[i] = tempData;
                    }
                    else if("store_name".equals(TAG_NAME[j])){
                        CatchVo.storeNm = tempData;
                    }
                    else if("add_data".equals(TAG_NAME[j])){
                        CatchVo.storeAdd = tempData;
                    }
                    else{
                        throw new Exception();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void refreshCatchVo(){
        CatchVo.catchStoreSeq = null;
        CatchVo.catchPrice = null;
        CatchVo.storeAdd = null;
    }

    public static String getStrCatchData(){
        String strCatch = "";

        strCatch += "가게 이름 : " + CatchVo.storeNm + "님 \n";
        strCatch += "주소 : " + CatchVo.storeAdd + " \n";

        strCatch += "메뉴 : \n";
        for(int i=0; i < CatchVo.catchMenuNm.length; i++){
            strCatch += CatchVo.catchMenuNm[i] + " " + CatchVo.menuPerson[i] + " 개 \n";
        }

        strCatch += "\n";
        strCatch += "총합 : " + CatchVo.catchPrice + " 원 \n";

        return strCatch;
    }

}
