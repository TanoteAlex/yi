
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { AreaColumnBo } from '../../../models/domain/bo/area-column-bo.model';
import { AreaColumnService } from '../../../services/area-column.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-area-column',
    templateUrl: './add-area-column.component.html',
    styleUrls: ['./add-area-column.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAreaColumnComponent implements OnInit {

    submitting = false;

    areaColumn: AreaColumnBo;

    constructor(private router:Router,private areaColumnService: AreaColumnService, public msgSrv: NzMessageService,
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
            this.areaColumnService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/area-column/list']);
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
