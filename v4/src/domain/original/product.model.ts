import {Commodity} from "./commodity.model";
import {Category} from "./category.model";
import {Supplier} from "./supplier.model";

export class Product {

    /**
     * 货品ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 货品编码
     */
    productNo: string;

    /**
     * 货品名称
     */
    productName: string;

    /**
     * 货品简称
     */
    productShortName: string;

    /**
     * 商品图片
     */
    imgPath: string;

    /**
     * 商品（commodity表ID）
     */
    commodityId: number;
    commodity: Commodity;

    /**
     * 分类（category表ID）
     */
    categoryId: Category;

    /**
     * 供应商（supplier表ID）
     */
    supplierId: Supplier;

    /**
     * 排序
     */
    sort: number;

    /**
     * 是否参与分销(0参加1不参加)
     */
    distribution: boolean;

    /**
     * 佣金比例
     */
    commissionRate: number;

    /**
     * 运费类型（1统一运费2运费模板）
     */
    freightType: boolean;

    /**
     * 统一运费
     */
    unifiedFreight: number;

    /**
     * 运费模板（freight_template表ID）
     */
    freightTemplateId: number;

    /**
     * 库存设置（1下单减库存2支付减库存）
     */
    stockSet: boolean;

    /**
     * 体积
     */
    volume: number;

    /**
     * 重量
     */
    weight: number;

    /**
     * 是否上架（1立即上架2放入仓库）
     */
    shelf: boolean;

    /**
     * 商品介绍
     */
    description: string;

    /**
     * 属性名
     */
    attrName: string;

    /**
     * 属性值
     */
    attrValue: string;

    /**
     * 原价
     */
    originalPrice: number;

    /**
     * 现价
     */
    currentPrice: number;

    /**
     * 会员价
     */
    memberPrice: number;
    vipPrice

    /**
     * SKU
     */
    sku: string;

    /**
     * 库存
     */
    stock: number;

    /**
     * 创建时间
     */
    createTime: string;

    /**
     * 删除（0否1是）
     */
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;

    /**
     * 选择的购买数量
     */
    quantity: number;

    /**
     * 已选属性组
     */
    attrs = [];


    productId;

    orderId;

    productImgPath;

    attributes = [];
    levelPrices
}
