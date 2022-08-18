package com.fanbo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author FanBo
 */
public class BirthdayUtil {

    /**
     * 得到出生的天数
     *
     * @param strDate
     * @return
     */
    public static int getDaysByBirth(String strDate, SimpleDateFormat simpleDateFormat) {
        Date birthDay;
        try {
            birthDay = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        if (calendar.before(birthDay)) { //出生日期晚于当前时间，无法计算
            return -1;
        }

        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_YEAR); //当前日期
        calendar.setTime(birthDay);
        int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_YEAR);

        return dayOfMonthNow - dayOfMonthBirth;
    }

    /**
     * 计算年龄
     *
     * @param strDate
     * @return
     */
    public static int getAgeByBirth(String strDate, SimpleDateFormat simpleDateFormat) {

        Date birthDay;
        try {
            birthDay = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        if (calendar.before(birthDay)) { //出生日期晚于当前时间，无法计算
            return -1;
        }
        int yearNow = calendar.get(Calendar.YEAR);  //当前年份
        int monthNow = calendar.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH); //当前日期
        calendar.setTime(birthDay);
        int yearBirth = calendar.get(Calendar.YEAR);
        int monthBirth = calendar.get(Calendar.MONTH);
        int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }


}
