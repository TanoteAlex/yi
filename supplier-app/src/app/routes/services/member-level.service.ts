import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import { BaseService } from './base.service';


@Injectable()
export class MemberLevelService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
        super(http, "memberLevel");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

}
