import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class PositionService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor( http: HttpClient) {
    super(http,"position");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }


    /**
     *  禁用
     * @param id
     * @param 禁用
     * @returns {Observable<any>}
     */
    updateStateDisable(id){
      return this.getByParams("updateStateDisable", {id: id })
    }

    /**
     *  启用
     * @param id
     * @param 启用
     * @returns {Observable<any>}
     */
    updateStateEnable(id){
      return this.getByParams("updateStateEnable", {id: id })
    }



    }
