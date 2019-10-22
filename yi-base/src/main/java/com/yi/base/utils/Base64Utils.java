package com.yi.base.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * base64 工具类
 * 
 * @author xuyh
 *
 */
public class Base64Utils {

	public static MultipartFile base64ToMultipartFile(String base64) throws Exception {
		String[] baseStrs = base64.split(",");
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		byte[] b = decoder.decodeBuffer(baseStrs[1]);
//		byte[] b = Base64.getEncoder().encode(baseStrs[1].getBytes());
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {
				b[i] += 256;
			}
		}
		return new Base64DecodedMultipartFile(b, baseStrs[0]);
	}

	public static byte[] base64ToByte(String base64Str) throws Exception{
		String[] baseStrs = base64Str.split(",");
		// Base64解码
		 sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		 byte[] fileByte = decoder.decodeBuffer(baseStrs[1]);
//		byte[] fileByte = Base64.getDecoder().decode(baseStrs[1].getBytes());
		for (int i = 0; i < fileByte.length; ++i) {
			if (fileByte[i] < 0) {
				fileByte[i] += 256;
			}
		}
		return fileByte;
	}

}
