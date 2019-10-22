
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { FreightTemplateConfigService } from '../../../services/freight-template-config.service';
import {FreightTemplateConfig} from "../../../models/original/freight-template-config.model";
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'edit-freight-template-config',
  templateUrl: './edit-freight-template-config.component.html',
  styleUrls: ['./edit-freight-template-config.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditFreightTemplateConfigComponent implements OnInit {

    submitting = false;

    freightTemplateConfig: FreightTemplateConfig;

    constructor(private route: ActivatedRoute,private router:Router,private freightTemplateConfigService: FreightTemplateConfigService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.freightTemplateConfigService.getByIdForSupplier(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.freightTemplateConfig = response.data;
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
            formValue.obj.id = this.freightTemplateConfig.id;
            this.freightTemplateConfigService.updateForSupplier(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/freight-template-config/list']);
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
