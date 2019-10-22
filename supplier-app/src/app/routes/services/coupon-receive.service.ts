import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class CouponReceiveService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
    super(http, "couponReceive");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }


  grantCoupon(record: any){
      return this.postData("grantCoupon",record);
  }




    }
