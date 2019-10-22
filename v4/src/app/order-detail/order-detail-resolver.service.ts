import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/internal/Observable";
import {NativeProvider} from "../../services/native-service/native";
import {OrderProvider} from "../../services/order-service/order";

@Injectable()
export class OrderDetailResolverService implements Resolve<any> {
    constructor(public nativeProvider: NativeProvider, public orderProvider: OrderProvider, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        return new Promise((resolve, reject) => {
            this.nativeProvider.showLoadingForI4().then(() => {
                this.orderProvider.getOrder(route.params["orderId"]).then(data => {
                    this.nativeProvider.hideLoadingForI4();
                    resolve(data.data);
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    reject();
                });
            })
        });
    }
}