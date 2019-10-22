
import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RescService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(http: HttpClient) {
    super(http, "resc");
  }

  setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
  }

  getAllResc() {
    return this.getByParams("getAll")
  }

}
