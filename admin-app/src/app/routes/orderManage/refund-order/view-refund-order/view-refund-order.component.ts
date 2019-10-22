import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {RefundOrderService} from "../../../services/refund-order.service";
import {RefundOrder} from "../../../models/original/refund-order.model";
import {Location} from "@angular/common";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ObjectUtils} from "@shared/utils/ObjectUtils";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'view-refund-order',
  templateUrl: './view-refund-order.component.html',
  styleUrls: ['./view-refund-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRefundOrderComponent implements OnInit {

  refundOrder: RefundOrder = new RefundOrder;

  submitted: boolean = false;

  commonForm: FormGroup;

  constructor(private router: Router, private fb: FormBuilder, private route: ActivatedRoute, private location: Location, private refundOrderService: RefundOrderService,public userService: UserService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, private modalService: NzModalService) {
    this.buildForm()
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
  }

  setMemberLevel(event) {

    this.commonForm.patchValue({

      refundProcess: {
        user: {
          id: event.id,
          fullName: event.fullName
        }
      }
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
    refundNo: []
    ,
    refundProcess: {
      remark: {
        name: '',
        msg: '',
      },

    },
    user: {
      id: {
        name: '',
        msg: '',
      },
      fullName: {
        name: '',
        msg: '',
      },
    },
  };

  buildForm(): void {
    this.commonForm = this.fb.group({
      id: [],
      refundProcess: this.fb.group({

        user: {
          id: null,
          fullName: "请选择"

          ,
        },
        remark: [null],
      }),
      refundNo: []

    });
  }

  setBuildFormValue(refundOrder: RefundOrder) {
    this.commonForm.setValue({
      id:
      refundOrder.id
      ,

      refundProcess: {

        remark:
        refundOrder.refundProcess.remark
        ,
        user: {
          id: refundOrder.refundProcess.user.id,
          fullName: refundOrder.refundProcess.user.fullName
        }

      },
    });
  }

  getById(objId) {
    this.refundOrderService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.refundOrder = response.data;
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

  goBack() {
    this.location.back();
  }


  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }


  goRefundOrder(id, state) {
    const formValue = this.submitCheck();
    if (this.refundOrder) {
    }
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    if (this.commonForm.value.refundProcess.user.fullName == "请选择") {
      this.msgSrv.error('请选择处理人员！');
      return;
    }
    /**
     * 将 refundOrder的id 赋给 process 的退款单id
     */
    /*this.commonForm.value.returnProcesse.returnOrderId = id;*/
    const searchObj = this.commonForm.value;

    searchObj.id = id;
    /* searchObj.refundNo=this.returnOrder.refundNo*/
    searchObj.state = state

    this.submitted = true;
    this.refundOrderService.toExamine(searchObj).subscribe(response => {
      if (response.result == SUCCESS) {
        this.router.navigate(['/dashboard/refund-order/list']);
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

}
