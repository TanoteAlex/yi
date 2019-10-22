import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class CommunityService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(http: _HttpClient) {
        super(http, "community");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }


    /**
     * 启用 禁用
     * @param id
     * @param shel=0启用  shelf=1禁用
     * @returns {Observable<any>}
     */
    updateState(communityId) {
        return this.getByParams("updateState", { communityId: communityId })
    }


}
