import {Component, OnInit} from '@angular/core';
import {RuleInfoService} from "../../../services/common-service/rule-info.service";
import {NativeProvider} from "../../../services/native-service/native";
import {ActivatedRoute} from "@angular/router";
import {SUCCESS} from "../../Constants";

@Component({
    selector: 'app-faq',
    templateUrl: './faq.page.html',
    styleUrls: ['./faq.page.scss'],
})
export class FaqPage implements OnInit {

    list = [];

    constructor(public ruleInfoProvider: RuleInfoService, public nativeProvider: NativeProvider, public route: ActivatedRoute,) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.ruleInfoProvider.getQuestion(this.route.params["value"].questionTypeId).then(e => {
            if (e.result == SUCCESS) {
                this.list = e.data;
            } else {
                this.nativeProvider.showToastFormI4(e.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message))
    };
}
