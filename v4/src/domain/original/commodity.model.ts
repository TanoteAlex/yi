import {Supplier} from "./supplier.model";
import {ProductComment} from "./product-comment.model";

export class Commodity {

    /**
     * 商品ID
     */
    id: number;
    commodityType
    /**
     * GUID
     */
    guid: string;

    /**
     * 商品编码
     */
    commodityNo: string;
    commodityShortName: string = ""
    /**
     * 商品名称
     */
    commodityName: string = "";

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
     * 运费设置（1统一运费2运费模板）
     */
    freightSet: number;

    /**
     * 统一运费
     */
    unifiedFreight: number;

    /**
     * 运费模板（freight_template表ID）
     */
    freightTemplate: number;

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
    description: any;

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

    imgPath: string = "";

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
    /**
     * 优惠信息
     */
    discountInfo: string;

    comments: ProductComment[] = [];

    /**
     * 商品规格
     */
    attributeGroups;

    /**
     * 附件，轮播图
     */
    attachmentVos;

    products;
    couponGrantConfig;
    returnVoucher

}
