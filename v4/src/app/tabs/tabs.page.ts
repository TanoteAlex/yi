import {Component} from '@angular/core';
import {MemberProvider} from "../../services/member-service/member";
import {AlertController, Events, NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {REFRESH_CUSTOMERCENTER, REFRESH_HOME, REFRESH_SHOPPINGCART} from "../Constants";
import {WechatService} from "../../services/wechat-service/wechat.service";
import {NativeProvider} from "../../services/native-service/native";
import {HomePage} from "../home/home.page";
import {CategoryPage} from "../category/category.page";

@Component({
    selector: 'app-tabs',
    templateUrl: 'tabs.page.html',
    styleUrls: ['tabs.page.scss']
})
export class TabsPage {
    names = ['home.svg',
        'category.svg',
        'loveLife.svg',
        'shoppingCart.svg',
        'customerCenter.svg'];
    selects = ['home_on.svg',
        'category_on.svg',
        'loveLife_on.svg',
        'shoppingCart_on.svg',
        'customerCenter_on.svg'];
    icons = ['/assets/app_icon/tab/home.svg',
        '/assets/app_icon/tab/category.svg',
        '/assets/app_icon/tab/loveLife.svg',
        '/assets/app_icon/tab/shoppingCart.svg',
        '/assets/app_icon/tab/customerCenter.svg'];

    member;

    constructor(public memberProvider: MemberProvider, public navCtrl: NavController, public route: ActivatedRoute, public events: Events, public alertController: AlertController,
                public wechatProvider: WechatService, public nativeProvider: NativeProvider) {
        this.memberProvider.initLoginSession();
    }

    ngAfterViewInit() {
        setTimeout(e => {
            this.memberProvider.getLocation();
        }, 2000)
    }


    selectTab(index) {
        this.icons.forEach((e, i) => {
            this.icons[i] = this.icons[i].replace(this.selects[i], this.names[i])
        });
        this.icons[index] = this.icons[index].replace(this.names[index], this.selects[index]);
        if (index == 0) {
            this.events.publish(REFRESH_HOME);
        }
        if (index == 3) {
            this.events.publish(REFRESH_SHOPPINGCART);
        }
        if (index == 4) {
            this.events.publish(REFRESH_CUSTOMERCENTER);
        }
    }
}
