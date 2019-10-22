import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class SupplierService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
        super(http, "supplier");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    getList() {
        return this.getByParams("getList", {});
    }
    getPlatformdata() {
        return this.getByParams("getPlatformdata", {});
    }

    getSalesList(id) {
        return this.getByParams("salesList", { id });
    }

    /**
     * 账户冻结
     * @param id
     * @param shel=0启用  shelf=1禁用
     * @returns {Observable<any>}
     */
    updateState(supplierId) {
        return this.getByParams("updateState", { supplierId: supplierId })
    }

    querySupplierData() {
        return this.getByParams("querySupplierData")
    }

}
