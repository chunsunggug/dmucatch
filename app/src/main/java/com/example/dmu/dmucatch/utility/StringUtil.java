package com.example.dmu.dmucatch.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isNull(String str) {
        return (str == null || (str.trim().length()) == 0 || str.equals(""));
    } // null 이면 return true

    public static boolean isFailed(String str) {
        return str.equalsIgnoreCase("fail");
    }

    public static boolean isEmail(String str) {

        String ptn = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        // email 정규식

        return Pattern.matches(ptn, str);
    }

    public static boolean isValidPwd(String str) {

        String ptn = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$";
        // 대소문자 구분 숫자 특수문자  조합 9 ~ 12 자리

        return Pattern.matches(ptn, str);
    }

    public static boolean isPhNumber(String str){
        String ptn = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        // 01(0,1,6-9) - 3자리 || 4자리 - 4자리

        return Pattern.matches(ptn, str);
    }

    public static boolean alertYN(Context context, String title, String content)
    {
        System.out.println("컨텍스트 : "+context);
        final Boolean[] yn = new Boolean[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        yn[0] = true;
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        yn[0] = false;
                        //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();

        return yn[0];
    } // 내부 클래스에 final 변수 선언 후 show error 떨어짐

    public static void toastStr(Context context, String content)
    {
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
    }

}
