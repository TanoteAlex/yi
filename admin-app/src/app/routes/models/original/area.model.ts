
export class Area {

    /**
       * 地区Id
     */
    id:number;

    /**
       * 父节点ID
     */
    parentId:number;

    /**
       * 地区级别（1:省份province,2:市city,3:区县district,4:街道street）
     */
    level:number;

    /**
       * 地区编码
     */
    areaCode:string;

    /**
       * 地区名
     */
    name:string;

    /**
       * 简称
     */
    shortName:string;

    /**
       * 拼音
     */
    pinYin:string;

    /**
       * 简拼
     */
    jianPin:string;

    /**
       * 城市编码
     */
    cityCode:string;

    /**
       * 邮政编码
     */
    zipCode:string;

    /**
       * 城市中心点（即：经纬度坐标）
     */
    center:string;

    /**
       * 经度
     */
    longitude:number;

    /**
       * 纬度
     */
    latitude:number;
}
