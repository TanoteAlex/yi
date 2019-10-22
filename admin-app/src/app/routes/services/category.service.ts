import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class CategoryService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: HttpClient) {
    super(http, "category");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  getAllCategory() {
    return this.getByParams("getAll")
  }

  updateCategory(category:any){
    return this.postData("updateCategory",{category})
  }

}
