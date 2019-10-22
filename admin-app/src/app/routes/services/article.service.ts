import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class ArticleService extends BaseService {

  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

  constructor(http: HttpClient) {
    super(http, "article");
  }

  setRefreshFileManager() {
    this.onRefreshFileManager.next(true);
  }

  updateDisplayState(articleId) {
    return this.getByParams("updateDisplayState", {articleId: articleId})
  }

}
