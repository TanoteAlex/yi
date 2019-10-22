import {ProvinceCity} from "./province-city.model";
import {Address} from "./address.model";
import {Account} from "./account.model";


export class Area {

    /**
     *
     */
    areaId: number;

    /**
     *
     */
    provinceCityId: number;

    /**
     *
     */
    accountId: number;

    /**
     *
     */
    addressId: number;

    /**
     * 小区名称
     */
    areaName: string;
    /**
     *管理员账号
     */
    account:Account
    /**
     * 地区
     */
    provinceCity:ProvinceCity;
    /**
     * 县区
     */
    address:Address;

}
