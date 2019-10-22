import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class RegionGroupService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
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
     * @param state=0启用  state=1禁用
     * @returns {Observable<any>}
     */
    updateState(regionGroupId) {
        return this.getByParams("updateState", { regionGroupId: regionGroupId })
    }

}
