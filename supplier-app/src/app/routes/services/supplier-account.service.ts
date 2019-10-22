import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class SupplierAccountService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "supplierAccount");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  getForSupplier() {
   return this.getByParams("getForSupplier");
  }


}
