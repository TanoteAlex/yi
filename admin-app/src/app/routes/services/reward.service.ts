import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "@core/startup/base.service";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";



@Injectable()
export class RewardService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
        super(http, "reward");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    /**
     * 启用 禁用
     * @param id
     * @param state=0启用  state=1禁用
     * @returns {Observable<any>}
     */
    updateState(rewardId) {
        return this.getByParams("updateState", { rewardId: rewardId })
    }


}
