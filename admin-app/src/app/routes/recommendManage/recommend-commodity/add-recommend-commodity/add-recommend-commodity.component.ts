import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { RecommendCommodityBo } from '../../../models/domain/bo/recommend-commodity-bo.model';
import { RecommendCommodityService } from '../../../services/recommend-commodity.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-recommend-commodity',
    templateUrl: './add-recommend-commodity.component.html',
    styleUrls: ['./add-recommend-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRecommendCommodityComponent implements OnInit {

    submitting = false;

    recommendCommodity: RecommendCommodityBo;

    constructor(private router: Router, private recommendCommodityService: RecommendCommodityService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue) {
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.recommendCommodityService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/recommend-commodity/list']);
                } else {
                    this.msgSrv.error('请求失败' + response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误' + error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }

}
