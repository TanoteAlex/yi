import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import { BaseService } from './base.service';
import {Observable} from "rxjs/internal/Observable";


@Injectable()
export class SaleOrderService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
        super(http, "saleOrder");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    updateOrderStateForSupplier(id, state) {
        return this.getByParams("updateStateForSupplier", { id: id, state: state })
    }

    updateProvinceForSupplier(params: any) {
        return this.postData("updateProvinceForSupplier", params)
    }

    updatePriceForSupplier(params: any) {
        return this.postData("updatePriceForSupplier", params)
    }

    goDelivery(id, trackingNo, logisticsCompany) {
        return this.getByParams("delivery", { id: id, trackingNo: trackingNo, logisticsCompany: logisticsCompany })
    }

    goHomeData() {
        return this.getByParams("homeData")
    }

    queryForSupplier(pageQuery: any): Observable<any> {
        return this.postData("queryForSupplier", pageQuery);
    }

    getByIdForSupplier(id: any): Observable<any> {
        return this.getByField("getByIdForSupplier", "id", id);
    }

    getOrderNumForSupplier() {
        return this.getByParams("getOrderNumForSupplier");
    }

}
