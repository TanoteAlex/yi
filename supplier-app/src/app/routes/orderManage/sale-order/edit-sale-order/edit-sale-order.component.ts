import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {SaleOrderService} from '../../../services/sale-order.service';
import {SaleOrder} from '../../../models/original/sale-order.model';

@Component({
    selector: 'edit-sale-order',
    templateUrl: './edit-sale-order.component.html',
    styleUrls: ['./edit-sale-order.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditSaleOrderComponent implements OnInit {

    submitting = false;

    saleOrder: SaleOrder;

    constructor(private route: ActivatedRoute, private router: Router, private saleOrderService: SaleOrderService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.saleOrderService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.saleOrder = response.data;
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
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            formValue.obj.id = this.saleOrder.id;
            this.saleOrderService.update(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/saleOrder/list']);
                } else {
                    this.msgSrv.error(response.message ? response.message : '请求失败');
                }
                this.msgSrv.remove(messageId);
                this.submitting = false;
            }, error => {
                this.msgSrv.error(error.message ? error.message : '请求错误');
                this.msgSrv.remove(messageId);
                this.submitting = false;
            });
        }
    }
}
