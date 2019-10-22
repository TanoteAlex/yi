import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {OperateCategory} from '../../../models/original/operate-category.model';
import {OperateCategoryService} from '../../../services/operate-category.service';
import {SUCCESS} from '../../../models/constants.model';

@Component({
  selector: 'add-operate-category',
  templateUrl: './add-operate-category.component.html',
  styleUrls: ['./add-operate-category.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AddOperateCategoryComponent implements OnInit {

  submitting = false;

  operateCategory: OperateCategory;

  constructor(private router: Router, private operateCategoryService: OperateCategoryService, public msgSrv: NzMessageService,
              private modalService: NzModalService) {
  }

  ngOnInit() {
  }

  onTransmitFormValueSubscription(event) {
    if (event) {
      this.confirmSub(event)
    }
  }

  confirmSub(formValue) {
    if (formValue) {
      this.submitting = true;

      if (formValue.obj.parent.id == 0) {
        formValue.obj.parent = null
      }

      const messageId = this.msgSrv.loading("正在提交...").messageId;
      this.operateCategoryService.add(formValue.obj).subscribe(response => {
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
