package com.yi.core.common;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.service.IAreaService;
import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.utils.ExcelUtil;
import com.yihz.common.utils.date.DateUtils;

/**
 * 文件操作方法
 *
 * @author xuyh
 */
@Component
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    private static String EXPRESS_COMPANY = "{\"kuayuesuyun\": \"跨越速运\",\n" +
            "    \"annengwuliu\": \"安能物流\",\n" +
            "    \"baishisudi\": \"百世速递\",\n" +
            "    \"youzhengxiaobao\": \"邮政小包\",\"yuantong\": \"圆通速递\",\"shentong\": \"申通快递\","
            + "\"zhongtong\": \"中通速递\",\"huitongkuaidi\": \"汇通快运\",\"guotongkuaidi\": \"国通快递\",\"yunda\": \"韵达快运\","
            + "\"shunfeng\": \"顺丰快递\",\"tiantian\": \"天天快递\",\"debangwuliu\": \"德邦物流\",\"aae\": \"aae全球专递\","
            + "\"anjie\": \"安捷快递\",\"anxindakuaixi\": \"安信达快递\",\"biaojikuaidi\": \"彪记快递\"," + "\"baifudongfang\": \"百福东方国际物流\",\"coe\": \"中国东方\",\"changyuwuliu\": \"长宇物流\","
            + "\"datianwuliu\": \"大田物流\",\"dhl\": \"dhl\",\"dpex\": \"dpex\",\"dsukuaidi\": \"d速快递\","
            + "\"disifang\": \"递四方\",\"ems\": \"ems快递\",\"fedex\": \"fedex\",\"feikangda\": \"飞康达物流\","
            + "\"fenghuangkuaidi\": \"凤凰快递\",\"feikuaida\": \"飞快达\",\"ganzhongnengda\": \"港中能达物流\","
            + "\"guangdongyouzhengwuliu\": \"广东邮政物流\",\"gongsuda\": \"共速达\",\"hengluwuliu\": \"恒路物流\","
            + "\"huaxialongwuliu\": \"华夏龙物流\",\"haihongwangsong\": \"海红\",\"haiwaihuanqiu\": \"海外环球\","
            + "\"jiayiwuliu\": \"佳怡物流\",\"jinguangsudikuaijian\": \"京广速递\",\"jixianda\": \"急先达\",\"jjwl\": \"佳吉物流\","
            + "\"jymwl\": \"加运美物流\",\"jindawuliu\": \"金大物流\",\"jialidatong\": \"嘉里大通\",\"jykd\": \"晋越快递\","
            + "\"kuaijiesudi\": \"快捷速递\",\"lianb\": \"联邦快递\",\"lianhaowuliu\": \"联昊通物流\",\"longbanwuliu\": \"龙邦物流\","
            + "\"lijisong\": \"立即送\",\"lejiedi\": \"乐捷递\",\"minghangkuaidi\": \"民航快递\",\"meiguokuaidi\": \"美国快递\","
            + "\"menduimen\": \"门对门\",\"ocs\": \"OCS\",\"peisihuoyunkuaidi\": \"配思货运\",\"quanchenkuaidi\": \"全晨快递\","
            + "\"quanfengkuaidi\": \"全峰快递\",\"quanjitong\": \"全际通物流\",\"quanritongkuaidi\": \"全日通快递\","
            + "\"quanyikuaidi\": \"全一快递\",\"rufengda\": \"如风达\",\"santaisudi\": \"三态速递\","
            + "\"shenghuiwuliu\": \"盛辉物流\",\"sue\": \"速尔物流\",\"shengfeng\": \"盛丰物流\",\"saiaodi\": \"赛澳递\","
            + "\"tiandihuayu\": \"天地华宇\",\"tnt\": \"tnt\",\"ups\": \"ups\",\"wanjiawuliu\": \"万家物流\","
            + "\"wenjiesudi\": \"文捷航空速递\",\"wuyuan\": \"伍圆\",\"wxwl\": \"万象物流\",\"xinbangwuliu\": \"新邦物流\","
            + "\"xinfengwuliu\": \"信丰物流\",\"yafengsudi\": \"亚风速递\",\"yibangwuliu\": \"一邦速递\","
            + "\"youshuwuliu\": \"优速物流\",\"youzhengguonei\": \"邮政包裹\",\"youzhengguoji\": \"邮政国际包裹\","
            + "\"yuanchengwuliu\": \"远成物流\",\"yuanweifeng\": \"源伟丰快递\",\"yuanzhijiecheng\": \"元智捷诚快递\","
            + "\"yuntongkuaidi\": \"运通快递\",\"yuefengwuliu\": \"越丰物流\",\"yad\": \"源安达\",\"yinjiesudi\": \"银捷速递\","
            + "\"zhaijisong\": \"宅急送\",\"zhongtiekuaiyun\": \"中铁快运\",\"zhongyouwuliu\": \"中邮物流\"," + "\"zhongxinda\": \"忠信达\",\"zhimakaimen\": \"芝麻开门\",\"bht\": \"bht\",    }";

    private static Map<String, String> AREA_MAP = new ConcurrentHashMap<>();

    /**
     * 临时存储文件地址
     */
    @Value("${YI_HOME}/download/")
    private String rootPath;

    @Resource
    private IAreaService areaService;

    /**
     * EXCEL导出
     */
    public Workbook createOrderExcel(List<SaleOrder> saleOrders) {
        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            // 创建sheet页
            HSSFSheet sheet = workbook.createSheet("销售订单");
            // 创建样式
            HSSFCellStyle bodyStyle = workbook.createCellStyle();
            // 水平居中
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            // 竖直居中
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setWrapText(true);

            // 创建标题头
            HSSFRow titleRow = sheet.createRow(0);
            // 定义标题
            String[] titles = new String[]{"订单编号", "会员账号", "收货信息", "物流信息", "订单金额", "实付金额", "利润", "订单状态", "下单时间"};
            for (int i = 0; i < titles.length; i++) {
                titleRow.createCell(i).setCellValue(titles[i]);
            }
            if (CollectionUtils.isNotEmpty(saleOrders)) {
                // 赋值 数据
                for (int i = 0; i < saleOrders.size(); i++) {
                    SaleOrder tmpOrder = saleOrders.get(i);
                    if (tmpOrder != null) {
                        HSSFRow dataRow = sheet.createRow(i + 1);
                        dataRow.setRowStyle(bodyStyle);
                        dataRow.createCell(0).setCellValue(tmpOrder.getOrderNo());
                        dataRow.createCell(1).setCellValue(tmpOrder.getMember().getUsername());
                        // 收货信息
                        String receiptInfo = tmpOrder.getConsignee() + "，" + tmpOrder.getConsigneePhone() + "，" + this.getAddress(tmpOrder.getConsigneeAddr());
                        dataRow.createCell(2).setCellValue(receiptInfo);
                        // 快递信息
                        String expressInfo = "";
                        if (StringUtils.isNoneBlank(tmpOrder.getExpressNo())) {
                            expressInfo = tmpOrder.getExpressNo() + "，" + this.getExpressByCode(tmpOrder.getExpressCompany());
                        }
                        dataRow.createCell(3).setCellValue(expressInfo);
                        dataRow.createCell(4).setCellValue(tmpOrder.getOrderAmount().toString());
                        // 实付金额
                        String paymentInfo = "优惠券：" + tmpOrder.getCouponAmount() + "\r\n代金券：" + tmpOrder.getVoucherAmount() + "\r\n余额：" + tmpOrder.getBalance() + "\r\n运费："
                                + tmpOrder.getFreight() + "\r\n实付金额：" + tmpOrder.getPayAmount();
                        dataRow.createCell(5).setCellValue(new HSSFRichTextString(paymentInfo));
                        // 利润
                        String profitInfo = "供货价：" + tmpOrder.getCostAmount() + "\r\n销售价：" + tmpOrder.getOrderAmount() + "\r\n利润："
                                + tmpOrder.getOrderAmount().subtract(tmpOrder.getCostAmount());
                        dataRow.createCell(6).setCellValue(new HSSFRichTextString(profitInfo));
                        if (OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(7).setCellValue(OrderEnum.ORDER_STATE_WAIT_PAY.getValue());
                        } else if (OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(7).setCellValue(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getValue());
                        } else if (OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(7).setCellValue(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getValue());
                        } else if (OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(7).setCellValue(OrderEnum.ORDER_STATE_COMPLETED.getValue());
                        } else if (OrderEnum.ORDER_STATE_CLOSED.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(7).setCellValue(OrderEnum.ORDER_STATE_CLOSED.getValue());
                        }
                        dataRow.createCell(8).setCellValue(DateUtils.getFormatTimestamp(tmpOrder.getOrderTime()));
                    }
                }
            }
            return workbook;
        } catch (Exception e) {
            LOG.error("excel创建失败", e);
            return workbook;
        }
    }

    /**
     * EXCEL导出
     */
    public Workbook createOrderExcelForSupplier(List<SaleOrder> saleOrders) {
        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            // 创建sheet页
            HSSFSheet sheet = workbook.createSheet("销售订单");
            // 创建样式
            HSSFCellStyle bodyStyle = workbook.createCellStyle();
            // 水平居中
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            // 竖直居中
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setWrapText(true);

            // 创建标题头
            HSSFRow titleRow = sheet.createRow(0);
            // 定义标题
            String[] titles = new String[]{"订单编号", "会员账号", "订单金额", "实付金额", "订单状态", "下单时间"};
            for (int i = 0; i < titles.length; i++) {
                titleRow.createCell(i).setCellValue(titles[i]);
            }
            if (CollectionUtils.isNotEmpty(saleOrders)) {
                // 赋值 数据
                for (int i = 0; i < saleOrders.size(); i++) {
                    SaleOrder tmpOrder = saleOrders.get(i);
                    if (tmpOrder != null) {
                        HSSFRow dataRow = sheet.createRow(i + 1);
                        dataRow.setRowStyle(bodyStyle);
                        dataRow.createCell(0).setCellValue(tmpOrder.getOrderNo());
                        dataRow.createCell(1).setCellValue(tmpOrder.getMember().getUsername());
                        dataRow.createCell(2).setCellValue(tmpOrder.getOrderAmount().toString());
                        String paymentInfo = "优惠券：" + tmpOrder.getCouponAmount() + "\r\n代金券：" + tmpOrder.getVoucherAmount() + "\r\n余额：" + tmpOrder.getBalance() + "\r\n实付金额："
                                + tmpOrder.getPayAmount();
                        // HSSFCell cell3 = dataRow.createCell(3);
                        // cell3.setCellStyle(bodyStyle);
                        dataRow.createCell(3).setCellValue(new HSSFRichTextString(paymentInfo));
                        if (OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(4).setCellValue(OrderEnum.ORDER_STATE_WAIT_PAY.getValue());
                        } else if (OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(4).setCellValue(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getValue());
                        } else if (OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(4).setCellValue(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getValue());
                        } else if (OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(4).setCellValue(OrderEnum.ORDER_STATE_COMPLETED.getValue());
                        } else if (OrderEnum.ORDER_STATE_CLOSED.getCode().equals(tmpOrder.getOrderState())) {
                            dataRow.createCell(4).setCellValue(OrderEnum.ORDER_STATE_CLOSED.getValue());
                        }
                        dataRow.createCell(5).setCellValue(DateUtils.getFormatTimestamp(tmpOrder.getOrderTime()));
                    }
                }
            }
            return workbook;
        } catch (Exception e) {
            LOG.error("excel创建失败", e);
            return workbook;
        }
    }

    /**
     * EXCEL导出
     */
    public Workbook createCheckAccountExcel(List<SupplierCheckAccount> supplierCheckAccounts) {
        try {
            // 创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建sheet页
            HSSFSheet sheet = workbook.createSheet("供应商对账明细");
            // 创建样式
            HSSFCellStyle bodyStyle = workbook.createCellStyle();
            // 水平居中
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            // 竖直居中
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setWrapText(true);

            // 创建标题头
            HSSFRow titleRow = sheet.createRow(0);
            // 定义标题
            String[] titles = new String[]{"供应商名称", "订单编号", "商品编号", "商品名称", "销售价", "供货价", "数量", "下单时间", "应结货款"};
            for (int i = 0; i < titles.length; i++) {
                titleRow.createCell(i).setCellValue(titles[i]);
            }
            if (CollectionUtils.isNotEmpty(supplierCheckAccounts)) {
                // 赋值 数据
                for (int i = 0; i < supplierCheckAccounts.size(); i++) {
                    SupplierCheckAccount tmpCheckAccount = supplierCheckAccounts.get(i);
                    if (tmpCheckAccount != null) {
                        HSSFRow dataRow = sheet.createRow(i + 1);
                        dataRow.setRowStyle(bodyStyle);
                        dataRow.createCell(0).setCellValue(tmpCheckAccount.getSupplierName());
                        dataRow.createCell(1).setCellValue(tmpCheckAccount.getSaleOrderNo());
                        dataRow.createCell(2).setCellValue(tmpCheckAccount.getProductNo());
                        dataRow.createCell(3).setCellValue(tmpCheckAccount.getProductName());
                        dataRow.createCell(4).setCellValue(tmpCheckAccount.getSalePrice().toString());
                        dataRow.createCell(5).setCellValue(tmpCheckAccount.getSupplyPrice().toString());
                        dataRow.createCell(6).setCellValue(tmpCheckAccount.getQuantity());
                        dataRow.createCell(7).setCellValue(DateUtils.getFormatTimestamp(tmpCheckAccount.getOrderTime()));
                        dataRow.createCell(8).setCellValue(tmpCheckAccount.getSettlementAmount().toString());
                    }
                }
            }
            return workbook;
        } catch (Exception e) {
            LOG.error("excel创建失败", e);
            return null;
        }
    }

    /**
     * EXCEL导出
     *
     * @param workbook
     * @param outputFileName
     * @param request
     * @param response
     */
    public void exportExcel(Workbook workbook, String outputFileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (workbook instanceof HSSFWorkbook) {
                outputFileName += ExcelUtil.EXCEL_V_XLS;
            } else if (workbook instanceof XSSFWorkbook) {
                outputFileName += ExcelUtil.EXCEL_V_XLSX;
            }
            // 文件输出
            String filename = URLEncoder.encode(outputFileName, "UTF-8");
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + filename + "");
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            LOG.error("excel导出失败", e);
        }
    }

    /**
     * EXCEL导出
     *
     * @param workbook
     * @param request
     * @param response
     */
    public void exportExcel(Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 文件输出
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            LOG.error("excel导出失败", e);
        }
    }

    public String getExpressByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            JSONObject express = JSONObject.parseObject(EXPRESS_COMPANY);
            return Optional.ofNullable(express.getString(code)).orElse("");
        }
        return "";
    }

    public String getAddress(String source) {
        if (StringUtils.isNotBlank(source)) {
            String province = this.getAreaNameByAreaCode(source.substring(0, 6));
            String city = this.getAreaNameByAreaCode(source.substring(6, 12));
            String district = this.getAreaNameByAreaCode(source.substring(12, 18));
            return province + city + district + source.substring(18, source.length());
        }
        return "";
    }

    public String getAreaNameByAreaCode(String areaCode) {
        if (StringUtils.isNotBlank(areaCode)) {
            if (AREA_MAP.containsKey(areaCode)) {
                return AREA_MAP.get(areaCode);
            } else {
                Area dbArea = areaService.getByAreaCode(areaCode);
                if (dbArea != null) {
                    AREA_MAP.put(dbArea.getAreaCode(), dbArea.getName());
                    return dbArea.getName();
                }
            }
        }
        return "";
    }

}
