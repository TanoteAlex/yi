import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class MemberService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
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

  resetPassword(memberId){
      return this.postFormData("resetPassword",{id:memberId})
    }

  updataVipYes(memberId){
    return this.getByParams("updataVipYes",{memberId:memberId})
  }
  updataVipNo(memberId){
    return this.getByParams("updataVipNo",{memberId:memberId})
  }


}
