import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class CommunityGroupRecordService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
    super(http, "communityGroupRecord");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }



    }
