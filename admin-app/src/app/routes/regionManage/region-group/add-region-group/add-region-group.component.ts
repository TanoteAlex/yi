
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { RegionGroup } from '../../../models/original/region-group.model';
import { RegionGroupService } from '../../../services/region-group.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-region-group',
    templateUrl: './add-region-group.component.html',
    styleUrls: ['./add-region-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddRegionGroupComponent implements OnInit {

    submitting = false;

    regionGroup: RegionGroup;

    constructor(private router:Router,private regionGroupService: RegionGroupService, public msgSrv: NzMessageService,
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
            this.regionGroupService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/region-group/list']);
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
