package com.yi.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 替换工具类
 * 
 * @author xuyh
 *
 */
public class ReplaceUtil {

	private static final char REPLACEMENT_CHAR = '*';

	public static String replace(String source, ReplacementScheme... schemes) {
		if (StringUtils.isNotBlank(source)) {
			char[] arr = source.toCharArray();
			for (int i = 0; i < arr.length; i++) {
				for (ReplacementScheme scheme : schemes) {
					if (scheme.getStart() <= i && scheme.getEnd() > i) {
						arr[i] = scheme.getReplacement();
					}
				}
			}
			return String.valueOf(arr);
		}
		return source;
	}

	public static String replace(String source, int start, int end, char replacement) {
		if (StringUtils.isNotBlank(source)) {
			char[] arr = source.toCharArray();
			for (int i = 0; i < arr.length; i++) {
				if (start <= i && end > i) {
					arr[i] = replacement;
				}
			}
			return String.valueOf(arr);
		}
		return source;
	}

	public static String replaceChineseName(String source) {
		if (StringUtils.isNotBlank(source)) {
			if (source.length() == 2) {
				ReplaceUtil.replace(source, 0, 1, REPLACEMENT_CHAR);
			} else if (source.length() > 3) {
				ReplaceUtil.replace(source, 0, 2, REPLACEMENT_CHAR);
			}
		}
		return source;
	}

	public static String replaceEnglishName(String source) {
		if (StringUtils.isNotBlank(source)) {
			if (source.length() == 2) {
				return ReplaceUtil.replace(source, 0, 1, REPLACEMENT_CHAR);
			} else if (source.length() > 3) {
				return ReplaceUtil.replace(source, 0, 2, REPLACEMENT_CHAR);
			}
		}
		return source;
	}

	public static String replacePhone(String source) {
		if (StringUtils.isNotBlank(source)) {
			return ReplaceUtil.replace(source, 3, 7, REPLACEMENT_CHAR);
		}
		return source;
	}

	public static String replaceBankNo(String source) {
		if (StringUtils.isNotBlank(source)) {
			return ReplaceUtil.replace(source, 4, 12, REPLACEMENT_CHAR);
		}
		return source;
	}

}
