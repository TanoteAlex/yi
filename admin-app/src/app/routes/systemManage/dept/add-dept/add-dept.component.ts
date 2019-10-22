import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {Dept} from '../../../models/original/dept.model';
import {DeptService} from '../../../services/dept.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-dept',
    templateUrl: './add-dept.component.html',
    styleUrls: ['./add-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddDeptComponent implements OnInit {

    submitting = false;

    dept: Dept;

    constructor(private router: Router, private deptService: DeptService, public msgSrv: NzMessageService,
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
            if (formValue.obj.parent && formValue.obj.parent.id == 0) {
                formValue.obj.parent = null
            }
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.deptService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/dept/list']);
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
