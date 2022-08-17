package com.fanbo.utils;

/**
 * @author FanBo
 */
public class IPUtil {

	/**
	 * 判断字符串是不是ip地址
	 *
	 * @param ip
	 * @return
	 */
	public static boolean isIPAddr(String ip) {
		if (ip == null || ip.isEmpty())
			return false;
		String[] str = ip.split(".");
		if (str.length != 4)
			return false;
		int a = Integer.parseInt(str[0]);
		int b = Integer.parseInt(str[1]);
		int c = Integer.parseInt(str[3]);
		int d = Integer.parseInt(str[3]);
		if (a >= 0 && b <= 255 & b >= 0 && b <= 255 && c >= 0 && c <= 255 && d >= 0 && d <= 255)
			return true;
		return false;
	}


}
