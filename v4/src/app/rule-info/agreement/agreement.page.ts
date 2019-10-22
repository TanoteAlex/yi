import {Component, OnInit} from '@angular/core';
import {RuleInfoService} from "../../../services/common-service/rule-info.service";
import {DomSanitizer} from "@angular/platform-browser";
import {SUCCESS} from "../../Constants";
import {NativeProvider} from "../../../services/native-service/native";

@Component({
    selector: 'app-agreement',
    templateUrl: './agreement.page.html',
    styleUrls: ['./agreement.page.scss'],
})
export class AgreementPage implements OnInit {

    content;

    constructor(public ruleProvider: RuleInfoService, public domSanitizer: DomSanitizer, public nativeProvider: NativeProvider) {
    }

    ngOnInit() {
    }

    ionViewWillEnter() {
        this.ruleProvider.getBasicRule().then(e => {
            if (e.result == SUCCESS) {
                this.content = e.data.content.replace('<p', '<p style="font-size:0!important" ');
                this.content = this.domSanitizer.bypassSecurityTrustHtml(e.data.content);
            } else {
                this.nativeProvider.showToastFormI4(e.message)
            }
        }, err => this.nativeProvider.showToastFormI4(err.message));
    }


}
