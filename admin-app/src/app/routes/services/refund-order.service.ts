import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RefundOrderService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: HttpClient) {
    super(http, "refundOrder");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  toExamine(record: any){
    return this.postData("toExamine",record)
  }

    }
