import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import { BaseService } from './base.service';



@Injectable()
export class SupplierAccountRecordService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "supplierAccountRecord");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

}
