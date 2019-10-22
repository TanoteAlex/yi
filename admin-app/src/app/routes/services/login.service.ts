import {Injectable} from '@angular/core';
import {BaseService} from "@core/startup/base.service";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {environment} from "@env/environment";

@Injectable()
export class LoginService extends BaseService {


  onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);


  constructor(http: HttpClient) {
    super(http, "auth");
  }


  login(username, password): any {
    return this.http.request("GET", environment.SERVER_URL + '/auth/login?username=' + username + "&password=" + password, {})
  }

  getUserMenu(): any {
    return this.http.request("GET", environment.SERVER_URL + '/auth/getUserMenu', {})
  }

}
