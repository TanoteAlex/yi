import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class RebateGroupService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
    super(http, "rebateGroup");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }



    }
