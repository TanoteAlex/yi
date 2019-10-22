import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import { _HttpClient } from '@delon/theme';
import {Injectable} from "@angular/core";

@Injectable()
export class SupplierpageService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "supplierpage");
  }

  querySupplierData(){
    return this.getByParams("query")
  }

}
