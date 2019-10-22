
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { RecommendCommodityService } from '../../../services/recommend-commodity.service';
import { RecommendCommodityBo } from '../../../models/domain/bo/recommend-commodity-bo.model';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'edit-recommend-commodity',
    templateUrl: './edit-recommend-commodity.component.html',
    styleUrls: ['./edit-recommend-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditRecommendCommodityComponent implements OnInit {

    submitting = false;

    recommendCommodity: RecommendCommodityBo;

    constructor(private route: ActivatedRoute, private router: Router, private recommendCommodityService: RecommendCommodityService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.recommendCommodityService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.recommendCommodity = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
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
            formValue.obj.id = this.recommendCommodity.id;
            this.recommendCommodityService.update(formValue.obj).subscribe(response => {
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
