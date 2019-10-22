import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from 'rxjs/internal/BehaviorSubject';
import {Observable} from "rxjs/internal/Observable";


@Injectable()
export class CommodityService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
        super(http, "commodity");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    /**
     * 商品上下架
     * @param id
     * @param shelf 下架 shelf=2
     * @returns {Observable<any>}
     */
    upOrDownShelfForSupplier(commodityId) {
        return this.getByParams("upOrDownShelfForSupplier", { commodityId: commodityId })
    }

    /**
     * 商品重新申请上架
     */
    reapplyUpShelfForSupplier(commodityId) {
        return this.getByParams("reapplyUpShelfForSupplier", { commodityId: commodityId })
    }

    /**
     *   审核决绝
     */
    auditReject(id) {
        return this.getByParams("auditReject", { id: id })
    }

    /**
     * 审核通过
     */
    auditPass(id) {
        return this.getByParams("auditPass", { id: id })
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
     * 下架
     * @param {any[]} ids
     * @returns {Observable<any>}
     */
    batchDownShelf(ids: number[]) {
        return this.postData("batchDownShelf", ids)
    }

    queryForSupplier(pageQuery: any): Observable<any> {
        return this.postData("queryForSupplier", pageQuery);
    }

    addForSupplier(record: any): Observable<any> {
        return this.postData("addForSupplier", record);
    }

    updateForSupplier(record: any): Observable<any> {
        return this.postData("updateForSupplier", record);
    }

    getVoByIdForSupplier(id: any): Observable<any> {
        return this.getByField("getByIdForSupplier", "id", id);
    }

    removeByIdForSupplier(id: any): Observable<any> {
        return this.removeByField("removeByIdForSupplier", "id", id);
    }

}
