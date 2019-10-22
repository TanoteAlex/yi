import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from "../../../models/constants.model";
import {ReturnOrderService} from "../../../services/return-order.service";
import {ReturnOrder} from "../../../models/original/return-order.model";
import {Location} from "@angular/common";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ObjectUtils} from "@shared/utils/ObjectUtils";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'view-return-order',
  templateUrl: './view-return-order.component.html',
  styleUrls: ['./view-return-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewReturnOrderComponent implements OnInit {

  returnOrder: ReturnOrder = new ReturnOrder;

  submitted = false;

  commonForm: FormGroup;

  constructor(private fb: FormBuilder, private route: ActivatedRoute, private location: Location, private returnOrderService: ReturnOrderService, public userService: UserService,
              public msgSrv: NzMessageService, private router: Router, public msg: NzMessageService, private modalService: NzModalService) {
    this.buildForm()
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });

    }
  freightTemplateName = '请选择';
  position=[];

  getById(objId) {
    this.returnOrderService.getById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.returnOrder = response.data;
        if(this.returnOrder.consigneeAddr!=null) {
          this.position[0] = this.returnOrder.consigneeAddr.substring(0, 6);
          this.position[1] = this.returnOrder.consigneeAddr.substring(6, 12);
          this.position[2] = this.returnOrder.consigneeAddr.substring(12, 18);
          this.position[3] = this.returnOrder.consigneeAddr.substring(18, 30);
        }
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
    }, error => {
      this.msgSrv.error('请求错误', error.message);
    });
  }

  formErrors = {
    id: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    returnProcess: {
      user: {
        id: {
          name: 'required',
          msg: '不可为空',

        },
        fullName: {
          name: 'required',
          msg: '不可为空',

        }
      },
      remark: {
        name: '',
        msg: '',
      },
    },
  };

  buildForm(): void {
    this.commonForm = this.fb.group({
      id: [],
      returnProcess: this.fb.group({
        /*  returnOrderId: [null],*/
        user: {
          id: null,
          fullName: "请选择"
        }
        ,
        remark: null,
      }),
    });
  }


  goBack() {
    this.location.back();
  }

  setBuildFormValue(returnOrder: ReturnOrder) {

    this.commonForm.setValue({
      id:
      returnOrder.id
      ,
      returnProcess: {
        user: {
          id: returnOrder.returnProcess.user.id,
          fullName: returnOrder.returnProcess.user.fullName
        }
        ,
        remark:
        returnOrder.returnProcess.remark
        ,
      },
    });

  }

  setMemberLevel(event) {
    this.commonForm.patchValue({
      returnProcess: {
        user: {
          id: event.id,
          fullName: event.fullName
        }
      }
    });
  }

  /*  setCommodityFreightTemplate(event) {
      this.commonForm.patchValue({
        freightTemplate: {
          id: event.id,
          templateNo: event.event,
          templateName: event.templateName,
        }
      });
    }*/


  refusalToConfirm(id, state) {
    const formValue = this.submitCheck();
    if (this.returnOrder) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    if (this.commonForm.value.returnProcess.user.fullName == "请选择") {
      this.msgSrv.error('请选择处理人员！');
      return;
    }

    const searchObj = this.commonForm.value;
    searchObj.id = id;
    searchObj.state = state
    this.submitted = true;
    this.returnOrderService.returnGoods(searchObj).subscribe(response => {
      if (response.result == SUCCESS) {
        this.router.navigate(['/dashboard/return-order/list']);
        this.msgSrv.success('操作成功');
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
      this.submitted = false;
    }, error => {
      this.msgSrv.error('请求错误', error.message);
      this.submitted = false;
    });
  }

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }


}
