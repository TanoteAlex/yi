import {Component, OnInit} from '@angular/core';
import {inputHandle} from "../../util/bug-util";

@Component({
    selector: 'app-forget-password',
    templateUrl: './forget-password.page.html',
    styleUrls: ['./forget-password.page.scss'],
})
export class ForgetPasswordPage implements OnInit {

    constructor() {
    }

    ngOnInit() {
        inputHandle();
    }

}
