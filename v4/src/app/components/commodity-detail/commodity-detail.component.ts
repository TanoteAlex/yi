import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Commodity} from "../../../domain/original/commodity.model";
import {ModalController} from "@ionic/angular";
import {PageQuery} from "../../../util/page-query.model";
import {CommodityProvider} from "../../../services/commodity-service/commodity";
import {NativeProvider} from "../../../services/native-service/native";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
    selector: 'commodity-detail',
    templateUrl: './commodity-detail.component.html',
    styleUrls: ['./commodity-detail.component.scss']
})
export class CommodityDetailComponent implements OnInit {

    private _commodity: Commodity;

    @Input()
    set commodity(commodity: Commodity) {
        if (commodity && typeof commodity.description === 'string'){
            commodity.description = commodity.description.replace('<p', '<p style="font-size:0!important" ');
            commodity.description = this.domSanitizer.bypassSecurityTrustHtml(commodity.description);
        }
        this._commodity = commodity;
    }

    get commodity(): Commodity { return this._commodity; }

    @Input()
    select: string = "introduction";

    @Output()
    onCustomEvent: EventEmitter<any> = new　EventEmitter();

    preMemberId = 'y';

    pageQuery: PageQuery = new PageQuery();
    comments = [];

    constructor(public modalCtrl: ModalController, public commodityProvider: CommodityProvider, public nativeProvider: NativeProvider,public domSanitizer: DomSanitizer, ) { }

    ngOnInit() {
    }

    getCommentData(commodity) {
        this.commodity=commodity;
        this.pageQuery.pushParamsRequests('commodity.id',this.commodity.id);
        this.commodityProvider.queryCommodityComment(this.pageQuery.requests).then( e=> {
            this.comments = e.content;
            this.pageQuery.covertResponses(e);
        }, err => this.nativeProvider.showToastFormI4(err.message))
    }

    doInfinite(infiniteScroll) {
        if (!this.pageQuery.isLast()) {
            this.pageQuery.plusPage();
            this.commodityProvider.queryCommodityComment(this.pageQuery.requests).then(data => {
                    this.comments = this.comments.concat(data.content);
                    this.pageQuery.covertResponses(data);
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

    goEvaluation() {
        this.select = "evaluation";
        this.onCustomEvent.emit(this.select);
    }

}
