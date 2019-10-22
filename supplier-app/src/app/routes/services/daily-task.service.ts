import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class DailyTaskService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
    super(http,"dailyTask");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }




    }
