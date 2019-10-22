import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class SupplierSaleStatService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(http: HttpClient) {
        super(http, "supplierSaleStats");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    getSupplierSaleTotal(params: any) {
        return this.postData("getSupplierSaleTotal", params);

    }

    querySupplierSaleList(params: any) {
        return this.postData("querySupplierSaleList", params);
    }

}
