import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";

@Injectable()
export class InvitePrizeService extends BaseService {

  constructor(protected http: HttpClient) {
    super(http, "invitePrize");
  }

}
