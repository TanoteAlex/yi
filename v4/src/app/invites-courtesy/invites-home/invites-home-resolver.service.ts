import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/internal/Observable";
import {Injectable} from "@angular/core";
import {NativeProvider} from '../../../services/native-service/native';
import {InviteActivityProvider} from "../../../services/activity-service/inviteActivity";

@Injectable()
export class InvitesHomeResolverService implements Resolve<any> {
    constructor(public nativeProvider: NativeProvider, public inviteActivityProvider:InviteActivityProvider, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        // return new Promise((resolve, reject) => {
        //     this.nativeProvider.showLoadingForI4().then(() => {
        //         this.inviteActivityProvider.getLatestInviteActivity(route.params["preMemberId"]).then(data => {
        //             this.nativeProvider.hideLoadingForI4();
        //             resolve(data.data);
        //         }, err => {
        //             this.nativeProvider.showToastFormI4(err.message);
        //             reject();
        //         });
        //     })
        //
        // });
        return null;
    }
}