package com.yi.base.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * TODO 待优化 图片压缩，裁剪，旋转，水印等 工具类
 * 
 * @author xuyh
 *
 */
public class ThumbnailatorUtils {

	/** 列表页默认宽度 小图 */
	public static final Integer SMALL_WIDTH = 300;
	/** 列表页默认高度 小图 */
	public static final Integer SMALL_HEIGHT = 300;
	/** 详情页默认宽度 大图 */
	public static final Integer LARGE_WIDTH = 800;
	/** 详情页默认高度 大图 */
	public static final Integer LARGE_HEIGHT = 800;
	// /** 详情交互页默认宽度 中图 */
	// public static final Integer MIDDLE_WIDTH = 800;
	// /** 详情交互页默认高度 中图 */
	// public static final Integer MIDDLE_HEIGHT = 576;
	/** 详情交互页默认宽度 中图 */
	public static final Integer MIDDLE_WIDTH = 750;
	/** 详情交互页默认高度 中图 */
	public static final Integer MIDDLE_HEIGHT = 650;

	/** 列表图 宽高后缀 */
	public static final String SMALL_IMAGE_SUFFIX = "_300_300_s";
	/** 详情介绍图 宽高后缀 */
	public static final String MIDDLE_IMAGE_SUFFIX = "_750_650_m";
	/** 详情主图 宽高后缀 */
	public static final String LARGE_IMAGE_SUFFIX = "_800_800_l";

	/**
	 * 生成图片路径
	 * 
	 * @param originalPath
	 * @return
	 */
	private static String generateImagePath(String originalPath, String imageSuffix) {
		if (StringUtils.isNotBlank(originalPath)) {
			String filepath = FilenameUtils.getFullPath(originalPath);
			String baseName = FilenameUtils.getBaseName(originalPath);
			String fileExt = FilenameUtils.getExtension(originalPath);
			String imagePath = filepath + baseName;
			if (StringUtils.isNotBlank(imageSuffix)) {
				imagePath = imagePath + imageSuffix;
			}
			if (StringUtils.isNotBlank(fileExt)) {
				imagePath = imagePath + "." + fileExt;
			}
			return imagePath;
		}
		return null;
	}

	/**
	 * 生成小图路径
	 * 
	 * @param originalPath
	 * @return
	 */
	public static String generateSmallImagePath(String originalPath) {
		return generateImagePath(originalPath, SMALL_IMAGE_SUFFIX);
	}

	/**
	 * 生成中图路径
	 * 
	 * @param originalPath
	 * @return
	 */
	public static String generateMiddleImagePath(String originalPath) {
		return generateImagePath(originalPath, MIDDLE_IMAGE_SUFFIX);
	}

	/**
	 * 生成大图路径
	 * 
	 * @param originalPath
	 * @return
	 */
	public static String generateLargeImagePath(String originalPath) {
		return generateImagePath(originalPath, LARGE_IMAGE_SUFFIX);
	}

	/**
	 * 指定大小进行缩放 （若尺寸为空默认为300*300，默认按照原图比例）
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param width
	 *            宽度 默认300
	 * @param height
	 *            高度 默认300
	 * @param keepAspectRatio
	 *            true 按原图比例缩放</br>
	 *            false不按照原图比例缩放
	 * @param outFilepath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void compressBySize(String originalPath, Integer width, Integer height, Boolean keepAspectRatio, String outFilepath) throws IOException {
		// 默认宽度
		if (width == null) {
			width = SMALL_WIDTH;
		}
		// 默认高度
		if (height == null) {
			height = SMALL_HEIGHT;
		}
		// 默认按照原图比例
		if (keepAspectRatio == null) {
			keepAspectRatio = Boolean.TRUE;
		}
		OutputStream os = new FileOutputStream(outFilepath);
		Thumbnails.of(originalPath).size(width, height).outputQuality(0.7f).keepAspectRatio(keepAspectRatio).toOutputStream(os);
	}

	/**
	 * 指定大小进行缩放（ 小图300*300，默认按照原图比例）
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param outFilepath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void compressBySmallSize(String originalPath, String outFilepath) throws IOException {
		if (StringUtils.isBlank(outFilepath)) {
			outFilepath = generateSmallImagePath(originalPath);
		}
		compressBySize(originalPath, SMALL_WIDTH, SMALL_WIDTH, Boolean.TRUE, outFilepath);
	}

	/**
	 * 指定大小进行缩放（ 中图750*540，默认按照原图比例）
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param outFilepath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void compressByMiddleSize(String originalPath, String outFilepath) throws IOException {
		if (StringUtils.isBlank(outFilepath)) {
			outFilepath = generateMiddleImagePath(originalPath);
		}
//		compressBySize(originalPath, MIDDLE_WIDTH, MIDDLE_HEIGHT, true, outFilepath);
		// 图片尺寸不变，压缩图片文件大小
		OutputStream os = new FileOutputStream(outFilepath);
		Thumbnails.of(originalPath).scale(1f).outputQuality(0.7f).toOutputStream(os);
	}

	/**
	 * 指定大小进行缩放 （大图800*800， 默认按照原图比例）
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param outFilepath
	 *            输出文件路径
	 * @throws Exception
	 */
	public static void compressByLargeSize(String originalPath, String outFilepath) throws IOException {
		if (StringUtils.isBlank(outFilepath)) {
			outFilepath = generateLargeImagePath(originalPath);
		}
//		 compressBySize(originalPath, LARGE_WIDTH, LARGE_HEIGHT, true, outFilepath);
		 // 图片尺寸不变，压缩图片文件大小
		OutputStream os = new FileOutputStream(outFilepath);
		Thumbnails.of(originalPath).scale(1f).outputQuality(0.7f).toOutputStream(os);
	}

