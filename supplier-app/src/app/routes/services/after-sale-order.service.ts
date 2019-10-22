import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {Observable} from "rxjs/internal/Observable";


@Injectable()
export class AfterSaleOrderService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "afterSaleOrder");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  queryForSupplier(pageQuery: any): Observable<any> {
    return this.postData("queryForSupplier", pageQuery);
  }

  getByIdForSupplier(id: any): Observable<any> {
    return this.getByField("getByIdForSupplier", "id", id);
  }

}
