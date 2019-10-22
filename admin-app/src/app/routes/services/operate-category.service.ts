import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class OperateCategoryService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: HttpClient) {
    super(http, "operateCategory");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  getAllCategory() {
    return this.getByParams("getAll")
  }

}
