import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class MemberService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
    super(http, "member");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

    memberLevel(){
        return this.getByParams("memberLevel", {});
    }

    prohibition(memberId){
      return this.getByParams("prohibition",{memberId:memberId})
    }

  updataVipYes(memberId){
    return this.getByParams("updataVipYes",{memberId:memberId})
  }
  updataVipNo(memberId){
    return this.getByParams("updataVipNo",{memberId:memberId})
  }


}
