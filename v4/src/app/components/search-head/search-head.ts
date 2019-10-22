import {Component, Input, ViewChild} from '@angular/core';
import {Events, NavController} from "@ionic/angular";
import {LOCAL_CITY} from "../../Constants";
import {MemberProvider} from "../../../services/member-service/member";


/**
 * Generated class for the SearchHeadComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
    selector: 'search-head',
    templateUrl: 'search-head.html',
    styleUrls: ['./search-head.scss']
})
export class SearchHeadComponent {
    @Input()
    backgroundColor: string = "#ffffff";                     //头部搜索栏背景色

    @Input()
    isHome: boolean = false;

    /*定位城市*/
    @Input()
    localCity = "请定位";

    @ViewChild('p') p;

    constructor(public events: Events, public memberProvider: MemberProvider, public navCtrl: NavController) {
        this.events.subscribe(LOCAL_CITY, data => {
            this.localCity = this.memberProvider.getLocationInfo();
            this.p.nativeElement.innerHTML = this.localCity
        })
    }

    goNews() {
        this.navCtrl.navigateForward("NewsPage");
    }

    goLocation() {
        this.navCtrl.navigateForward("LocationPage")
    }

    goSearch() {
        this.navCtrl.navigateForward("SearchResultPage")
    }


}
