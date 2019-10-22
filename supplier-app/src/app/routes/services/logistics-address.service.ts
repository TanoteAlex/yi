import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/internal/Observable";


@Injectable()
export class LogisticsAddressService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "logisticsAddress");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
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

}
