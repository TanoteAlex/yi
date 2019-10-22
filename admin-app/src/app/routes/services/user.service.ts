import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {BaseService} from "@core/startup/base.service";


@Injectable()
export class UserService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
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
