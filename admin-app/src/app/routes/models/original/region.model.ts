import {Area} from "./area.model";

export class Region {

    /**
       * 地区ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 省
     */
    province:string;

    /**
       * 市
     */
    city:string;

    /**
       * 状态（0启用1禁用）
     */
    state:number;

    /**
       * 创建时间
     */
    createTime:string;

    area: Area;
}
