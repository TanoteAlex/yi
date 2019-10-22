import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class IntegralTaskService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient) {
    super(http, "integralTask");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }


  updateStates(id){
    return this.getByParams("updateState",{id:id})
  }

  updateGrowthValue(id,growthValue){
    return this.getByParams("updateGrowthValue",{id:id,growthValue:growthValue})
  }



    }
