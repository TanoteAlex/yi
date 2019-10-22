import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/internal/Observable";


@Injectable()
export class OperateCategoryService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: _HttpClient) {
    super(http, "operateCategory");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  getAllCategoryForSupplier() {
    return this.getByParams("getAllForSupplier")
  }

  queryForSupplier(pageQuery: any): Observable<any> {
    return this.postData("queryForSupplier", pageQuery);
  }

}
