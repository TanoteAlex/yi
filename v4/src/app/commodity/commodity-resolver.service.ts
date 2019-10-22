import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/internal/Observable";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {NativeProvider} from "../../services/native-service/native";

@Injectable()
export class CommodityResolverService implements Resolve<any> {
    constructor(public nativeProvider: NativeProvider, public commodityProvider: CommodityProvider, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        return new Promise((resolve, reject) => {
            this.nativeProvider.showLoadingForI4().then(() => {
                this.commodityProvider.getCommodity(route.params["id"]).then(data => {
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