import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RoleService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
    super(http,"role");
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
