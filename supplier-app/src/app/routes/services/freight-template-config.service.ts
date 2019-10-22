import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import { BaseService } from './base.service';
import {Observable} from "rxjs/Rx";



@Injectable()
export class FreightTemplateConfigService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "freightTemplateConfig");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

  queryForSupplier(pageQuery: any): Observable<any> {
    return this.postData("queryForSupplier", pageQuery);
  }

  removeByIdForSupplier(id: any): Observable<any> {
    return this.removeByField("removeByIdForSupplier", "id", id);
  }

  getByIdForSupplier(id: any): Observable<any> {
    return this.getByField("getByIdForSupplier", "id", id);
  }

  updateForSupplier(record: any): Observable<any> {
    return this.postData("updateForSupplier", record);
  }

  addForSupplier(record: any): Observable<any> {
    return this.postData("addForSupplier", record);
  }

}
