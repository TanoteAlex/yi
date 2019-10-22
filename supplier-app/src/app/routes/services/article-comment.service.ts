import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class ArticleCommentService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(protected http: _HttpClient) {
    super(http, "articleComment");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  updateHidden(id, state) {
    return this.getByParams("updateState", {id: id, state: state})
  }

  reply(id, replys) {
    return this.getByParams("reply", {id: id, replys: replys})
  }


  display(id, state) {
    return this.getByParams("display", {id: id, state: state})
  }


  hide(id, state) {
    return this.getByParams("hide",{id: id, state: state})
  }


}
