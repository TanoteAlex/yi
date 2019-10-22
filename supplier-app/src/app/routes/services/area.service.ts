import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class AreaService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
        super(http, "area");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }


    getAreas(area) {
        return this.getByParams("getAreas", { parentId: area.id });
    }

  getProvinces(){
      return this.getByParams("getProvinces");
    }

  getAllAreas(area){
    return this.getByParams("getAllAreas", { parentId: area.id });
  }

}
