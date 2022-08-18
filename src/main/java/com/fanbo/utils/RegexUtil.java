package com.fanbo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 类名:正则校验工具类
 */
public class RegexUtil {

	/**
	 * 验证邮箱地址是否正确
	 *
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 *
	 * @param mobile
	 * @return [0-9]{5,9}
	 */

	public static boolean isMobile(String mobile) {
		boolean flag = false;
		try {
//			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Pattern p = Pattern.compile("^[1][3456789][0-9]{9}$");
			Matcher m = p.matcher(mobile);
			flag = m.matches();
			System.out.println("flag=" + flag);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否是数字
	 *
	 * @param number
	 * @return
	 */
	public static boolean isNum(String number) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[0-9]*$");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否包含数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isIncludeNum(String str) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile(".*[0-9]+.*");
			Matcher m = p.matcher(str);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 字符串中汉字的个数
	 *
	 * @param str
	 * @return
	 */
	public static int countOfChineseCharacter(String str) {
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		return count;
	}

	/**
	 * 带长度的判断是否是数字
	 *
	 * @param number
	 * @param length
	 * @return
	 */
	public static boolean isNumHaveLength(String number, int length) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[0-9]{" + length + "}$");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验时间格式yyyy-MM-dd
	 *
	 * @param yyyy_MM_dd
	 */
	public static boolean checkDateFormatOfyyyy_MM_dd(String yyyy_MM_dd) {
		boolean flag = false;
		try {
			String regexString = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
			Pattern regex = Pattern.compile(regexString);
			Matcher matcher = regex.matcher(yyyy_MM_dd);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验时间格式yyyy-MM-dd 00:00:00
	 *
	 * @param effectiveTime
	 */
	public static boolean checkDateFormatOfEffectiveTime(String effectiveTime) {
		boolean flag = false;
		try {
			String regexString = "^[0-9]{4}-[0-9]{2}-[0-9]{2} 00:00:00$";
			Pattern regex = Pattern.compile(regexString);
			Matcher matcher = regex.matcher(effectiveTime);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验时间格式yyyy年MM月dd日
	 *
	 * @param yyyy_MM_ddChinese
	 */
	public static boolean checkDateFormatOfyyyyMMddChinese(String yyyy_MM_ddChinese) {
		boolean flag = false;
		try {
			String regexString = "^[0-9]{4}年[0-9]{2}月[0-9]{2}日$";
			Pattern regex = Pattern.compile(regexString);
			Matcher matcher = regex.matcher(yyyy_MM_ddChinese);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验时间格式yyyyMMdd
	 *
	 * @param yyyyMMdd
	 */
	public static boolean checkDateFormatOfyyyyMMdd(String yyyyMMdd) {
		boolean flag = false;
		try {
			String regexString = "^[0-9]{4}[0-9]{2}[0-9]{2}$";
			Pattern regex = Pattern.compile(regexString);
			Matcher matcher = regex.matcher(yyyyMMdd);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 正则表达式过滤内容只保留数字、字母、中文、标点符号、下划线
	 *
	 * @param str
	 * @return
	 */
	public static String filter(String str) {
		return str.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？_“”]+", "");

	}

	/*由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。
	 * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。
	 * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。
	 * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。*/
	public static int getStrLength(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	/**
	 * 判断是否是数字(整数和浮点数)
	 *
	 * @param number
	 * @return
	 */
	public static boolean isNumOrFloat(String number) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("[1-9]\\d*\\.?\\d*");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 正则：数字和小数点
	 *
	 * @param num
	 * @return
	 */
	public static boolean checkNumAndPoint(String num) {
		boolean flag = false;
		try {
			String regexString = "^([0-9]\\d*\\.?\\d*)|(0\\.\\d*[0-9])$";
			Pattern regex = Pattern.compile(regexString);
			Matcher matcher = regex.matcher(num);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @return boolean
	 * @Description 是否包含中文
	 * @Param [str]
	 **/
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}


	private static String toString(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	public static String getPriceString(String price) {
		price = "拾捌元整";//大写壹佰贰拾伍万肆仟叁佰玖
		char[] chars = price.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			switch (toString(chars[i])) {
				case "零":
					buffer.append(0);
					break;
				case "壹":
					buffer.append(1);
					break;
				case "贰":
					buffer.append(2);
					break;
				case "叁":
					buffer.append(3);
					break;
				case "肆":
					buffer.append(4);
					break;
				case "伍":
					buffer.append(5);
					break;
				case "陆":
					buffer.append(6);
					break;
				case "柒":
					buffer.append(7);
					break;
				case "捌":
					buffer.append(8);
					break;
				case "玖":
					buffer.append(9);
					break;
				case "元":
					buffer.append(".");
					break;
				case "整":
					buffer.append("00");
					break;
			}
		}

		return buffer.toString();
	}

	/**
	 * @return java.lang.String
	 * @Description 将名字的中间位替换成* 比如 华晨宇替换成华*宇  杨幂替换成杨*
	 * @Param [name]
	 **/
	public static String replaceName(String name) {
		name = name.replaceAll("([^x00-xff]|\\w)([^x00-xff]|\\w)?([^x00-xff]|\\w)*", "$1*$3");
		return name;

	}

	/**
	 * 验证出生日期(yyyy-MM-dd)是否正确
	 * <p>
	 * 该表达式的匹配规则（按以下序号为顺序）：
	 * 1.匹配除了2月份之外的1-30日
	 * 2.若1无法匹配，则匹配1，3，5，7，8，10，12月份的31日
	 * 3.若2无法匹配，则匹配2月份的1-28日
	 * 4.若以上都无法匹配，那只可能剩下一天，那就是闰年2月份的最后一天2月29日
	 *
	 * @param birthday
	 * @return
	 */
	public static boolean checkBirthDay(String birthday) {
		boolean flag = false;
		try {
			String check = "((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(birthday);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}


}
