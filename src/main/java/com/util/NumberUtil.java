package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class NumberUtil {
	public static Pattern patternInt = Pattern.compile("(^[+]?([1-9][0-9]*))");
	public static Pattern patternDouble = Pattern.compile("(^[+]?([0-9]\\d*\\.?\\d{0,2}))");

	/**
	 * 是否是数字
	 * 
	 * @Description: TODO
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int i = str.length();
		do {
			if (!Character.isDigit(str.charAt(i)))
				return false;
			i--;
		} while (i >= 0);

		return true;
	}

	/**
	 * 是否是正整数
	 */
	public static boolean isNumericInt(String str) {
		if (str == null) {
			return false;
		}
		if (str.equals("0")) {
			return true;
		}
		return patternInt.matcher(str).matches();
	}

	/**
	 * 是否是double
	 */
	public static boolean isNumericDouble(String str) {
		if (str == null) {
			return false;
		}
		return (patternDouble.matcher(str).matches()) || (isNumericInt(str));
	}

	/**
	 * 是否是boolean
	 * 
	 * @Description: TODO
	 */
	public static boolean isBoolean(String str) {
		if (str == null) {
			return false;
		}

		return (str.equals("true")) || (str.equals("false"));
	}

	/**
	 * 是否是日期
	 */
	public static boolean isDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.parse(str);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isDateTime(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format.parse(str);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static String getBillNo() {
		Date currentTime = new Date();
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
		Random random = new Random();
		String billno = formatter2.format(currentTime) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9)
				+ random.nextInt(9) + random.nextInt(9) + random.nextInt(9);
		return billno;
	}

	public static String getBillNo(String uid) {
		Date currentTime = new Date();
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(currentTime);
		long noNum = Long.parseLong(date) * 1000;
		Random random = new Random();
		String billno = noNum + "" + uid + random.nextInt(9) + random.nextInt(9);

		return billno;
	}

	public static long getLongVal(String value) {
		return Long.parseLong(value);
	}
	
	public static String toChinese(String string) {
		if(string.equals("10")){
			return "十";
		}
        String[] s1 = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }

        return result;

    }
}
