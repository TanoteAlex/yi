import {Injectable} from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { BaseService } from './base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";



@Injectable()
export class QuestionService extends BaseService {

  onRefreshFileManager:BehaviorSubject<boolean> =new BehaviorSubject<boolean>(null);

    constructor(protected http: _HttpClient ) {
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
