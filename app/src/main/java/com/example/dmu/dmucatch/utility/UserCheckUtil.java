package com.example.dmu.dmucatch.utility;


public class UserCheckUtil {
    public static boolean isNoFoundUser(String str) {
        return (str.equalsIgnoreCase("No Such User Found") || str.equalsIgnoreCase("No Such User Foun"));
    } // DB 에서 select 후 유저가 없는 경우

}
