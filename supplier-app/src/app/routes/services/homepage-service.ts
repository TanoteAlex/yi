import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import { _HttpClient } from '@delon/theme';
import { Inject, Injectable } from '@angular/core';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { HttpGlobalHeader } from '../configs/http-global-header';

@Injectable()
export class HomepageService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "homepage");
  }

  queryForSupplier(){
    return this.getByParams("queryForSupplier")
  }

}
