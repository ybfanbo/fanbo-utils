package com.fanbo.utils;


import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 * */
public class StringUtil {

    public static boolean isNull(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotNull(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }


    public static String decodeBase64Str(String input) {
        byte[] debytes = Base64.getDecoder().decode(input);
        return new String(debytes);
    }


    //十六进制转换成字符串
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    //生成随机数字和字母,
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * @Description 将名字的中间位替换成* 比如 华晨宇替换成华*宇  杨幂替换成杨*
     * @Param [name]
     * @return java.lang.String
     **/
    public static String replaceName(String name){
        name = name.replaceAll("([^x00-xff]|\\w)([^x00-xff]|\\w)?([^x00-xff]|\\w)*", "$1*$3");
        return name;

    }


}
