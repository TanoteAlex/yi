

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import { RecommendCommodityService } from "../../../services/recommend-commodity.service";
import { RecommendCommodityVo } from '../../../models/domain/vo/recommend-commodity-vo.model';
import { Location } from "@angular/common";

@Component({
    selector: 'view-recommend-commodity',
    templateUrl: './view-recommend-commodity.component.html',
    styleUrls: ['./view-recommend-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewRecommendCommodityComponent implements OnInit {

    recommendCommodity: RecommendCommodityVo = new RecommendCommodityVo;

    constructor(private route: ActivatedRoute, private location: Location, private recommendCommodityService: RecommendCommodityService,
        public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, ) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.recommendCommodityService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.recommendCommodity = response.data;
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack() {
        this.location.back();
    }

}
