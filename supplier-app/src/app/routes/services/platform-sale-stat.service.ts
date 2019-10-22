import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class PlatformSaleStatService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
    super(http, "platformSaleStat");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

    search(startTime,endTime){
      return this.getByParams("saleOrderItem", {"startTime":startTime,"endTime":endTime});

    }


    }
