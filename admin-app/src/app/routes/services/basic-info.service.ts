import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class BasicInfoService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient ) {
    super(http, "basicInfo");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }



    }
