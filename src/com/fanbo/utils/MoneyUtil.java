package com.fanbo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Description: 钱相关的工具
 * @Author: FanBo
 */
public class MoneyUtil {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    //long分转换成元（除100）
    public static String transMoney(Long money) {
        if (money == null) {
            return "0.00";
        }
        return new BigDecimal(money).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP) + "";
    }

    public static String transMoney(Long money, Integer mode) {
        if (money == null) {
            return "0.00";
        }
        return BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, mode == null ? BigDecimal.ROUND_HALF_EVEN : mode) + "";
    }

    /**
     * @param money
     * @Description : BigDecimal转字符串
     * @Return :
     */
    public static String transMoney(BigDecimal money) {
        if (money == null) {
            return "";
        }
        return money.toString();
    }


    /**
     * BigDecimal转分
     *
     * @param money
     * @return
     */
    public static long transMoneyCent(BigDecimal money) {
        if (money == null) {
            return 0L;
        }
        return money.divide(new BigDecimal(0.01), 2, RoundingMode.HALF_UP).longValue();
    }

    //double分转换成元（除100）
    public static String transMoney(Double money) {
        if (money == null) {
            return "0.00";
        }
        return new BigDecimal(money).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP) + "";
    }

    //double元转换成long的分（除0.01）
    public static Long transMoneyToLong(Double money) {
        if (money == null) {
            return 0L;
        }
        return new BigDecimal(money).divide(new BigDecimal(0.01), 2, RoundingMode.HALF_UP).longValue();
    }

    // money是long除数，divideNum是被除数，小数点后面保留2位
    public static String moneyDivideToString(Long money, Long divideNum) {
        if (divideNum == 0) {
            return "0.00";
        }
        return new BigDecimal(money).divide(new BigDecimal(divideNum), 2, RoundingMode.HALF_UP) + "";
    }

    // money是long除数，divideNum是被除数，保留2位小数
    public static Double moneyDivideToDouble(Long money, Long divideNum) {
        if (divideNum == 0) {
            return 0d;
        }
        return new BigDecimal(money).divide(new BigDecimal(divideNum), 2, RoundingMode.HALF_UP).doubleValue();
    }

    // money是double除数，divideNum是被除数
    public static String moneyDivide(Double money, Double divideNum) {
        if (divideNum == 0 || money == null) {
            return "0.00";
        }
        return new BigDecimal(money).divide(new BigDecimal(divideNum), 2, RoundingMode.HALF_UP) + "";
    }

    //money乘数，multiplyNum被乘数
    public static String moneyMultiply(Double money, Double multiplyNum) {
        if (multiplyNum == null || money == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        double result = new BigDecimal(money).multiply(new BigDecimal(multiplyNum)).doubleValue();
        return df.format(result);
    }

    //money乘数，multiplyNum被乘数
    public static String moneyMultiply(Long money, Double multiplyNum) {
        if (multiplyNum == null || money == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        double result = new BigDecimal(money).multiply(new BigDecimal(multiplyNum)).doubleValue();
        return df.format(result);
    }

    //双精度的钱，除某数之后返回双精度的钱
    public static Double transDoubleMoney(Double money, Long divideNum) {
        if (divideNum == 0) {
            return 0d;
        }
        return new BigDecimal(money).divide(new BigDecimal(divideNum), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public static String formatDoubleMoney(Double money) {

        return df.format(money);
    }

    /**
     * @param money
     * @param divideNum
     * @Description : 除法运算
     * @Return :
     */
    public static BigDecimal moneyDivideToBigDecimal(BigDecimal money, BigDecimal divideNum) {
        if (money == null || divideNum == null || money.compareTo(BigDecimal.ZERO) == 0 || divideNum.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("0.00");
        }
        return money.divide(divideNum, 2, RoundingMode.HALF_UP);
    }

    /**
     * @param money
     * @param divideNum
     * @Description : 除法运算
     * @Return :
     */
    public static BigDecimal moneyDivideToBigDecimal(int money, int divideNum) {
        if (divideNum == 0) {
            return new BigDecimal("0.00");
        }
        return new BigDecimal(money).divide(new BigDecimal(divideNum), 2, RoundingMode.HALF_UP);
    }

    /**
     * 小数转化成百分数
     *
     * @param
     * @return
     */
    public static String formatBigDecimal(BigDecimal money) {
        if (money == null) {
            return "";
        }
        return money.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%";
    }
}
