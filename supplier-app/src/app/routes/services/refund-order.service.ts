import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RefundOrderService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
    super(http, "refundOrder");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  toExamine(record: any){
    return this.postData("toExamine",record)
  }

    }
