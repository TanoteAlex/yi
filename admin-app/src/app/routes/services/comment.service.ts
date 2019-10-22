import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class CommentService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: HttpClient) {
    super(http, "comment");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }


  reply(id, replyContent) {
    return this.getByParams("reply", {id: id, replyContent: replyContent})
  }

  updateDispaly(id, display) {
    return this.getByParams("updateDispaly", {id: id, display: display})
  }

  display(id, display) {
    return this.getByParams("display", {id: id, display: display})
  }


  hide(id, display) {
    return this.getByParams("hide",{id: id, display: display})
  }

}
