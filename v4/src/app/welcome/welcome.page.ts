import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-welcome',
    templateUrl: './welcome.page.html',
    styleUrls: ['./welcome.page.scss'],
})
export class WelcomePage implements OnInit {

    imgs = ["../../assets/splash.png", "../../assets/splash.png", "../../assets/splash.png"];
    timeOut: number = 5;
    constructor(public navCtrl: NavController,) {
    }

    ngOnInit() {
        this.countDown();
    }

    goToHome() {
        this.countDown = function () {
            return
        };
        this.navCtrl.navigateRoot('/app/tabs/home',true,{replaceUrl:true});
    }

    countDown() {
        if (this.timeOut <= 1) {
            this.goToHome();
            return
        }
        setTimeout(() => {
            this.timeOut--;
            this.countDown();
        }, 1000)
    }

}
