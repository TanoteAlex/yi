import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";
import {BaseService} from "@core/startup/base.service";



@Injectable()
export class PrizeService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
        super(http, "prize");
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
    updateState(prizeId) {
        return this.getByParams("updateState", { prizeId: prizeId })
    }

}
