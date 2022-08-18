package com.fanbo.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证验证的工具（支持5位或18位省份证）
 * 身份证号码结构：
 * 17位数字和1位校验码：6位地址码数字，8位生日数字，3位出生时间顺序号，1位校验码。
 * 地址码（前6位）：表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。
 * 出生日期码，（第七位 至十四位）：表示编码对象出生年、月、日，按GB按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 顺序码（第十五位至十七位）：表示在同一地址码所标示的区域范围内，对同年、同月、同日出生的人编订的顺序号，
 * 顺序码的奇数分配给男性，偶数分配给女性。 
 * 校验码（第十八位数）：
 * 十七位数字本体码加权求和公式 s = sum(Ai*Wi), i = 0,,16，先对前17位数字的权求和；   
 *  Ai:表示第i位置上的身份证号码数字值.Wi:表示第i位置上的加权因.Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2；
 * 计算模 Y = mod(S, 11) 
 * 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2 
 */
public class IdCardUtil {

    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }

    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2};

    /**
     * 身份证验证
     * param s  号码内容
     *
     * @return 是否有效 null和"" 都是false
     */
    public static boolean isIDCard(String certNo) {
        if (certNo == null || (certNo.length() != 15 && certNo.length() != 18))
            return false;
        final char[] cs = certNo.toUpperCase().toCharArray();
        //校验位数
        int power = 0;
        for (int i = 0; i < cs.length; i++) {
            if (i == cs.length - 1 && cs[i] == 'X')
                break;//最后一位可以 是X或x
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1) {
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }

        //校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(certNo.substring(0, 2)))) {
            return false;
        }

        //校验年份
        String year = certNo.length() == 15 ? getIdcardCalendar() + certNo.substring(6, 8) : certNo.substring(6, 10);

        final int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
            return false;//1900年的PASS，超过今年的PASS

        //校验月份
        String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10, 12);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12) {
            return false;
        }

        //校验天数      
        String day = certNo.length() == 15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31)
            return false;

        //校验"校验码"
        if (certNo.length() == 15)
            return true;
        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    private static int getIdcardCalendar() {
        //TODO这里获取时间时候，在校验15位身份证时候获取年份有误,建议转化为18位后进行校验
        GregorianCalendar curDay = new GregorianCalendar();
        int curYear = curDay.get(Calendar.YEAR);
        int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
        return year2bit;
    }

    /**
     * 身份证15位转换18位
     */
    public static String idCard15To18(String ID15) {
        String ID18 = "";
        if (ID15.length() == 18) {
            ID18 = ID15.toUpperCase();
        }
        if (ID15.length() == 15) {
            int[] w = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
            char[] A = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
            String ID17 = ID15.substring(0, 6) + "19" + ID15.substring(6, 15);
            int[] ID17Array;
            ID17Array = new int[17];
            for (int i = 0; i < 17; i++) {
                ID17Array[i] = Integer.parseInt(ID17.substring(i, i + 1));
            }
            int s = 0;
            for (int i = 0; i < 17; i++) {
                s = s + ID17Array[i] * w[i];
            }
            s = s % 11;
            ID18 = ID17 + Character.toString(A[s]);
        }
        return ID18;
    }

    /**
     * 校验15位和18位身份证号码
     *
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        if (idCard.length() == 15) {
            idCard = idCard15To18(idCard);
        }
        if (idCard.length() == 18) {
            return isIDCard(idCard);
        }
        return false;
    }

    /**
     * 根据身份证判断男女	add by yf 2016-4-22 11:19:03
     * -- 1 男	;  2 女
     *
     * @param idCard
     * @return
     */
    public static String getSexFromIdCard(String idCard) {
        try {
            if (idCard == null || "".equals(idCard) || " ".equals(idCard)) {
                return "1";
            }
            idCard = idCard.trim();
            if (idCard.length() == 15) {
                idCard = idCard15To18(idCard);
            }
            int sexInteger = Integer.valueOf(idCard.charAt(16) + "");
            if (sexInteger % 2 == 0) {
                return "2";//判断被保人为女性
            } else {
                return "1";//判定被保人为男性
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }

    /**
     * 获取出生日期	add by yf 2016-5-4 20:12:53
     *
     * @param idCard 身份证
     * @return yyyyMMdd
     */
    public static String getBirthDayFromIdCard(String idCard) {
        if (idCard.length() == 15) {
            idCard = idCard15To18(idCard);
        }
        return idCard.substring(6, 14);
    }

    public static String getBirthDayNoYearFromIdCard(String idCard) {
        if (idCard.length() == 15) {
            idCard = idCard15To18(idCard);
        }
        return idCard.substring(10, 14);
    }

    public static String getBirthDay(String idCard) {
        String birth = getBirthDayFromIdCard(idCard);
        return birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6);
    }

    /**
     * 根据身份证号获取生日
     *
     * @param idCard
     * @return
     */
    public static int getAgeByIdCard(String idCard) {
        if (idCard.length() == 15) {
            idCard = idCard15To18(idCard);
        }
        Calendar ca = Calendar.getInstance();
        int nowYear = ca.get(Calendar.YEAR);
        int nowMonth = ca.get(Calendar.MONTH) + 1;
        int today = ca.get(Calendar.DAY_OF_MONTH);
        int IDYear = Integer.parseInt(idCard.substring(6, 10));
        int IDMonth = Integer.parseInt(idCard.substring(10, 12));
        int IDDay = Integer.parseInt(idCard.substring(12, 14));

        int age = nowYear - IDYear - 1;
        if (IDMonth < nowMonth || (IDMonth == nowMonth && IDDay <= today)) {
            age++;
        }
        return age;
    }

    /**
     * 计算周岁
     *
     * @param
     * @return
     */
    public static int getAgeFromIdCard(String idCard) {
        // 先截取到字符串中的年、月、日
        int selectYear = Integer.parseInt(idCard.substring(6, 10));
        int selectMonth = Integer.parseInt(idCard.substring(10, 12));
        int selectDay = Integer.parseInt(idCard.substring(12, 14));
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
                age = age - 1;
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                    age = age - 1;
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age;
    }

}