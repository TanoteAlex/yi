import {Component} from '@angular/core';
import {Category} from "../../domain/original/category.model";
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {CommodityProvider} from "../../services/commodity-service/commodity";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-about',
    templateUrl: 'category.page.html',
    styleUrls: ['category.page.scss']
})
export class CategoryPage {
    categories: Category[] = [];

    isCategoryPage;

    category: Category;

    constructor(public nativeProvider: NativeProvider, public memberProvider: MemberProvider, public navCtrl: NavController, public commodityProvider: CommodityProvider) {
        // let _category = this
        // window.addEventListener('hashchange', function () {
        //     setTimeout(function () {
        //         var url = window.location.toString();
        //         var id = url.split("#")[2];
        //         if (id) {
        //             if (document.getElementById(id) && !_category.isCategoryPage) {
        //                 var t = document.getElementById(id).offsetTop;
        //                 // _category.content.scrollByPoint(0,t,500)
        //                 document.getElementById('categoryUl').scrollTo({top: t})
        //             }
        //         }
        //     }, 150)
        // })
    }

    ionViewWillEnter() {
        this.commodityProvider.getCommodityCategory().then(data => {
            this.categories = data.data;
            this.allCategory(this.categories);
            if (this.categories && this.categories.length != 0) {
                this.category = this.categories[0];
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }


    selectCategory(i, id) {
        // window.location.href = './#/tabs/(category:category)#' + id;
        this.isCategoryPage = true;
        this.category = this.categories[i];
    }

    goCommList(id) {
        this.isCategoryPage = false;
        this.navCtrl.navigateForward(["CommListPage", {id: id}])
    }

    goSearch() {
        this.isCategoryPage = false;
        this.navCtrl.navigateForward("SearchResultPage")
    }

    /*全部商品*/
    allCategory(all) {
        let childrenAttr = [];
        all.forEach(e => {
            e.children.forEach(e1 => {
                childrenAttr.push(e1);
            })
        });

        all.push({
            categoryName: "全部商品",
            children: childrenAttr,
            id: 0,
        });
    }

    goUrl(banner) {
        // 1商品2文章
        if (banner.linkType == 1) {
            this.navCtrl.navigateForward(["CommodityPage", {id: banner.linkId}])
        } else if (banner.linkType == 2) {
            this.navCtrl.navigateForward(["ArticleDetailPage", {"articleId": banner.linkId}])
        } else {
            return
        }
    }
}
