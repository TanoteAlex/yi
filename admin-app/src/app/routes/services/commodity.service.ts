import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from 'rxjs/internal/BehaviorSubject';


@Injectable()
export class CommodityService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(http: HttpClient) {
        super(http, "commodity");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    syncCommoditiesToEs(){
      return this.getByParams("syncCommoditiesToEs")
    }

    /**
     * 商品上下架
     * @param id
     * @param shelf 下架 shelf=2
     * @returns {Observable<any>}
     */
    upOrDownShelf(commodityId) {
        return this.getByParams("upOrDownShelf", { commodityId: commodityId })
    }

    /**
     * 审核通过
     */
    auditPass(id) {
        return this.getByParams("auditPass", { id: id })
    }

    /**
     * 审核拒绝
     */
    auditReject(id) {
        return this.getByParams("auditReject", { id: id })
    }


    getAttributeGroupsByCategoryId(categoryId: number) {
        return this.getByParams("getAttributeGroupsByCategoryId", { categoryId: categoryId })
    }

    /**
     * 批量上架
     * @param {any[]} ids
     * @returns {Observable<any>}
     */
    batchUpShelf(ids: number[]) {
        return this.postData("batchUpShelf", ids)
    }

    /**
     * 批量下架
     * @param {any[]} ids
     * @returns {Observable<any>}
     */
    batchDownShelf(ids: number[]) {
        return this.postData("batchDownShelf", ids)
    }
}
