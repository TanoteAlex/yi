import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {BaseService} from '@core/startup/base.service';



@Injectable()
export class AfterSaleProcessService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: HttpClient) {
    super(http, "afterSaleProcess");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

  confirmReturn(process: any) {
    return this.postData("confirmReturn", process);
  }

}
