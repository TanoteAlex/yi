package com.yi.core.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yi.core.commodity.domain.entity.Commodity;

import sun.awt.SunHints;

/**
 * 二维码生成工具类
 * 
 * @author Administrator
 *
 */
public class QRCodeUtil extends LuminanceSource {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

	// 二维码颜色
	private static final int BLACK = 0xFF000000;
	// 二维码颜色
	private static final int WHITE = 0xFFFFFFFF;

	private final BufferedImage image;
	private final int left;
	private final int top;

	public QRCodeUtil(BufferedImage image) {
		this(image, 0, 0, image.getWidth(), image.getHeight());
	}

	public QRCodeUtil(BufferedImage image, int left, int top, int width, int height) {
		super(width, height);
		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		if (left + width > sourceWidth || top + height > sourceHeight) {
			throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
		}
		for (int y = top; y < top + height; y++) {
			for (int x = left; x < left + width; x++) {
				if ((image.getRGB(x, y) & 0xFF000000) == 0) {
					// = white
					image.setRGB(x, y, 0xFFFFFFFF);
				}
			}
		}
		this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
		this.image.getGraphics().drawImage(image, 0, 0, null);
		this.left = left;
		this.top = top;
	}

	@Override
	public byte[] getRow(int y, byte[] row) {
		if (y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("Requested row is outside the image: " + y);
		}
		int width = getWidth();
		if (row == null || row.length < width) {
			row = new byte[width];
		}
		image.getRaster().getDataElements(left, top + y, width, 1, row);
		return row;
	}

	@Override
	public byte[] getMatrix() {
		int width = getWidth();
		int height = getHeight();
		int area = width * height;
		byte[] matrix = new byte[area];
		image.getRaster().getDataElements(left, top, width, height, matrix);
		return matrix;
	}

	@Override
	public boolean isCropSupported() {
		return true;
	}

	@Override
	public LuminanceSource crop(int left, int top, int width, int height) {
		return new QRCodeUtil(image, this.left + left, this.top + top, width, height);
	}

	@Override
	public boolean isRotateSupported() {
		return true;
	}

	@Override
	public LuminanceSource rotateCounterClockwise() {
		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
		BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = rotatedImage.createGraphics();
		g.drawImage(image, transform, null);
		g.dispose();
		int width = getWidth();
		return new QRCodeUtil(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
	}

	/**
	 * @param matrix
	 * @return
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 生成二维码图片
	 *
	 * @param matrix
	 * @param format
	 * @param file
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	/**
	 * 生成二维码图片流
	 *
	 * @param matrix
	 * @param format
	 * @param stream
	 * @throws IOException
	 */
	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	/**
	 * 根据内容，生成指定宽高、指定格式的二维码图片
	 *
	 * @param text
	 *            内容
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param format
	 *            图片格式
	 * @return 生成的二维码图片路径
	 * @throws Exception
	 */
	public static String generateQRCode(String text, int width, int height, String format, String pathName) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 白边大小，取值范围0~4
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		File outputFile = new File(pathName);
		if (!outputFile.exists()) {
			outputFile.mkdir();
		}
		writeToFile(bitMatrix, format, outputFile);
		return pathName;
	}

	/**
	 * 输出二维码图片流
	 *
	 * @param text
	 *            二维码内容
	 * @param width
	 *            二维码宽
	 * @param height
	 *            二维码高
	 * @param format
	 *            图片格式eg: png, jpg, gif
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 */
	/*
	 * public static void generateQRCode(String text, int width, int height, String
	 * format, HttpServletResponse response) throws Exception {
	 * Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType,
	 * Object>(); hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 指定编码格式
	 * hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);// 指定纠错等级
	 * hints.put(EncodeHintType.MARGIN, 1); // 白边大小，取值范围0~4 BitMatrix bitMatrix =
	 * new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,
	 * hints); writeToStream(bitMatrix, format, response.getOutputStream()); }
	 */

	/**
	 * 解析指定路径下的二维码图片
	 *
	 * @param filePath
	 *            二维码图片路径
	 * @return
	 */
	public static String parseQRCode(String filePath) {
		String content = "";
		try {
			File file = new File(filePath);
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new QRCodeUtil(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			MultiFormatReader formatReader = new MultiFormatReader();
			Result result = formatReader.decode(binaryBitmap, hints);

			logger.info("result 为：" + result.toString());
			logger.info("resultFormat 为：" + result.getBarcodeFormat());
			logger.info("resultText 为：" + result.getText());
			// 设置返回值
			content = result.getText();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return content;
	}

	public static String combineCodeAndPicToBase64(File backPicPathFile, File codeFile, Commodity commodity) {
		ImageOutputStream imOut = null;
		try {
			BufferedImage buffImg = new BufferedImage(400, 550, BufferedImage.TYPE_INT_RGB);
			BufferedImage big = ImageIO.read(backPicPathFile);
			BufferedImage small = ImageIO.read(codeFile);
			// 得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			// 设置文字清晰度
			g.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
			g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
			g.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_ON);
			g.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_QUALITY);
			// 生成白色背景色
			g.setBackground(Color.WHITE);
			// 填充整个屏幕
			g.fillRect(0, 0, 400, 550);
			g.drawImage(big, 0, 0, 400, 400, null);
			g.drawImage(small, 30, 420, 100, 100, null);
			g.setColor(Color.black);
			g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			// 标题默认显示10个字符
			String commodityName = commodity.getCommodityShortName().length() > 12 ? commodity.getCommodityShortName().substring(0, 12) + "..." : commodity.getCommodityShortName();
			g.drawString(commodityName, 140, 450);
			g.setColor(Color.red);
			g.drawString("￥ " + commodity.getCurrentPrice(), 140, 480);
			g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			g.setColor(Color.decode("0x17994f"));
			g.drawString("长按识别二维码或扫一扫购买", 140, 510);
			g.dispose();
			// 为了保证大图背景不变色，formatName必须为"png"
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(buffImg, "png", imOut);
			InputStream in = new ByteArrayInputStream(bs.toByteArray());

			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			String base64 = Base64.getEncoder().encodeToString(bytes);
			System.out.println(base64);

			return "data:image/jpeg;base64," + base64;
		} catch (Exception e) {
			logger.error("合成base64失败");
		}
		return null;
	}

	/*
	 * public static void main(String[] args) { // 随机生成验证码 String text =
	 *
	 * "http://test.h5.tangutsz.com/#/LoginPage;preMemberId=64?openId=oiR4m58nQlBPJ-AedXe4hVwVQqtA&unionId=onw_8531kgRvjeEWKG9-aaaDdc4s";
	 * System.out.println("随机码： " + text); // 二维码图片的宽 int width = 180; // 二维码图片的高
	 * int height = 180; // 二维码图片的格式 String format = "png"; try { // 生成二维码图片，并返回图片路径
	 * String pathName = generateQRCode(text, width, height, format,
	 * "G:/upload/1545700783332.jpg"); System.out.println("生成二维码的图片路径： " +
	 * pathName); String content = parseQRCode(pathName);
	 * System.out.println("解析出二维码的图片的内容为： " + content); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

}
