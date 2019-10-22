import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {ExpressTemplateService} from '../../../services/express-template.service';
import {ExpressTemplate} from '../../../models/original/express-template.model';

@Component({
  selector: 'edit-express-template',
  templateUrl: './edit-express-template.component.html',
  styleUrls: ['./edit-express-template.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditExpressTemplateComponent implements OnInit {

    submitting = false;

expressTemplate: ExpressTemplate;

    constructor(private route: ActivatedRoute,private router:Router,private expressTemplateService: ExpressTemplateService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.expressTemplateService.getByIdForSupplier(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.expressTemplate = response.data;
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

    confirmSub(formValue){
        if (formValue) {
            this.submitting = true;
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj[0].id = this.expressTemplate.id;
            this.expressTemplateService.updateForSupplier(formValue.obj[0]).subscribe(response => {
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
