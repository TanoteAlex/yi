import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {ReturnOrderService} from '../../../services/return-order.service';
import {ReturnOrder} from '../../../models/original/return-order.model';

@Component({
  selector: 'edit-return-order',
  templateUrl: './edit-return-order.component.html',
  styleUrls: ['./edit-return-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditReturnOrderComponent implements OnInit {

    submitting = false;

returnOrder: ReturnOrder;

    constructor(private route: ActivatedRoute,private router:Router,private returnOrderService: ReturnOrderService, public msgSrv:
        NzMessageService,private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.returnOrderService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.returnOrder = response.data;
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
            formValue.obj.id = this.returnOrder.id;
            this.returnOrderService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/returnOrder/list']);
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
