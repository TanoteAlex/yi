package com.yi.core.utils;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayerCodeUtils {

	private final static Logger LOG = LoggerFactory.getLogger(ObjectPathUtils.class);

	private LayerCodeUtils() {

	}

	public static final ThreadLocal<DecimalFormat> decimalFormatThree = ThreadLocal.withInitial(() -> new DecimalFormat("000"));

	public static final ThreadLocal<DecimalFormat> decimalFormatFive = ThreadLocal.withInitial(() -> new DecimalFormat("00000"));

	/**
	 * 编码生成器
	 *
	 * @param parentLayerCode
	 *            父级编码
	 * @param levelCodeStrings
	 *            所在层级的所有编码
	 * @return
	 */
	public static String calParentLevelCodeThree(String parentLayerCode, List<String> levelCodeStrings) {
		int code = 0;
		if (CollectionUtils.isNotEmpty(levelCodeStrings)) {
			int pos = parentLayerCode != null ? parentLayerCode.length() : 0;
			boolean[] ddd = new boolean[100];
			for (String levelCode : levelCodeStrings) {
				String ssString = levelCode.substring(pos);
				if (NumberUtils.isNumber(ssString)) {
					ddd[Integer.parseInt(ssString)] = true;
				}
			}
			for (int i = 0; i < ddd.length; i++) {
				if (!ddd[i]) {
					code = i;
					break;
				}
			}
		}
		String layerCode;
		if (parentLayerCode != null) {
			layerCode = parentLayerCode + decimalFormatThree.get().format(code);
		} else {
			layerCode = decimalFormatThree.get().format(code);
		}
		return layerCode;
	}

	/**
	 * 编码生成器
	 *
	 * @param parentLayerCode
	 *            父级编码
	 * @param levelCodeStrings
	 *            所在层级的所有编码
	 * @return
	 */
	public static String calParentLevelCodeFive(String parentLayerCode, List<String> levelCodeStrings) {
		int code = 0;
		if (CollectionUtils.isNotEmpty(levelCodeStrings)) {
			int pos = parentLayerCode != null ? parentLayerCode.length() : 0;
			boolean[] ddd = new boolean[10000];
			for (String levelCode : levelCodeStrings) {
				String ssString = levelCode.substring(pos);
				if (NumberUtils.isNumber(ssString)) {
					ddd[Integer.parseInt(ssString)] = true;
				}
			}
			for (int i = 0; i < ddd.length; i++) {
				if (!ddd[i]) {
					code = i;
					break;
				}
			}
		}
		String layerCode;
		if (parentLayerCode != null) {
			layerCode = parentLayerCode + decimalFormatFive.get().format(code);
		} else {
			layerCode = decimalFormatFive.get().format(code);
		}
		return layerCode;
	}
}