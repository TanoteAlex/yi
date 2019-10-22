import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {CommodityService} from '../../../services/commodity.service';
import {Commodity} from '../../../models/original/commodity.model';

@Component({
    selector: 'edit-commodity',
    templateUrl: './edit-commodity.component.html',
})
export class EditCommodityComponent implements OnInit {

    submitting = false;

    commodity: Commodity;

    constructor(private route: ActivatedRoute, private router: Router, private commodityService: CommodityService, public msgSrv: NzMessageService, private modalService: NzModalService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.commodityService.getVoByIdForSupplier(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.commodity = response.data;
            } else {
                this.msgSrv.error(response.message ? response.message : "请求失败");
            }
        }, error => {
            this.msgSrv.error(error.message ? error.message : "请求错误");
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
            formValue.obj.id = this.commodity.id;
            formValue.obj.auditState = this.commodity.auditState;
            this.commodityService.updateForSupplier(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/commodity/list']);
                } else {
                    this.msgSrv.error(response.message ? response.message : "请求失败");
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;

            }, error => {
                this.msgSrv.error(error.message ? error.message : "请求错误");
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}

