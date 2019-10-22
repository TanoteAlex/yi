import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {FreightTemplate} from '../../../models/original/freight-template.model';
import {FreightTemplateService} from '../../../services/freight-template.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-freight-template',
    templateUrl: './add-freight-template.component.html',
    styleUrls: ['./add-freight-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddFreightTemplateComponent implements OnInit {

    submitting = false;

    freightTemplate: FreightTemplate;

    constructor(private router:Router,private freightTemplateService: FreightTemplateService, public msgSrv: NzMessageService,
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
            this.freightTemplateService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/freight-template/list']);
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
