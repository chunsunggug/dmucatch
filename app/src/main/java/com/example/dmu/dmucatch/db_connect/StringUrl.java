package com.example.dmu.dmucatch.db_connect;

public class StringUrl {

    private final String server = "http://ci2019catch.dongyangmirae.kr";
    private final String user = "u";
    private final String store = "s";
    private final String admin = "a";
    private final String kakaoApi = "kakaoApi";
    private final String Catch = "catch";
    private final  String userCatch = "userCatch";
    private final String notice ="notice";


    public String login = server + "/" + user + "/" +"login.php";
    public String signUp = server + "/" + user + "/" + "signup.php";
    public String userModify = server + "/" + user + "/" + "modify.php";
    public String userDel = server + "/" + user + "/" + "delete.php";
    public String userAddress = server + "/" + user + "/" + "findaddress.php";
    public String getMenu = server + "/" + store + "/" + "getmenu.php";
    public String AddressApi = server + "/" + kakaoApi +"/" + "address.php";
    public String setAddress = server + "/" + kakaoApi +"/" + "setaddress.php";
    public String getAddress = server + "/" + kakaoApi +"/" + "getaddress.php";
    public String modiAddress = server + "/" + kakaoApi +"/" + "modiaddress.php";
    public String insertCatch = server + "/" + Catch + "/" + userCatch + "/" +"insert.php";
    public String selectCatch = server + "/" + Catch + "/" + userCatch + "/" + "start.php";
    //public String selectIsCatched = server + "/" + Catch + "/" + userCatch + "/" + "selectIsCatched.php";
    public String stopCatch = server + "/" + Catch + "/" + userCatch + "/" + "stop.php";
    public String deleteCatch = server + "/" + Catch + "/" + userCatch + "/" +"deleteCatch.php";
    public String getCatchSeq = server + "/" + Catch + "/" + userCatch + "/" +"get_catch_seq.php";
    public String getStoreAdd = server + "/" + Catch + "/" + userCatch + "/" +"getStoreAdd.php";
    public String getStoreNm = server + "/" + Catch + "/" + userCatch + "/" +"getStoreNm.php";
    public String getCatchMenuList = server + "/" + Catch + "/" + userCatch + "/" +"getCatchMenuList.php";
    public String myOrder = server + "/" + Catch + "/" + userCatch + "/" +"myorder.php";
    public String myOrdersearch = server + "/" + Catch + "/" + userCatch + "/" +"myordersearch.php";
    public String submit = server + "/" + admin + "/" +"submit.php";
    public String noticelist = server+"/"+notice+"/"+"noticelist.php";
    public String AddReadnum = server+"/"+notice+"/"+"addreadnum.php";
    public String noticeDel = server+"/"+notice+"/"+"delnotice.php";
    public String noticeModi = server+"/"+notice+"/"+"modinotice.php";
    public String noticesearch = server+"/"+notice+"/"+"noticesearch.php";

}
