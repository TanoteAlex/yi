import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ExpressTemplate} from '../../../models/original/express-template.model';
import {ExpressTemplateService} from '../../../services/express-template.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
    selector: 'add-express-template',
    templateUrl: './add-express-template.component.html',
    styleUrls: ['./add-express-template.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddExpressTemplateComponent implements OnInit {

    submitting = false;

    expressTemplate: ExpressTemplate;

    constructor(private router:Router,private expressTemplateService: ExpressTemplateService, public msgSrv: NzMessageService,
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
            this.expressTemplateService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/express-template/list']);
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
