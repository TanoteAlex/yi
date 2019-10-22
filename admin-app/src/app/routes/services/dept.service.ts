import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class DeptService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: HttpClient) {
       super(http,"dept");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  getAllDept() {
    return this.getByParams("getAll")
  }




}
