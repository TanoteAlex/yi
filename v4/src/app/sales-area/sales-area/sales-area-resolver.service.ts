import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/internal/Observable";
import {Injectable} from "@angular/core";
import {NativeProvider} from '../../../services/native-service/native';
import {SalesAreaProvider} from '../../../services/sales-area-service/sales-area';

@Injectable()
export class SalesAreaResolverService implements Resolve<any> {
    constructor(public nativeProvider: NativeProvider, public salesAreaProvider: SalesAreaProvider, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        return new Promise((resolve, reject) => {
            this.nativeProvider.showLoadingForI4().then(() => {
                this.salesAreaProvider.getSalesAreaById(route.params["id"]).then(data => {
                    this.nativeProvider.hideLoadingForI4()
                    resolve(data.data);
                }, err => {
                    this.nativeProvider.showToastFormI4(err.message);
                    reject();
                });
            })

        });
    }
}