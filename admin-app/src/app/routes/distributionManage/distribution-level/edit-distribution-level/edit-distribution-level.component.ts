
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { DistributionLevelService } from '../../../services/distribution-level.service';
import { DistributionLevelBo } from '../../../models/domain/bo/distribution-level-bo.model';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'edit-distribution-level',
  templateUrl: './edit-distribution-level.component.html',
  styleUrls: ['./edit-distribution-level.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditDistributionLevelComponent implements OnInit {

    submitting = false;

    distributionLevel: DistributionLevelBo;

    constructor(private route: ActivatedRoute,private router:Router,private distributionLevelService: DistributionLevelService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.distributionLevelService.getBoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.distributionLevel = response.data;
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
            formValue.obj.id = this.distributionLevel.id;
            this.distributionLevelService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/distribution-level/list']);
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
