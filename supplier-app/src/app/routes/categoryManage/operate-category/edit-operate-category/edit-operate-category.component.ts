import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {SUCCESS} from '../../../models/constants.model';
import {OperateCategoryService} from '../../../services/operate-category.service';
import {OperateCategory} from '../../../models/original/operate-category.model';

@Component({
  selector: 'edit-operate-category',
  templateUrl: './edit-operate-category.component.html',
  styleUrls: ['./edit-operate-category.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EditOperateCategoryComponent implements OnInit {

  submitting = false;

  operateCategory: OperateCategory;

  constructor(private route: ActivatedRoute, private router: Router, private operateCategoryService: OperateCategoryService, public msgSrv:
    NzMessageService, private modalService: NzModalService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
  }

  getById(objId) {
    this.operateCategoryService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.operateCategory = response.data;
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
      formValue.obj.id = this.operateCategory.id;
      this.operateCategoryService.update(formValue.obj).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msgSrv.success("操作成功");
          this.router.navigate(['/dashboard/operate-category/list']);
        } else {
          this.msgSrv.error('请求失败' + response.message);
        }
        this.msgSrv.remove(messageId);
        this.submitting = false;
      }, error => {
        this.msgSrv.error('请求错误' + error.message);
        this.msgSrv.remove(messageId);
        this.submitting = false;
      });
    }
  }
}
