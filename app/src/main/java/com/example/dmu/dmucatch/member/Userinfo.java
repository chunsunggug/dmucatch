package com.example.dmu.dmucatch.member;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Userinfo implements Serializable {
    public static String id;
    public static String name;
    public static String phone;
    public static String address;
    public static String class_code;
    public static int user_seq;

    public static int getUser_seq() {
        return user_seq;
    }

    public static void setUser_seq(int user_seq) {
        Userinfo.user_seq = user_seq;
    }


    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Userinfo.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Userinfo.name = name;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Userinfo.phone = phone;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Userinfo.address = address;
    }

    public static String getClass_code() {
        return class_code;
    }

    public static void setClass_code(String class_code) {
        Userinfo.class_code = class_code;
    }

}
