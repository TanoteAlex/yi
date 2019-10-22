import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {DeptService} from '../../../services/dept.service';
import {Dept} from '../../../models/original/dept.model';

@Component({
    selector: 'edit-dept',
    templateUrl: './edit-dept.component.html',
    styleUrls: ['./edit-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditDeptComponent implements OnInit {

    submitting = false;

    dept: Dept;

    constructor(private route: ActivatedRoute, private router: Router, private deptService: DeptService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.deptService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.dept = response.data;
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
            if (formValue.obj.parent && formValue.obj.parent.id == 0) {
                formValue.obj.parent = null
            }
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.dept.id;
            this.deptService.update(formValue.obj).subscribe(response => {
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
