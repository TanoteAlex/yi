import {ActivatedRoute, ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {NativeProvider} from "../../../services/native-service/native";
import {GroupActiveService} from "../../../services/group-active-service/group-active.service";
import {Observable} from "rxjs/internal/Observable";
import {CommodityProvider} from "../../../services/commodity-service/commodity";
import {Injectable} from "@angular/core";

@Injectable()
export class CommodityGroupResolverService implements Resolve<any> {
    constructor(public nativeProvider: NativeProvider,
                public commodityProvider: CommodityProvider,
                public router: ActivatedRoute,
                public activeProvider: GroupActiveService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        return new Promise((resolve, reject) => {
            this.nativeProvider.showLoadingForI4().then(() => {
                this.activeProvider.getGroupProduct(route.params["id"]).then(data => {
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