	/**
	 * 按照比例进行缩放
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param scale
	 *            比例
	 * @param outFilepath
	 *            输出文件路径
	 * @throws IOException
	 */
	public void compressByScale(String originalPath, Double scale, String outFilepath) throws IOException {
		Thumbnails.of(originalPath).scale(scale).toFile(outFilepath);
	}

	/**
	 * 按角度旋转
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param angle
	 *            角度 正数：顺时针 负数：逆时针
	 * @param outFilepath
	 *            输出文件路径
	 * @throws IOException
	 */
	public void rotateByAngle(String originalPath, Double angle, String outFilepath) throws IOException {
		Thumbnails.of(originalPath).rotate(angle).toFile(outFilepath);
	}

	/**
	 * 水印
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param width
	 *            输出宽
	 * @param height
	 *            输出高
	 * @param positions
	 *            水印位置
	 * @param watermarkPath
	 *            水印图片
	 * @param opacity
	 *            透明度
	 * @param outputQuality
	 *            输出质量
	 * @param outFilepath
	 *            输出路径
	 * @throws IOException
	 */
	public void watermark(String originalPath, Integer width, Integer height, Positions positions, String watermarkImage, Float opacity, Float outputQuality, String outFilepath)
			throws IOException {
		/**
		 * watermark(位置，水印图，透明度)
		 */
		Thumbnails.of(originalPath).size(width, height).watermark(positions, ImageIO.read(new File(watermarkImage)), opacity).outputQuality(outputQuality).toFile(outFilepath);
	}

	/**
	 * 根据区域裁剪 图片中心400*400的区域
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param regionWidth
	 *            区域宽
	 * @param regionHeight
	 *            区域高
	 * @param width
	 *            输出宽
	 * @param height
	 *            输出高
	 * @param cropPath
	 *            输出文件路径
	 * @throws IOException
	 */
	public void cropByRegion(String originalPath, Integer regionWidth, Integer regionHeight, Integer width, Integer height, String cropPath) throws IOException {
		Thumbnails.of(originalPath).sourceRegion(Positions.CENTER, regionWidth, regionHeight).size(width, height).keepAspectRatio(false).toFile(cropPath);
	}

	/**
	 * 根据坐标裁剪
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param x
	 *            坐标X
	 * @param y
	 *            坐标Y
	 * @param regionWidth
	 *            区域宽
	 * @param regionHeight
	 *            区域高
	 * @param width
	 *            输出宽
	 * @param height
	 *            输出高
	 * @param cropPath
	 *            输出文件路径
	 * @throws IOException
	 */
	public void cropByCoordinate(String originalPath, Integer x, Integer y, Integer regionWidth, Integer regionHeight, Integer width, Integer height, String cropPath)
			throws IOException {
		Thumbnails.of(originalPath).sourceRegion(x, y, regionHeight, regionHeight).size(width, height).keepAspectRatio(false).toFile(cropPath);
	}

	/**
	 * 转化图像格式
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param width
	 *            输出宽 如1280
	 * @param height
	 *            输出高 如1024
	 * @param format
	 *            输出格式 如png
	 * @param outFilepath
	 *            输出文件
	 * @throws IOException
	 */
	public void ConvertImageFormat(String originalPath, int width, int height, String format, String outFilepath) throws IOException {
		Thumbnails.of(originalPath).size(width, height).outputFormat(format).toFile(outFilepath);
	}

	/**
	 * 输出到OutputStream
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param width
	 *            输出宽
	 * @param height
	 *            输出高
	 * @param os
	 *            输出流对象
	 * 
	 * @throws IOException
	 */
	public static void toOutputStream(String originalPath, int width, int height, OutputStream os) throws IOException {
		Thumbnails.of(originalPath).size(width, height).toOutputStream(os);
	}

	/**
	 * 输出到BufferedImage
	 * 
	 * @param originalPath
	 *            原图路径
	 * @param formatName
	 *            格式
	 * @param output
	 *            输出文件
	 * @throws IOException
	 */
	public static void toBufferedImage(String originalPath, String formatName, File output) throws IOException {
		BufferedImage thumbnail = Thumbnails.of(originalPath).asBufferedImage();
		ImageIO.write(thumbnail, formatName, output);
	}

}
