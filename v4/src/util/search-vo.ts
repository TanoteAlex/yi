export class SearchVo {

    keyword:string;

    categoryId:number;

    lowestPrice:number;

    highestPrice:number;

    priceSection:PriceSection;

    cityCode:string;

    sortBy:string;

    direction:string;

    pageSize:number;

    page:number;
}

export class PriceSection {
    /**
     * 起始价格
     */
    startPrice:number;

    /**
     * 结束价格
     */
    endPrice:number;
}
