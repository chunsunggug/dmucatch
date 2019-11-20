package com.example.dmu.dmucatch.utility;

public class DaumAddressVo {
    public static String zipCode;
    public static String address;
    public static String buidName;

    public static String getZipCode() {
        return zipCode;
    }

    public static void setZipCode(String zipCode) {
        DaumAddressVo.zipCode = zipCode;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        DaumAddressVo.address = address;
    }

    public static String getBuidName() {
        return buidName;
    }

    public static void setBuidName(String buidName) {
        DaumAddressVo.buidName = buidName;
    }

}
