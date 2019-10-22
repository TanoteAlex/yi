import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BaseService} from '@core/startup/base.service';
import {BehaviorSubject} from "rxjs/BehaviorSubject";


@Injectable()
export class CouponReceiveService extends BaseService {

    onRefreshFileManager: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);

    constructor(protected http: HttpClient) {
        super(http, "couponReceive");
    }

    setRefreshFileManager() {
        this.onRefreshFileManager.next(true);
    }


    grantCoupon(couponReceiveBo: any) {
        return this.postData("grantCoupon", couponReceiveBo);
    }

    grantStored(couponReceiveBo: any) {
        return this.postData("grantStored", couponReceiveBo);
    }


}
