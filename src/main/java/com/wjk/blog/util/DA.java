package com.wjk.blog.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * @author wang.jk
 * @date 2020/1/8
 */
public class DA {
    public static void main(String[] args) {
   /*  //   String a="^[0-9]{3}\\-[0-9]{3}\\-[0-9]{4}$";
        String a="^[1][3,4,5,7,8][0-9]{9}$";
        Scanner sc=new Scanner(System.in);
        String b=sc.nextLine();
        if (b.matches(a)){
            System.out.println(1);
        }else {
            System.out.println(0);
        }*/


        Date date=new Date();

        System.out.println(date);
//        Calendar calendar=Calendar.getInstance();
//      int d= (calendar.get(Calendar.DATE) +20);
//      calendar.set(Calendar.DAY_OF_YEAR,d);
//      simpleDateFormat.format(calendar.getTime());
//        System.out.println(calendar.getTime());

    }
}
