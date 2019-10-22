import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class ReturnOrderService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
    super(http, "returnOrder");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  returnGoods(record: any){
    return this.postData("returnGoods",record)
  }

    }
