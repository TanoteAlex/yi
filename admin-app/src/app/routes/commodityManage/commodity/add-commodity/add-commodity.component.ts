import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Commodity} from "../../../models/original/commodity.model";
import {CommodityService} from "../../../services/commodity.service";
import {SUCCESS} from "../../../models/constants.model";


@Component({
    selector: 'add-commodity',
    templateUrl: './add-commodity.component.html',
})
export class AddCommodityComponent implements OnInit {

    submitting = false;

    commodity: Commodity;

    constructor(private router: Router, private commodityService: CommodityService, public msgSrv: NzMessageService, private modalService: NzModalService) {
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
            formValue.obj.auditState = 2;
            this.commodityService.add(formValue.obj).subscribe(response => {
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

