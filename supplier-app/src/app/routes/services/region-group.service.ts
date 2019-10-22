import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RegionGroupService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
        super(http, "regionGroup");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    getRegionGroups() {
        return this.getByParams("getRegionGroups");
    }

    /**
     * 区域列表启用 禁用
     * @param id
     * @param shel=0启用  shelf=1禁用
     * @returns {Observable<any>}
     */
    updateState(regionGroupId) {
        return this.getByParams("updateState", { regionGroupId: regionGroupId })
    }

}
