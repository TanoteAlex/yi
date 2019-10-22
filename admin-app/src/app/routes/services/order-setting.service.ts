import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class OrderSettingService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: HttpClient) {
    super(http, "orderSetting");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  updateTimeoutValue(id, timeout) {
    return this.getByParams("updateTimeoutValue", {id: id, minute: timeout})
  }

}
