import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {Observable} from "rxjs/internal/Observable";
import {NativeProvider} from "../../services/native-service/native";
import {Injectable} from "@angular/core";
import {ArticleProvider} from "../../services/article-service/article";

@Injectable()
export class ArticleDetailResolverService implements Resolve<any> {
    constructor(public nativeProvider: NativeProvider, public articleProvider: ArticleProvider, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
        return new Promise((resolve, reject) => {
            this.nativeProvider.showLoadingForI4().then(() => {
                this.articleProvider.getArticle(route.params["articleId"]).then(data => {
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