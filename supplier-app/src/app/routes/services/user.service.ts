import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import { BaseService } from './base.service';


@Injectable()
export class UserService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
    super(http, "user");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  /**
   * 禁用
   */
  disable(id){
   return this.getByParams("disable",{id:id})
  }

  /**
   * 启用
   */
  enable(id){
    return this.getByParams("enable",{id:id})
  }

}
