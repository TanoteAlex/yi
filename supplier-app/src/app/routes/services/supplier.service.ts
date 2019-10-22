import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/Rx";


@Injectable()
export class SupplierService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
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
    return this.getByParams("salesList", {id});
  }

  getForSupplier() {
    return this.getByParams("getForSupplier", {});
  }

  updateForSupplier(record: any): Observable<any> {
    return this.postData("updateForSupplier", record);
  }

  /**
   * 账户冻结
   * @param id
   * @param shel=0启用  shelf=1禁用
   * @returns {Observable<any>}
   */
  updateState(supplierId) {
    return this.getByParams("updateState", {supplierId: supplierId})
  }

}
