import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {SaleOrderService} from '../../../services/sale-order.service';
import {NzMessageService} from "ng-zorro-antd";
import {SaleOrder} from "../../../models/original/sale-order.model";
import {SUCCESS} from "../../../models/constants.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'app-edit-price',
    templateUrl: './edit-price.component.html',
    encapsulation: ViewEncapsulation.None
})
export class EditPriceComponent implements OnInit {

    commonForm: FormGroup;


    saleOrder: SaleOrder = new SaleOrder;

    constructor(private route: ActivatedRoute, private location: Location,
        private saleOrderService: SaleOrderService, public msgSrv:
            NzMessageService, private fb: FormBuilder, private router: Router) {
        this.buildForm()
    }


    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    addPrice(oprate, i) {
        if (oprate) {
            this.saleOrder.saleOrderItems[i].discount++
        } else {
            if (this.saleOrder.saleOrderItems[i].discount > 0) {
                this.saleOrder.saleOrderItems[i].discount--
            }
        }
        this.algorithm();

    }

    formErrors = {
        saleOrderItems: [],
        freight: [],

    };

    buildForm(): void {
        this.commonForm = this.fb.group({
            saleOrderItems: [
                {
                    id: null,
                    price: null
                }
            ],
            freight: []
        });
    }

    thePrice: any[] = []

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

    /*  for(let saleOrderItemsKey in this.saleOrder.saleOrderItems){
  
    }*/
    freight(e) {
        this.saleOrder.freight = e;
    }

    addPrices(e, i) {
        this.saleOrder.saleOrderItems[i].discount = e;
    }

    submitted = true;

    submit() {
        this.saleOrder.saleOrderItems.map(e => {
            if (e.discount.toString() == "") {
                this.msgSrv.warning('优惠金额不能为空');
                return;
            }
        })
        if (this.saleOrder.freight.toString() == "") {
            this.msgSrv.warning('运费不能为空');
            return;
        }

        this.commonForm.patchValue({
            saleOrderItems: this.saleOrder.saleOrderItems.map(e => {
                return { id: e.id, discount: e.discount }
            }),
            freight: this.saleOrder.freight,
        })
        const formValue = this.commonForm.value;
        this.commonForm.value.id = this.saleOrder.id;
        this.saleOrderService.updatePriceForSupplier(formValue).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msgSrv.success("修改成功");
                this.router.navigate(['/dashboard/sale-order/view', this.saleOrder.id]);
                // this.router.navigate(['/category/list']);
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
            this.submitted = false;
        }, error => {
            this.msgSrv.error('请求错误', error.message);
            this.submitted = false;
        });
    }

    goBack() {
        this.location.back();
    }

    //原价-优惠+运费+涨价或折扣

    originalPrice = 0;//原价

    discount = 0;//优惠金额

    shipping = 0;//运费

    rebate = 0;//折扣

    //
    algorithm() {
        this.originalPrice = 0;
        this.rebate = 0;

        this.saleOrder.saleOrderItems.map(e => {
            this.originalPrice = this.originalPrice + e.price;
            // this.discount=e.voucherAmount+e.couponAmount;
            this.rebate = this.rebate + e.discount;
        })

    }


}
