import {Supplier} from "./supplier.model";
import {FreightTemplate} from "./freight-template.model";
import {AttributeGroup} from "./attribute-group.model";
import {Attachment} from "./attachment.model";
import {Region} from "./region.model";
import {Category} from "./category.model";
import {CommodityLevelDiscount} from "./commodity-level-discount.model";
import {OperateCategory} from "./operate-category.model";
import {Product} from "./product.model";

export class Commodity {

    /**
     * 商品ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 商品编码
     */
    commodityNo: string;

    /**
     * 商品名称
     */
    commodityName: string;

    /**
     * 商品简称
     */
    commodityShortName: string;

    /**
     * 供应商（supplier表ID）
     */
    supplier: Supplier;
    /**
     * 商品销售地址（region表ID）
     */
    regions: Region[];
    /**
     * 排序
     */
    sort: number;

    /**
     * 是否参与分销(0参加1不参加)
     */
    distribution: number;

    /**
     * 佣金比例
     */
    commissionRate: number;

    integralRate: number;

    /**
     * 运费设置（1统一运费2运费模板）
     */
    freightSet: number;

    /**
     * 统一运费
     */
    unifiedFreight: number;

    /**
     * vip价
     */
    vipPrice: number;

    /**
     * 运费模板（freight_template表ID）
     */
    freightTemplate: FreightTemplate;

    /**
     * 库存设置（1下单减库存2支付减库存）
     */
    stockSet: number;

    /**
     * 体积
     */
    volume: number;

    /**
     * 重量
     */
    weight: number;
    /**
     * 审核状态（1待审核2审核通过3审核拒绝）
     */
    auditState: number;
    /**
     * 上架状态（1立即上架2放入仓库）
     */
    shelfState: number;

    /**
     * 商品介绍
     */
    description: string;

    /**
     * 创建时间
     */
    createTime: string;

    /**
     * 删除（0否1是）
     */
    deleted: number;

    /**
     * 删除时间
     */
    delTime: string;

    /**
     * 图片路径
     */
    imgPath: string;

    attributeGroups: AttributeGroup[];

    product: Product;

    category: Category;

    operateCategories: OperateCategory[];

    supplierName: string;

    attachmentVos: Attachment[];

    products: Product[];

    commodityLevelDiscounts: CommodityLevelDiscount[];

    commodityType: number;

}
