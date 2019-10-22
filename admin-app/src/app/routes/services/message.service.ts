import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class MessageService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
        super(http, "message");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }

    updateState(id) {
        return this.getByParams("updateState", { id: id })
    }

}
