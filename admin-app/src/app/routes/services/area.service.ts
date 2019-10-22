import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class AreaService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
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
