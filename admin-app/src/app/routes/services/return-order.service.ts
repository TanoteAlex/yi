import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class ReturnOrderService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: HttpClient) {
    super(http, "returnOrder");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  returnGoods(record: any){
    return this.postData("returnGoods",record)
  }

    }
