package com.example.dmu.dmucatch.member;

public class DivUser {

    public String ckUser(String checkName){
        if(checkName.equals("user")){
            return "name";
        }else{
            return "store_name";
        }
    }

}
