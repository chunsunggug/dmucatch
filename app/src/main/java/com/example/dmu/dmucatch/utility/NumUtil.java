package com.example.dmu.dmucatch.utility;

public class NumUtil {

    // 클릭 시 인원 수 증가 || onclick plus person one.
    public int upPerson(int i_person){
        if(i_person<1){
            i_person=1;
        }else {
            i_person = i_person + 1;
        }
        return i_person;
    }

    // 클릭 시 인원 수 감소 || onclick minus person one.
    public int dnPerson(int i_person){
        if(i_person<=1){
            i_person=1;
        }else {
            i_person = i_person-1;
        }
        return i_person;
    }

    // 클릭 시 500원 증가 || onclick plus 500won.
    public int upMoney(int i_money){
        i_money = i_money+500;
        return i_money;
    }

    // 클릭 시 500원 감소 || onclick minus 500won.
    public int dnMoney(int i_money){
        if(i_money<=1000){
            i_money=1000;
            // 최소 주문 1000
        }else {
            i_money = i_money-500;
        }
        return i_money;
    }
}
