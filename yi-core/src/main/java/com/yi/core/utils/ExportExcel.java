//package com.yi.core.utils;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import javax.servlet.http.HttpServletResponse;
//
//import com.yi.core.activity.service.impl.CouponServiceImpl;
//import com.yihz.common.utils.RandomUtils;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFRichTextString;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.CellRangeAddress;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///***
// * 
// * @ClassName ExportExcel
// * @Author jstin
// * @Date 2018/12/28 20:53
// * @Version 1.0
// **/
//public class ExportExcel {
//	private final Logger LOG = LoggerFactory.getLogger(ExportExcel.class);
//
//	/**
//	 * 显示的导出表的标题
//	 */
//	private String title;
//	/**
//	 * 导出表的列名
//	 */
//	private String[] rowName;
//
//	private List<Object[]> dataList = new ArrayList<Object[]>();
//
//	HttpServletResponse response;
//
//	/**
//	 * 构造方法，传入要导出的数据
//	 * 
//	 * @param title
//	 * @param rowName
//	 * @param dataList
//	 * @param response
//	 */
//	public ExportExcel(String title, String[] rowName, List<Object[]> dataList, HttpServletResponse response) {
//		this.dataList = dataList;
//		this.rowName = rowName;
//		this.title = title;
//		this.response = response;
//	}
//
//	/**
//	 * 导出数据
//	 *
//	 * @throws Exception
//	 */
//	public void export() throws Exception {
//		try {
//			// 创建工作簿对象
//			HSSFWorkbook workbook = new HSSFWorkbook();
//			// 创建工作表
//			HSSFSheet sheet = workbook.createSheet(title);
//
//			// 产生表格标题行
//			HSSFRow rowm = sheet.createRow(0);
//			HSSFCell cellTiltle = rowm.createCell(0);
//			// 设置高度
//			rowm.setHeight((short) (25 * 35));
//
//			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
//			// 获取列头样式对象
//			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
//			// 单元格样式对象
//			HSSFCellStyle style = this.getStyle(workbook);
//			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.length - 1)));
//			cellTiltle.setCellStyle(columnTopStyle);
//			cellTiltle.setCellValue(title);
//			// 定义所需列数
//			int columnNum = rowName.length;
//			// 在索引2的位置创建行(最顶端的行开始的第二行)
//			HSSFRow rowRowName = sheet.createRow(1);
//			/**
//			 * 设置高度
//			 */
//			rowRowName.setHeight((short) (25 * 25));
//
//			// 将列头设置到sheet的单元格中
//			for (int n = 0; n < columnNum; n++) {
//				// 创建列头对应个数的单元格
//				HSSFCell cellRowName = rowRowName.createCell(n);
//				// 设置列头单元格的数据类型
//				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
//				HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
//				// 设置列头单元格的值
//				cellRowName.setCellValue(text);
//				// 设置列头单元格样式
//				cellRowName.setCellStyle(columnTopStyle);
//			}
//
//			// 将查询出的数据设置到sheet对应的单元格中
//			for (int i = 0; i < dataList.size(); i++) {
//				// 遍历每个对象
//				Object[] obj = dataList.get(i);
//				// 创建所需的行数
//				HSSFRow row = sheet.createRow(i + 2);
//				// 设置高度
//				row.setHeight((short) (25 * 20));
//
//				for (int j = 0; j < obj.length; j++) {
//					// 设置单元格的数据类型
//					HSSFCell cell = null;
//					if (j == 0) {
//						cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
//						cell.setCellValue(i + 1);
//					} else {
//						cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
//						if (!"".equals(obj[j]) && obj[j] != null) {
//							// 设置单元格的值
//							cell.setCellValue(obj[j].toString());
//						}
//					}
//					// 设置单元格样式
//					cell.setCellStyle(style);
//				}
//			}
//			// 让列宽随着导出的列长自动适应
//			for (int colNum = 0; colNum < columnNum; colNum++) {
//				int columnWidth = sheet.getColumnWidth(colNum) / 256;
//				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
//					HSSFRow currentRow;
//					// 当前行未被使用过
//					if (sheet.getRow(rowNum) == null) {
//						currentRow = sheet.createRow(rowNum);
//					} else {
//						currentRow = sheet.getRow(rowNum);
//					}
//					if (currentRow.getCell(colNum) != null) {
//						HSSFCell currentCell = currentRow.getCell(colNum);
//						if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
//							int length = currentCell.getStringCellValue().getBytes().length;
//							if (columnWidth < length) {
//								columnWidth = length;
//							}
//						}
//					}
//				}
//				if (colNum == 0) {
//					sheet.setColumnWidth(colNum, (columnWidth - 2) * 128);
//				} else {
//					sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
//				}
//
//			}
//
//			if (workbook != null) {
//				try {
//					String fileName = "Excel-" + RandomUtils.randomString(11, RandomUtils.RANDRULE.RAND_LOWER) + ".xls";
//					String headStr = "attachment; filename=\"" + fileName + "\"";
//					// 设置响应的编码
//					// 下面三行是关键代码，处理乱码问题
//					String filename = URLEncoder.encode(fileName, "UTF-8");
//					response.reset();
//					response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + filename + "");
//					response.setContentType("application/vnd.ms-excel");
//					workbook.write(response.getOutputStream());
//					/*
//					 * out = new FileOutputStream("E:/" + new
//					 * SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
//					 * workbook.write(out);
//					 */
//					// out.close();
//				} catch (IOException e) {
//					LOG.error("生成Excel失败 ==》{}", e);
//				}
//			}
//
//		} catch (Exception e) {
//			LOG.error("生成Excel失败 ==》{}", e);
//		}
//
//	}
//
//	/*
//	 * 列头单元格样式
//	 */
//	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
//
//		// 设置字体
//		HSSFFont font = workbook.createFont();
//		// 设置字体大小
//		font.setFontHeightInPoints((short) 11);
//		// 字体加粗
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 设置字体名字
//		font.setFontName("Courier New");
//		// 设置样式;
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置底边框;
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		// 设置底边框颜色;
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		// 设置左边框;
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		// 设置左边框颜色;
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		// 设置右边框;
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		// 设置右边框颜色;
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		// 设置顶边框;
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		// 设置顶边框颜色;
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		// 在样式用应用设置的字体;
//		style.setFont(font);
//		// 设置自动换行;
//		style.setWrapText(false);
//		// 设置水平对齐的样式为居中对齐;
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置垂直对齐的样式为居中对齐;
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//		// 设置单元格背景颜色
//		style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//		return style;
//
//	}
//
//	/*
//	 * 列数据信息单元格样式
//	 */
//	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
//		// 设置字体
//		HSSFFont font = workbook.createFont();
//		// 设置字体大小
//		// font.setFontHeightInPoints((short)10);
//		// 字体加粗
//		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 设置字体名字
//		font.setFontName("Courier New");
//		// 设置样式;
//		HSSFCellStyle style = workbook.createCellStyle();
//		// 设置底边框;
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		// 设置底边框颜色;
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		// 设置左边框;
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		// 设置左边框颜色;
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		// 设置右边框;
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		// 设置右边框颜色;
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		// 设置顶边框;
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		// 设置顶边框颜色;
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		// 在样式用应用设置的字体;
//		style.setFont(font);
//		// 设置自动换行;
//		style.setWrapText(false);
//		// 设置水平对齐的样式为居中对齐;
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置垂直对齐的样式为居中对齐;
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//
//		return style;
//	}
//}
