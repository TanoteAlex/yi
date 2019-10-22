
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import {GroupBuyOrderService} from '../../../services/group-buy-order.service';
import {GroupBuyOrderBo} from '../../../models/domain/bo/group-buy-order-bo.model';
import {SUCCESS} from "../../../models/constants.model";

@Component({
  selector: 'edit-group-buy-order',
  templateUrl: './edit-group-buy-order.component.html',
  styleUrls: ['./edit-group-buy-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditGroupBuyOrderComponent implements OnInit {

    submitting = false;

  groupBuyOrder: GroupBuyOrderBo;

    constructor(private route: ActivatedRoute,private router:Router,private groupBuyOrderService: GroupBuyOrderService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.groupBuyOrderService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.groupBuyOrder = response.data;
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
            formValue.obj.id = this.groupBuyOrder.id;
            this.groupBuyOrderService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/group-buy-order/list']);
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
