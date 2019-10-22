import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import { BaseService } from './base.service';


@Injectable()
export class SupplierCheckAccountService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
    super(http,"supplierCheckAccount");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }



    }
