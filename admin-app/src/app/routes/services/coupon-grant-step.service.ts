import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {BaseService} from "./base.service";



@Injectable()
export class CouponGrantStepService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: HttpClient) {
    super(http, "couponGrantStep");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

}
