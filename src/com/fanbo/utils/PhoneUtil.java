package com.fanbo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号工具
 * */
public class PhoneUtil {

    //隐藏手机号中间4位，如：138****2988
    public static String hiddenPhone(String phone){
        return phone != null && phone.length() == 11 ? phone.substring(0, 3) + "****" + phone.substring(7) : "****";
    }

    /**
     * 验证手机号码
     *
     * @param mobile
     * @return [0-9]{5,9}
     */

    public static boolean isMobile(String mobile) {
        boolean flag;
        try {
//			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Pattern p = Pattern.compile("^[1][3456789][0-9]{9}$");
            Matcher m = p.matcher(mobile);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
