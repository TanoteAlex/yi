
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { AreaColumnCommodityBo } from '../../../models/domain/bo/area-column-commodity-bo.model';
import { AreaColumnCommodityService } from '../../../services/area-column-commodity.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-area-column-commodity',
    templateUrl: './add-area-column-commodity.component.html',
    styleUrls: ['./add-area-column-commodity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAreaColumnCommodityComponent implements OnInit {

    submitting = false;

    areaColumnCommodity: AreaColumnCommodityBo;

    constructor(private router:Router,private areaColumnCommodityService: AreaColumnCommodityService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.confirmSub(event)
        }
    }

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.areaColumnCommodityService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/area-column-commodity/list']);
                } else {
                    this.msgSrv.error('请求失败'+response.message);
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error('请求错误'+error.message);
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }

}
