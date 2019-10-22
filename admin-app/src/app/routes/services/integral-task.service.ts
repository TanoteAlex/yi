import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from "./base.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class IntegralTaskService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
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
