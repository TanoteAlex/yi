import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class CommentService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: _HttpClient) {
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


}
