import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RoleService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
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
