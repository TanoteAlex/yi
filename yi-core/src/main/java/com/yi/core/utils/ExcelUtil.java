package com.yi.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yihz.common.utils.date.CalendarUtils;

/**
 * excel 工具类
 *
 * @author xuyh
 *
 */
public class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * excel 97-2003版本
	 */
	public static final String EXCEL_V_XLS = ".xls";
	/**
	 * excel 2007+以上版本
	 */
	public static final String EXCEL_V_XLSX = ".xlsx";
	/**
	 * 默认 sheet页
	 */
	private static final Integer SHEET_START = 0;
	/**
	 * 默认 标题行
	 */
	private static final Integer TITLE_START = 0;
	/**
	 * 默认 数据行
	 */
	private static final Integer DATA_START = 1;
	/**
	 * 数字格式
	 */
	private static DecimalFormat decimalFormat = new DecimalFormat("0");

	/**
	 * 读取EXCEL文件 从指定sheet页 指定row开始读取
	 *
	 * @param File
	 *            pageFile
	 * @param sheetIndex
	 *            指定sheet页
	 * @param dataIndex
	 *            指定行
	 * @return List<Row>
	 */
	public static List<Row> readExcel(File pageFile, int sheetIndex, int dataIndex) {
		if (null == pageFile) {
			logger.error("读取的文件为空");
			return null;
		}
		if (!pageFile.exists() || !pageFile.isFile()) {
			logger.error("读取的文件不存在或不是标准文件,文件路径为:" + pageFile.getPath());
			return null;
		}
		List<Row> result = new ArrayList<>();
		try (InputStream pageStream = new FileInputStream(pageFile)) {
			Workbook pageWorkbook = WorkbookFactory.create(pageStream);
			for (Row row : pageWorkbook.getSheetAt(sheetIndex)) {
				if (row != null && !isRowEmpty(row) && row.getRowNum() >= dataIndex) {
					// 去除标注
					// for (Cell cell : row) {
					// cell.removeCellComment();
					// }
					result.add(row);
				}
			}
		} catch (Exception e) {
			logger.error("-readExcel--读取上传文件时,出现异常,上传文件路径为:" + pageFile.getPath(), e);
		}
		return result;
	}

	/**
	 * 读取EXCEL文件 从指定sheet页 默认第二行开始读取 读取多个工作簿
	 *
	 * @param File
	 *            pageFile
	 * @param sheetIndex
	 *            指定sheet页
	 * @param dataIndex
	 *            指定行
	 * @return List<Row>
	 */
	public static List<Row> readExcelBySheets(File pageFile, int dataIndex) {
		if (null == pageFile) {
			logger.error("读取的文件为空");
			return null;
		}
		if (!pageFile.exists() || !pageFile.isFile()) {
			logger.error("读取的文件不存在或不是标准文件,文件路径为:" + pageFile.getPath());
			return null;
		}
		List<Row> result = new ArrayList<>();
		try (InputStream pageStream = new FileInputStream(pageFile)) {
			Workbook pageWorkbook = WorkbookFactory.create(pageStream);
			for (int i = 0; i < pageWorkbook.getNumberOfSheets(); i++) {
				for (Row row : pageWorkbook.getSheetAt(i)) {
					// 去除空行，隐藏行
					if (row != null && !isRowEmpty(row) && row.getRowNum() >= dataIndex) {
						// 去除标注
						for (Cell cell : row) {
							cell.removeCellComment();
						}
						result.add(row);
					}
				}
			}
		} catch (Exception e) {
			logger.error("-readExcel--读取上传文件时,出现异常,上传文件路径为:" + pageFile.getPath(), e);
		}
		return result;
	}

	/**
	 * 读取EXCEL文件 默认从第一个sheet页 从指定row开始
	 *
	 * @param File
	 *            pageFile
	 * @param int
	 *            dataIndex 指定从第几行开始读取数据
	 * @return List<Row>
	 */
	public static List<Row> readExcel(File pageFile, int dataIndex) {
		return readExcel(pageFile, SHEET_START, dataIndex);
	}

	/**
	 * 读取EXCEL文件 默认从第一个sheet页 第二行开始读取
	 *
	 * @param File
	 *            pageFile
	 * @return List<Row>
	 */
	public static List<Row> readExcel(File pageFile) {
		return readExcel(pageFile, SHEET_START, DATA_START);
	}

	/**
	 * 读取EXCEL文件 默认从第一个sheet页 第二行开始读取 读取多个工作簿
	 *
	 * @param File
	 *            pageFile
	 * @return List<Row>
	 */
	public static List<Row> readExcelBySheets(File pageFile) {
		return readExcelBySheets(pageFile, DATA_START);
	}

	/**
	 * 校验前台上传文件 是否符合模板
	 *
	 * @param pageFile
	 *            页面传入需要校验的文件
	 * @param tplPath
	 *            模板地址
	 * @param sheetIndex
	 *            指定 sheet页
	 * @param titleIndex
	 *            指定 标题行
	 * @return
	 */
	public static boolean checkExcel(File pageFile, String tplPath, int sheetIndex, int titleIndex) {
		try (InputStream pageStream = new FileInputStream(pageFile); InputStream tplStream = new FileInputStream(tplPath)) {
			// 模板文件
			Workbook tplWorkbook = WorkbookFactory.create(tplStream);
			Sheet tplSheet = tplWorkbook.getSheetAt(sheetIndex);
			// 要校验的文件
			Workbook pageWorkbook = WorkbookFactory.create(pageStream);
			Sheet pageSheet = pageWorkbook.getSheetAt(sheetIndex);
			// 遍历行
			Row tplRow = tplSheet.getRow(titleIndex);
			Row pageRow = pageSheet.getRow(titleIndex);
			// 校验页面传入文件数据是否为空
			if (null == pageRow || isRowEmpty(pageRow)) {
				return false;
			}
			for (int c = tplRow.getFirstCellNum(); c < tplRow.getLastCellNum(); c++) {
				// Cell tplCell = tplRow.getCell(c);
				// Cell pageCell = pageRow.getCell(c);
				// if (!replaceUnit(getCellValue(tplCell)).equals(getCellValue(pageCell))) {
				// return false;
				// }
			}
			return true;
		} catch (Exception e) {
			logger.error("-checkExcel--校验模板和导入文件是否相同时,出现异常,验证模板地址为:" + tplPath, e);
			return false;
		}
	}

	// private static String replaceUnit(String cell) {
	// if (cell.indexOf("${unit}") > -1) {
	// return cell.replace("${unit}", SystemCustomize.getSusCurMemberUnit());
	// }
	// return cell;
	// }

	/**
	 * 校验前台上传文件 是否符合模板 (默认第一个sheet页 指定行)
	 *
	 * @param pageFile
	 *            页面传入需要校验的文件
	 * @param tplPath
	 *            模板地址
	 * @param titleIndex
	 *            指定 标题行
	 * @return
	 */
	public static boolean checkExcel(File pageFile, String tplPath, int titleIndex) {
		return checkExcel(pageFile, tplPath, SHEET_START, titleIndex);
	}

	/**
	 * 校验前台上传文件 是否符合模板 (默认第一个sheet页 第一行)
	 *
	 * @param pageFile
	 *            页面传入需要校验的文件
	 * @param tplPath
	 *            模板地址
	 * @return
	 */
	public static boolean checkExcel(File pageFile, String tplPath) {
		return checkExcel(pageFile, tplPath, SHEET_START, TITLE_START);
	}

	/**
	 * 生成错误数据 EXCEL 以供导出修改
	 *
	 * @param errors
	 *            错误数据
	 * @param tplPath
	 *            模板地址
	 * @param errorPath
	 *            错误数据保存地址
	 * @param titleIndex
	 *            标题所在的行标
	 * @return
	 */
	public static void createErrorExcel(List<Row> errors, String tplPath, String errorPath, int titleIndex) {
		File file = new File(errorPath);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		try (InputStream inputStream = new FileInputStream(tplPath); FileOutputStream fos = new FileOutputStream(file)) {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(SHEET_START);
			Row titleRow = sheet.getRow(titleIndex);
			for (int i = 0; i < errors.size(); i++) {
				Row row = sheet.createRow(titleIndex + 1 + i);
				// 针对动态列使用的空模板
				titleRow = titleRow == null ? errors.get(i) : titleRow;
				for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); j++) {
					Cell cell = row.createCell(j);
					Cell errorCell = errors.get(i).getCell(j);
					if (errorCell != null) {
						cell.setCellValue(getCellValue(errorCell));
						if (errorCell.getCellComment() != null && errorCell.getCellComment().getString() != null
								&& StringUtils.isNotBlank(errorCell.getCellComment().getString().toString())) {
							addComment(cell, errorCell.getCellComment().getString().toString(), true);
						}
					}
				}
			}
			// 文件输出
			workbook.write(fos);
		} catch (Exception e) {
			logger.error("-createErrorExcel--生成错误文件时,出现异常,错误文件路径为:" + errorPath, e);
			e.printStackTrace();
		}
	}

	/**
	 * 生成错误数据 EXCEL 以供导出修改 (为bom导出错误表头定制)
	 *
	 * @param errors
	 *            错误数据
	 * @param tplPath
	 *            模板地址
	 * @param errorPath
	 *            错误数据保存地址
	 * @param titleIndex
	 *            标题所在的行标
	 * @return
	 */
	public static void createErrorExcel(List<Row> errors, String tplPath, String errorPath, int titleIndex, List<String> addHeadCelllists) {
		File file = new File(errorPath);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		try (InputStream inputStream = new FileInputStream(tplPath); FileOutputStream fos = new FileOutputStream(file)) {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(SHEET_START);
			Row titleRow = sheet.getRow(titleIndex);
			for (int i = 0; i < errors.size(); i++) {
				int a = titleRow.getLastCellNum() - errors.get(i).getLastCellNum();
				if (a < 0) {
					// 追加原料头部到bom表头上
					int addHeads = Math.abs(a) / addHeadCelllists.size();
					// 清除最后一列线缆重量
					Cell cell = titleRow.getCell(titleRow.getPhysicalNumberOfCells() - 1);
					titleRow.removeCell(cell);
					for (int s = 0; s < addHeads; s++) {
						for (int d = 0; d < addHeadCelllists.size(); d++) {
							Cell createCell = titleRow.createCell(titleRow.getPhysicalNumberOfCells());
							createCell.setCellValue(addHeadCelllists.get(d));
							if (d == 0) { // 原料编码列增加特殊样式
								createCell.setCellStyle(titleRow.getCell(3).getCellStyle()); // 第4列为原料编码
							} else {
								createCell.setCellStyle(cell.getCellStyle());
							}
						}
					}
					Cell createCell = titleRow.createCell(titleRow.getPhysicalNumberOfCells());
					createCell.setCellValue(cell.getStringCellValue());
					createCell.setCellStyle(cell.getCellStyle());
				}
				Row row = sheet.createRow(titleIndex + 1 + i);
				// 针对动态列使用的空模板
				titleRow = titleRow == null ? errors.get(i) : titleRow;
				for (int j = titleRow.getFirstCellNum(); j < titleRow.getLastCellNum(); j++) {
					Cell cell = row.createCell(j);
					Cell errorCell = errors.get(i).getCell(j);
					if (errorCell != null) {
						String cellValue = getCellValue(errorCell);
						if (ValidateUtil.isDecimal(cellValue) && cellValue.substring(cellValue.indexOf(".") + 1, cellValue.length()).length() > 3) {
							cellValue = String.format("%.2f", Double.parseDouble(cellValue));
						}
						cell.setCellValue(cellValue);
						if (errorCell.getCellComment() != null && errorCell.getCellComment().getString() != null
								&& StringUtils.isNotBlank(errorCell.getCellComment().getString().toString())) {
							addComment(cell, errorCell.getCellComment().getString().toString(), true);
						}
					}
				}
			}
			// 文件输出
			workbook.write(fos);
		} catch (Exception e) {
			logger.error("-createErrorExcel--生成错误文件时,出现异常,错误文件路径为:" + errorPath, e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据路径名称 判断是否是excel
	 *
	 * @param pathname
	 * @return boolean
	 */
	public static boolean isExcel(String pathname) {
		if (StringUtils.isBlank(pathname)) {
			return false;
		}
		return pathname.endsWith(EXCEL_V_XLS) || pathname.endsWith(EXCEL_V_XLSX);
	}

	/**
	 * 根据路径名称 判断是否是excel
	 *
	 * @param pathname
	 * @return boolean
	 */
	public static boolean isExcel_xls(String pathname) {
		if (StringUtils.isBlank(pathname)) {
			return false;
		}
		return pathname.endsWith(EXCEL_V_XLS);
	}

	/**
	 * 根据路径名称 判断是否是excel
	 *
	 * @param pathname
	 * @return boolean
	 */
	public static boolean isExcel_xlsx(String pathname) {
		if (StringUtils.isBlank(pathname)) {
			return false;
		}
		return pathname.endsWith(EXCEL_V_XLSX);
	}

	/**
	 * 判断空行和隐藏行
	 *
	 * @param row
	 * @return boolean
	 */
	public static boolean isRowEmpty(Row row) {
		if (row.getZeroHeight()) {
			return true;
		}
		for (Cell cell : row) {
			if (cell != null && CellType.BLANK.equals(cell.getCellTypeEnum()))
				return false;
		}
		return true;
	}

	/**
	 * 获取单元格的值 (空或错误 返回"")
	 *
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String value = "";
		if (null == cell) {
			return value;
		}
		try {
			switch (cell.getCellTypeEnum()) {
			case NUMERIC:
				/*
				 * 获取日期格式的值 yyyy-MM-dd-----14 yyyy年m月d日---31 yyyy年m月-------57 m月d日----------58
				 * HH:mm-----------20 h时mm分 -------32
				 */
				short format = cell.getCellStyle().getDataFormat();
				// excel 日期判断
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					value = CalendarUtils.formatDate(cell.getDateCellValue());
					// 日期
				} else if (format == 14 || format == 31 || format == 57 || format == 58) {
					value = CalendarUtils.formatDate(cell.getDateCellValue());
					// 时间
				} else if (format == 20 || format == 32) {
					value = CalendarUtils.formatTime(cell.getDateCellValue());
					// 数字
				} else {
					// 先获取当前cell数据
					String tmp1 = String.valueOf(cell.getNumericCellValue());
					// 设置单元格 格式
					cell.setCellType(CellType.STRING);
					// 再获取格式化后的数据
					String tmp2 = cell.getStringCellValue();
					if (tmp2.length() > tmp1.length() && ValidateUtil.isNumber(tmp2)) {
						value = tmp1;
						// 处理后的数据设置给当前cell
						cell.setCellValue(tmp1);
					} else {
						value = tmp2;
					}
				}
				break;
			case STRING:
				value = cell.getStringCellValue();
				break;
			case FORMULA:
				value = cell.getCellFormula();
				break;
			case BLANK:
				break;
			case BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case ERROR:
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("-getCellValue--获取单元格数据时出现异常", e);
		}
		return value.trim();
	}

	/**
	 * 设置单元格标注
	 *
	 * @param Cell
	 *            cell
	 * @param comments
	 *            标注
	 * @param errorStyle
	 *            是否添加 错误样式
	 */
	public static void addComment(Cell cell, String comments, boolean errorStyle) {
		if (cell instanceof HSSFCell) {
			addComment((HSSFCell) cell, comments, errorStyle);
		} else if (cell instanceof XSSFCell) {
			addComment((XSSFCell) cell, comments, errorStyle);
		}
	}

	/**
	 * 设置单元格标注
	 *
	 * @param HSSFCell
	 *            cell
	 * @param comments
	 *            标注
	 * @param errorStyle
	 *            是否添加 错误样式
	 */
	public static void addComment(HSSFCell cell, String comments, boolean errorStyle) {
		HSSFComment comment = cell.getSheet().createDrawingPatriarch().createCellComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 2, (short) 3, 4));
		comment.setString(new HSSFRichTextString(comments));
		cell.setCellComment(comment);
		if (errorStyle) {
			addCellStyle(cell);
		}
	}

	/**
	 * 设置单元格标注
	 *
	 * @param XSSFCell
	 *            cell
	 * @param comments
	 *            标注
	 * @param errorStyle
	 *            是否添加 错误样式
	 */
	public static void addComment(XSSFCell cell, String comments, boolean errorStyle) {
		XSSFComment comment = cell.getSheet().createDrawingPatriarch().createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 1, 2, 2, 4));
		comment.setString(new XSSFRichTextString(comments));
		cell.setCellComment(comment);
		if (errorStyle) {
			addCellStyle(cell);
		}
	}

	/**
	 * 设置单元格样式
	 *
	 * @param XSSFCell
	 *            cell
	 * @param comments
	 */
	public static void addCellStyle(Cell cell) {
		CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);
	}

}
