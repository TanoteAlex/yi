
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { AttributeGroup } from '../../../models/original/attribute-group.model';
import { AttributeGroupService } from '../../../services/attribute-group.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-attribute-group',
    templateUrl: './add-attribute-group.component.html',
    styleUrls: ['./add-attribute-group.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddAttributeGroupComponent implements OnInit {

    submitting = false;

    attributeGroup: AttributeGroup;

    constructor(private router: Router, private attributeGroupService: AttributeGroupService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }


    submitForm(formValue) {
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.attributeGroupService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/attribute-group/list']);
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
