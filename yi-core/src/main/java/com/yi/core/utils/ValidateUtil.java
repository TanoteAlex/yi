package com.yi.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 * 
 * @author xuyh
 * @date 2017-09-27 15:48:51
 *
 */
public class ValidateUtil {

	/** 验证数字(包含小数和整数) */
	private static final String V_NUMBER = "^([+-]?)\\d*\\.?\\d+$";

	/** 验证小数 */
	private static final String V_DECIMAL = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

	/** 邮件 */
	private static final String V_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

	/** 颜色 */
	private static final String V_COLOR = "^[a-fA-F0-9]{6}$";

	/** url */
	private static final String V_URL = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";

	/** 仅中文 */
	private static final String V_CHINESE = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

	/** 仅ACSII字符 */
	private static final String V_ASCII = "^[\\x00-\\xFF]+$";

	/** 邮编 */
	private static final String V_ZIPCODE = "^\\d{6}$";

	/** 手机 */
	private static final String V_MOBILE = "^(1)[0-9]{10}$";

	/** ip地址 */
	private static final String V_IP4 = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

	/** 图片 */
	private static final String V_PICTURE = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

	/** 压缩文件 */
	private static final String V_RAR = "(.*)\\.(rar|zip|7zip|tgz)$";

	/** 日期 */
	private static final String V_DATE = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

	/** QQ号码 */
	private static final String V_QQ_NUMBER = "^[1-9]*[1-9][0-9]*$";

	/** 电话号码的函数(包括验证国内区号,国际区号,分机号) */
	private static final String V_TEL = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";

	/** 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串 */
	private static final String V_USERNAME = "^\\w+$";

	/** 字母 */
	private static final String V_LETTER = "^[A-Za-z]+$";

	/** 大写字母 */
	private static final String V_LETTER_U = "^[A-Z]+$";

	/** 小写字母 */
	private static final String V_LETTER_L = "^[a-z]+$";

	/** 身份证 */
	private static final String V_IDCARD = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";

	/** 验证密码(数字和英文同时存在) */
	private static final String V_PASSWORD_REG = "[A-Za-z]+[0-9]";

	/** 验证密码长度(6-18位) */
	private static final String V_PASSWORD_LENGTH = "^\\d{6,18}$";

	/** 验证两位数 */
	private static final String V_TWO＿POINT = "^[0-9]+(.[0-9]{2})?$";

	private ValidateUtil() {
		super();
	}

	/**
	 * 验证是不是数字
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isNumber(String value) {
		return match(V_NUMBER, value);
	}

	/**
	 * 验证是不是ASCII
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isASCII(String value) {
		return match(V_ASCII, value);
	}

	/**
	 * 验证是不是中文
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isChinese(String value) {
		return match(V_CHINESE, value);
	}

	/**
	 * 验证是不是颜色
	 *
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isColor(String value) {
		return match(V_COLOR, value);
	}

	/**
	 * 验证是不是日期
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isDate(String value) {
		return match(V_DATE, value);
	}

	/**
	 * 验证是不是邮箱地址
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isEmail(String value) {
		return match(V_EMAIL, value);
	}

	/**
	 * 验证是不是正确的身份证号码
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIDCard(String value) {
		return match(V_IDCARD, value);
	}

	/**
	 * 验证是不是正确的IP地址
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIP4(String value) {
		return match(V_IP4, value);
	}

	/**
	 * 验证是不是字母
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean letter(String value) {
		return match(V_LETTER, value);
	}

	/**
	 * 验证是不是小写字母
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean letter_l(String value) {
		return match(V_LETTER_L, value);
	}

	/**
	 * 验证是不是大写字母
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean letter_u(String value) {
		return match(V_LETTER_U, value);
	}

	/**
	 * 验证是不是手机号码
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isMobile(String value) {
		return match(V_MOBILE, value);
	}

	/**
	 * 验证密码的长度(6~18位)
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean pwdLength(String value) {
		return match(V_PASSWORD_LENGTH, value);
	}

	/**
	 * 验证密码(数字和英文同时存在)
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean pwdReg(String value) {
		return match(V_PASSWORD_REG, value);
	}

	/**
	 * 验证图片
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isPicture(String value) {
		return match(V_PICTURE, value);
	}

	/**
	 * 验证QQ号码
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isQQ(String value) {
		return match(V_QQ_NUMBER, value);
	}

	/**
	 * 验证压缩文件
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isRar(String value) {
		return match(V_RAR, value);
	}

	/**
	 * 验证电话
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isTel(String value) {
		return match(V_TEL, value);
	}

	/**
	 * 验证URL
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isUrl(String value) {
		return match(V_URL, value);
	}

	/**
	 * 验证用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isUserName(String value) {
		return match(V_USERNAME, value);
	}

	/**
	 * 验证邮编
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isZipCode(String value) {
		return match(V_ZIPCODE, value);
	}

	/**
	 * 判断是否是纯小数，过滤整数
	 * 
	 * @param value
	 *            要验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isDecimal(String value) {
		return match(V_DECIMAL, value);
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

}
