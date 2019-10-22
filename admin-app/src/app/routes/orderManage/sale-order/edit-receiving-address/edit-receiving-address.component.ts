import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {SaleOrderService} from '../../../services/sale-order.service';
import {SaleOrder} from "../../../models/original/sale-order.model";
import {NzMessageService} from "ng-zorro-antd";
import {SUCCESS} from "../../../models/constants.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-edit-price',
  templateUrl: './edit-receiving-address.component.html',
  styleUrls: ['./edit-receiving-address.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditReceivingAddressComponent implements OnInit {

  commonForm: FormGroup;

  @Input() saleOrder: SaleOrder = new SaleOrder;

  position = [];

  constructor(private route: ActivatedRoute, private location: Location,
              private saleOrderService: SaleOrderService, public msgSrv:
                NzMessageService, private fb: FormBuilder, private router: Router) {
    this.buildForm();
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
  }

  getById(objId) {
    this.saleOrderService.getById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.saleOrder = response.data;
        this.setBuildFormValue(this.saleOrder);
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
    }, error => {
      this.msgSrv.error('请求错误', error.message);
    });
  }

  ngOnChanges(value) {
    if (value.saleOrder !== undefined && value.saleOrder.currentValue !== undefined) {
      this.setBuildFormValue(this.saleOrder);
    }
  }


  formErrors = {
    consignee: [],
    consigneePhone: [],
    detailed: [],
    consigneeAddr: []
  };


  buildForm(): void {
    this.commonForm = this.fb.group({
      consignee: [],
      consigneePhone: [],
      detailed: [],
      consigneeAddr: []
    });
  }
  setBuildFormValue(saleOrder: SaleOrder) {
    this.commonForm.setValue({
      consignee:
      saleOrder.consignee
      ,
      consigneePhone:
      saleOrder.consigneePhone
      ,
      detailed: null,
      consigneeAddr: null,
    });
    let position=[]
    if (saleOrder.consigneeAddr != null) {
      position.push(saleOrder.consigneeAddr .substring(0, 6))
      position.push(saleOrder.consigneeAddr .substring(6, 12))
      position.push(saleOrder.consigneeAddr .substring(12, 18))
      this.position=position
      this.commonForm.patchValue({
        detailed: saleOrder.consigneeAddr .substring(18, 30)
      })
      this.address = this.position[0] + this.position[1] + this.position[2]
    }
  }

  address = ""

  setProvince(event) {
    this.address = event[0] + event[1] + event[2]
  }

  goBack() {
    this.location.back();
  }

  submitted = true;

  submit() {

    if (this.address == "") {
      this.msgSrv.warning('请输入所在地');
      return
    }
    if (this.commonForm.value.detailed == "") {
      this.msgSrv.warning('请输入详细地址');
      return
    }

    this.commonForm.value.consigneeAddr = this.address + this.commonForm.value.detailed

    const searchObj = this.commonForm.value;
    searchObj.id = this.saleOrder.id;
    this.saleOrderService.updateProvince(searchObj).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msgSrv.success("操作成功");
        this.router.navigate(['/dashboard/sale-order/view', this.saleOrder.id]);
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
      this.submitted = false;
    }, error => {
      this.msgSrv.error('请求错误', error.message);
      this.submitted = false;
    });

  }


}
