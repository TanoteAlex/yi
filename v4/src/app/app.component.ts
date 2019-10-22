import {Component} from '@angular/core';
import {ModalController, NavController, Platform} from '@ionic/angular';
import {SplashScreen} from '@ionic-native/splash-screen/ngx';
import {StatusBar} from '@ionic-native/status-bar/ngx';
import {NativeProvider} from "../services/native-service/native";
import {Subscription} from "rxjs/internal/Subscription";
import {NavigationEnd, Router} from "@angular/router";
import {AppConfig} from "./app.config";
import {AppMinimize} from "@ionic-native/app-minimize";
import {WelcomePage} from "./welcome/welcome.page";

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html'
})
export class AppComponent {
    backButtonPressed: boolean = false; //用于判断返回键是否触发
    customBackActionSubscription: Subscription;
    url;

    constructor(private platform: Platform, private splashScreen: SplashScreen, private statusBar: StatusBar, private modalCtrl: ModalController,
                private nativeService: NativeProvider, public navCtrl: NavController, private router: Router, private appConfig: AppConfig,
                ) {
        this.initRouterListen();
        // this.initializeApp();
        this.platform.ready().then(() => {
            // this.registerBackButtonAction();//注册返回按键事件
            this.goWelcomePage();//启动页轮播
            // this.platform.resume.subscribe();//弹出框
        });
    }

    initializeApp() {
        /**
         * 状态条
         */
        this.platform.ready().then(() => {
            this.statusBar.overlaysWebView(false);
            if (this.nativeService.isAndroid()) {
                this.statusBar.styleLightContent();
            } else {
                this.statusBar.styleDefault();
            }
            // document.getElementById('splashShow').style.display = 'none'
        });
        /*this.platform.backButton.subscribe(e => {
            try {
                alert("物理返回，关闭modal");
                this.modalCtrl.dismiss()
            }catch (e) {
                alert("物理返回");
            }
        });*/

        /**
         * 物理返回，模态框需要关闭，app没问题
         * 公众号有这个bug，默认返回不知道怎么阻止。暂时同时dismiss模态框，pop页面
         */
        window.addEventListener("popstate", () => {
            try {
                this.modalCtrl.dismiss();
                return
            } catch (e) {

            }
        }, false);
    }


    /**
     * 再按一次退出应用
     * 参考，https://blog.csdn.net/z15802933724/article/details/82788606
     */
    registerBackButtonAction() {
        this.customBackActionSubscription = this.platform.backButton.subscribe(() => {
            if (this.url.indexOf('app/tabs/home') != -1 && this.appConfig.isApp && !this.backButtonPressed) {
                this.nativeService.showToastFormI4('再按一次退出应用');
                this.backButtonPressed = true;
                setTimeout(() => this.backButtonPressed = false, 2000);
                return
            }
            if (this.backButtonPressed) {
                AppMinimize.minimize().then(() => {
                    this.backButtonPressed = false;
                    return
                });
            }
        });
    }

    initRouterListen() {
        this.router.events.subscribe(event => { // 需要放到最后一个执行
            if (event instanceof NavigationEnd) {
                this.url = event.url;
            }
        });
    }

    /**
     * 进入轮播启动页
     */
    goWelcomePage() {
        if (this.appConfig.isApp) {
            this.navCtrl.navigateRoot("WelcomePage");
        }
    }

}
