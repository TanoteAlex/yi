import {BaseService} from "@core/startup/base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable()
export class SupplierpageService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: HttpClient) {
    super(http, "supplier");
  }

  querySupplierData(){
    return this.getByParams("querySupplierData")
  }

}
