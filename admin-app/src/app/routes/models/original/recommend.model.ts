import {Commodity} from "./commodity.model";
import {Position} from "./position.model";
import {Article} from "./article.model";
import {RecommendCommodity} from "./recommend-commodity.model";

export class Recommend {

    /**
     * 推荐位ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 位置表
     */
    position: Position;
    /**
     * 位置名称
     */
    positionName: string;

    /**
     * 推荐位标题
     */
    title: string;

    /**
     * 排序号
     */
    sort: number;

    /**
     * 推荐位默认图片路径
     */
    imgPath: string;

    /**
     * 排序方式
     */
    showMode: number;

    /**
     * 状态（0启用1禁用）
     */
    state: number;

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
     * 推荐类型（1今日推荐2楼层推荐）
     */
    recommendType: number;

    /**
     * 推荐商品
     */
    recommendCommodities: RecommendCommodity[];

    /**
    * 推荐商品
    */
    commodities: Commodity[];

    /**
     * 是否显示banner图 0显示1不显示
     */
    showBanner: number;
    /**
     * 是否显示标题 0显示1不显示
     */
    showTitle: number;

    linkType: number;

    linkId: number;

    commodity: Commodity;

}
