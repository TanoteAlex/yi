import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/internal/Observable";
import {NativeProvider} from "../../services/native-service/native";
import {OrderProvider} from "../../services/order-service/order";

@Injectable()
export class PaymentOrderResolverService implements Resolve<any> {
    constructor(public nativeProvider: NativeProvider, public orderProvider: OrderProvider, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        return new Promise((resolve, reject) => {
            this.nativeProvider.showLoadingForI4().then(() => {
                let data = {
                    orders: [],
                    totalAmount: 0,
                    payInvalidTime: ''
                };
                route.params['ids'].split(',').forEach(e => {
                    this.orderProvider.getOrder(e).then(e1 => {
                        if (e1.result == "SUCCESS") {
                            // data.orders.push(e1.data);
                            data.totalAmount += e1.data.payAmount;
                            data.payInvalidTime = e1.data.payInvalidTime;
                            this.nativeProvider.hideLoadingForI4();
                            resolve(data);
                        }
                    }, err => {
                        this.nativeProvider.showToastFormI4(err.message);
                        reject();
                    });
                });
            })
        });
    }
}