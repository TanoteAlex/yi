import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import { BaseService } from './base.service';


@Injectable()
export class MessageService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "message");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }


  enable(id){
    return this.getByParams("enable",{id:id})
  }


  disable(id){
    return this.getByParams("disable",{id:id})
  }






}
