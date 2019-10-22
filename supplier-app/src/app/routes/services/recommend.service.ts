import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RecommendService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
    super(http, "recommend");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }


  /**
   *  禁用
   * @param id
   * @param 禁用
   * @returns {Observable<any>}
   */
  updateStateDisable(recommendId){
    return this.getByParams("updateStateDisable", {recommendId: recommendId })
  }

  /**
   *  启用
   * @param id
   * @param 启用
   * @returns {Observable<any>}
   */
  updateStateEnable(recommendId){
    return this.getByParams("updateStateEnable", {recommendId: recommendId })
  }



    }
