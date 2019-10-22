import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import { BaseService } from './base.service';
import {Observable} from "rxjs/internal/Observable";



@Injectable()
export class AfterSaleProcessService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "afterSaleProcess");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

  confirmReturnForSupplier(process: any) {
    return this.postData("confirmReturnForSupplier", process);
  }

  queryForSupplier(pageQuery: any): Observable<any> {
    return this.postData("queryForSupplier", pageQuery);
  }

}
