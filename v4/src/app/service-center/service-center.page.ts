import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";
import {RuleInfoService} from "../../services/common-service/rule-info.service";
import {SUCCESS} from "../Constants";
import {NativeProvider} from "../../services/native-service/native";

@Component({
    selector: 'app-service-center',
    templateUrl: './service-center.page.html',
    styleUrls: ['./service-center.page.scss'],
})
export class ServiceCenterPage implements OnInit {

    list = [];

    constructor(public navCtrl: NavController, public ruleInfoProvider: RuleInfoService, public nativeProvider: NativeProvider) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.ruleInfoProvider.getQuestionType().then( e => {
            if (e.result == SUCCESS){
                this.list = e.data;
            } else{
                this.nativeProvider.showToastFormI4(e.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }

    goFaq(questionTypeId) {
        this.navCtrl.navigateForward(["FaqPage",{questionTypeId:questionTypeId}])
    }

}
