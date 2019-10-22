import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";


@Injectable()
export class SalesAreaCommodityService extends BaseService {

  constructor(protected http: HttpClient) {
    super(http, "salesAreaCommodity");
  }

}
