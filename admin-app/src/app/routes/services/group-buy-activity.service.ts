import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {BaseService} from "./base.service";


@Injectable()
export class GroupBuyActivityService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: HttpClient) {
    super(http, "groupBuyActivity");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  auditing(groupBuyActivityId, userId) {
    return this.getByParams("auditing", {id: groupBuyActivityId, userId: userId})
  }

  publish(groupBuyActivityId){
    return this.getByParams("publish", {id: groupBuyActivityId})
  }

}
