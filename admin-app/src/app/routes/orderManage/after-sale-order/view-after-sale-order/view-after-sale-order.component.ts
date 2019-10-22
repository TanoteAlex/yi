import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {AfterSaleOrderService} from "../../../services/after-sale-order.service";
import {AfterSaleOrderVo} from '../../../models/domain/vo/after-sale-order-vo.model';
import {Location} from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {ObjectUtils} from "@shared/utils/ObjectUtils";
import {AfterSaleProcessService} from "../../../services/after-sale-process.service";
import {AfterSaleProcessListVo} from "../../../models/domain/listVo/after-sale-process-list-vo.model";
import {PageQuery} from "../../../models/page-query.model";

@Component({
  selector: 'view-after-sale-order',
  templateUrl: './view-after-sale-order.component.html',
  styleUrls: ['./view-after-sale-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewAfterSaleOrderComponent implements OnInit {

  afterSaleOrder: AfterSaleOrderVo = new AfterSaleOrderVo;
  loading = false;
  pageQuery: PageQuery = new PageQuery();
  userPageQuery: PageQuery = new PageQuery();
  afterSaleProcesss: Array<AfterSaleProcessListVo>;

  commonForm: FormGroup;

  constructor(private router: Router, private route: ActivatedRoute, private fb: FormBuilder, private location: Location, private afterSaleOrderService: AfterSaleOrderService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, public userService: UserService, public afterSaleProcessService: AfterSaleProcessService) {
    this.buildForm()
  }

  ngOnInit() {
    this.userPageQuery.addOnlyOneFilter("state", 0, "eq");
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      afterSaleProcess: this.fb.group({
        afterSaleOrder: null,
        user: {
          id: null,
          fullName: "请选择",
        },
        processPerson: null,
        processType: null,
        processInfo: null,
      }),
    });
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  searchData(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
  }

  getById(objId) {
    this.afterSaleOrderService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.afterSaleOrder = response.data;
        this.pageQuery.addOnlyOneFilter("afterSaleOrder.id", this.afterSaleOrder.id, "eq")
        this.afterSaleProcessService.query(this.pageQuery).subscribe(response => {
          this.afterSaleProcesss = response['content'];
          this.pageQuery.covertResponses(response);
        }, error => {
          this.msg.error('请求错误' + error.message);
        });
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


  setProcessUser(event) {
    if (event) {
      this.commonForm.patchValue({
        afterSaleProcess: {
          afterSaleOrder: this.afterSaleOrder,
          user: {
            id: event.id,
            fullName: event.fullName,
          },
          processPerson: event.fullName,
        },
      });
    }
  }

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }

  returnRefund(id, type) {
    this.commonForm.patchValue({
      afterSaleProcess: {
        processType: type
      }
    })
    const formValue = this.submitCheck();
    if (this.commonForm.value.afterSaleProcess.user.fullName == "请选择" || this.commonForm.value.afterSaleProcess.user == null) {
      this.msgSrv.warning("请选择处理人")
      return;
    }
    this.afterSaleProcessService.confirmReturn(formValue.afterSaleProcess).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msgSrv.success('操作成功');
        this.router.navigate(['/dashboard/after-sale-order/list']);
      } else {
        this.msgSrv.error(response.message);
      }
    }, error => {
      this.msgSrv.error(error.message);
    });
  }

}
