import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class QuestionService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient ) {
    super(http, "question");
    }

    setRefreshFileManager(){
    this.onRefreshFileManager.next(true);
    }

  enable(id){
    return this.getByParams("enable",{id:id})
  }


  disable(id){
    return this.getByParams("disable",{id:id})
  }



    }
