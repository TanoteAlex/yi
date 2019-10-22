import {Component, ComponentFactoryResolver, Input, OnChanges, OnDestroy, SimpleChanges, ViewChild, ViewContainerRef} from '@angular/core';
import {ShowTwoCommoditiesComponent} from "../show-two-commodities/show-two-commodities.component";
import {ShowThreeCommoditiesComponent} from "../show-three-commodities/show-three-commodities.component";
import {ShowFourCommoditiesComponent} from "../show-four-commodities/show-four-commodities.component";
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-show-commodities',
    templateUrl: './show-commodities.component.html',
    styleUrls: ['./show-commodities.component.scss']
})
export class ShowCommoditiesComponent implements OnChanges,OnDestroy {
    @Input()
    item;

    @ViewChild('showCommoditiesContent', {read: ViewContainerRef}) _showCommoditiesContent

    _controlRef;

    constructor(public navCtrl: NavController, private componentFactoryResolver: ComponentFactoryResolver) {
    }


    ngOnChanges(changes: SimpleChanges): void {
        if (changes.item.currentValue) {
            this._showCommoditiesContent.clear();
            if (changes.item.currentValue.showMode == 2) {
                const controlComponentFactory = this.componentFactoryResolver.resolveComponentFactory(ShowTwoCommoditiesComponent);
                this._controlRef = this._showCommoditiesContent.createComponent(controlComponentFactory);
            }
            if (changes.item.currentValue.showMode == 3) {
                const controlComponentFactory = this.componentFactoryResolver.resolveComponentFactory(ShowThreeCommoditiesComponent);
                this._controlRef = this._showCommoditiesContent.createComponent(controlComponentFactory);
            }
            if (changes.item.currentValue.showMode == 4) {
                const controlComponentFactory = this.componentFactoryResolver.resolveComponentFactory(ShowFourCommoditiesComponent);
                this._controlRef = this._showCommoditiesContent.createComponent(controlComponentFactory);
            }
            let commodities = this.item.commodities;
            this._controlRef.instance.item = {
                commodities:commodities
            };
            this._controlRef.instance.goCommodity = this.goCommodity;
            this._controlRef.instance.goUrl = this.goUrl;
        }
    }

    goCommList() {
        this.navCtrl.navigateForward("CommListPage")
    }


    goCommodity(id, floorId) {
        this.navCtrl.navigateForward(["CommodityPage", {id: id}])
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

    ngOnDestroy() {
        if(this._controlRef){
            this._controlRef.destroy();
        }
    }

}
