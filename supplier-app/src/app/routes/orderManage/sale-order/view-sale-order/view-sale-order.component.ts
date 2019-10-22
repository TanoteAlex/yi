import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from "../../../models/constants.model";
import {SaleOrder} from "../../../models/original/sale-order.model";
import {Location} from "@angular/common";
import {SaleOrderService} from "../../../services/sale-order.service";
import { LOGISTICSCOMPANY } from '../../../../supplierConstants';

@Component({
    selector: 'view-sale-order',
    templateUrl: './view-sale-order.component.html',
    styleUrls: ['./view-sale-order.component.less'],
    encapsulation: ViewEncapsulation.None
})
export class ViewSaleOrderComponent implements OnInit {

    saleOrder: SaleOrder = new SaleOrder;

    constructor(private route: ActivatedRoute, private location: Location, private saleOrderService: SaleOrderService,
        public msgSrv: NzMessageService, public router: Router, private modalService: NzModalService) {
    }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.saleOrderService.getByIdForSupplier(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.saleOrder = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
    }






    goBack() {
        this.location.back();
    }

    goUpdateOrderState(id, state) {
        this.saleOrderService.updateOrderStateForSupplier(id, state).subscribe(response => {
            if (response.result == SUCCESS) {
                this.router.navigate(['/dashboard/sale-order/list']);
                this.msgSrv.success('操作成功')
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);

        })
    }


    transformation = LOGISTICSCOMPANY;

    deliver() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

}
