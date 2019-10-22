import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class DeptService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
       super(http,"dept");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  getAllDept() {
    return this.getByParams("getAll")
  }




}
