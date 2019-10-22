package com.yi.core.utils;

/**
 * 上下标 字符转义
 * 
 * @author xuyh
 * @date 2017年5月8日
 */
public class EscapeUtil {

	/**
	 * 转义上标
	 * 
	 * @param str
	 * @return String
	 */
	public static String escapeSuperscript(String str) {
		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer("");
		for (char ch : arr) {
			sb.append(escapeSup(String.valueOf(ch)));
		}
		return sb.toString();
	}

	/**
	 * 转义下标
	 * 
	 * @param str
	 * @return String
	 */
	public static String escapeSubscript(String str) {
		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer("");
		for (char ch : arr) {
			sb.append(escapeSub(String.valueOf(ch)));
		}
		return sb.toString();
	}

	/**
	 * 转义上标 由于1,2,3转义无效 特用上标¹²³代替
	 * 
	 * @param String
	 *            str 需要转义上标
	 * @return String
	 */
	private static String escapeSup(String str) {
		switch (str) {
		case "0":
			str = "&#8304;";
			break;
		case "1":
			str = "¹";
			break;
		case "2":
			str = "²";
			break;
		case "3":
			str = "³";
			break;
		case "4":
			str = "&#8308;";
			break;
		case "5":
			str = "&#8309;";
			break;
		case "6":
			str = "&#8310;";
			break;
		case "7":
			str = "&#8311;";
			break;
		case "8":
			str = "&#8312;";
			break;
		case "9":
			str = "&#8313;";
			break;
		case "+":
			str = "&#8314;";
			break;
		case "-":
			str = "&#8315;";
			break;
		case "=":
			str = "&#8316;";
			break;
		case "(":
			str = "&#8317;";
			break;
		case ")":
			str = "&#8318;";
			break;
		case "n":
			str = "&#8319;";
			break;
		default:
			break;
		}
		return str;
	}

	/**
	 * 转义下标
	 * 
	 * @param String
	 *            str 需要转义的下标
	 * @return String
	 */
	private static String escapeSub(String str) {
		switch (str) {
		case "0":
			str = "&#8320;";
			break;
		case "1":
			str = "&#8321;";
			break;
		case "2":
			str = "&#8322;";
			break;
		case "3":
			str = "&#8323;";
			break;
		case "4":
			str = "&#8324;";
			break;
		case "5":
			str = "&#8325;";
			break;
		case "6":
			str = "&#8326;";
			break;
		case "7":
			str = "&#8327;";
			break;
		case "8":
			str = "&#8328;";
			break;
		case "9":
			str = "&#8329;";
			break;
		case "+":
			str = "&#8330;";
			break;
		case "-":
			str = "&#8331;";
			break;
		case "=":
			str = "&#8332;";
			break;
		case "(":
			str = "&#8333;";
			break;
		case ")":
			str = "&#8334;";
			break;
		case "a":
			str = "&#8336;";
			break;
		case "e":
			str = "&#8337;";
			break;
		case "o":
			str = "&#8338;";
			break;
		case "x":
			str = "&#8339;";
			break;
		case "ə":
			str = "&#8340;";
			break;
		case "h":
			str = "&#8341;";
			break;
		case "k":
			str = "&#8342;";
			break;
		case "l":
			str = "&#8343;";
			break;
		case "m":
			str = "&#8344;";
			break;
		case "n":
			str = "&#8345;";
			break;
		case "p":
			str = "&#8346;";
			break;
		case "s":
			str = "&#8347;";
			break;
		case "t":
			str = "&#8348;";
			break;
		default:
			break;
		}
		return str;
	}

}
