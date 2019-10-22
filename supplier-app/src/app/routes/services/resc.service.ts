
import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RescService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(http: _HttpClient) {
    super(http, "resc");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

  getAllResc() {
    return this.getByParams("getAll")
  }

}
