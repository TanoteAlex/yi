import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class AdvertisementService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: _HttpClient) {
    super(http, "advertisement");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  /**
   *  禁用
   * @param id
   * @param 禁用
   * @returns {Observable<any>}
   */
  updateStateDisable(advertisementId) {
    return this.getByParams("updateStateDisable", {advertisementId: advertisementId})
  }

  /**
   *  启用
   * @param id
   * @param 启用
   * @returns {Observable<any>}
   */
  updateStateEnable(advertisementId) {
    return this.getByParams("updateStateEnable", {advertisementId: advertisementId})
  }

}
