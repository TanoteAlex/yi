package com.yi.core.utils;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 汉语 拼音相关工具类
 * 
 * @author xuyh
 *
 */
public class PinyinUtils {

	private static HanyuPinyinOutputFormat hanyuFormat = new HanyuPinyinOutputFormat();

	static {
		hanyuFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hanyuFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanyuFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	/**
	 * 得到汉字的首字母
	 *
	 * @param source
	 * @return
	 */
	public static String getPinYinHeaderChar(String source) {
		if (StringUtils.isBlank(source)) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char word = source.charAt(i);
			if (Character.toString(word).matches("[\\u4E00-\\u9FA5]")) {// PinyinHelper
				String[] pinYinArr = PinyinHelper.toHanyuPinyinStringArray(word);
				result.append(pinYinArr[0].charAt(0));
			} else {
				// 非汉字不进行转换，直接添加
				result.append(word);
			}
		}
		return result.toString();
	}

}
