
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { Category } from '../../../models/original/category.model';
import { CategoryService } from '../../../services/category.service';
import { SUCCESS } from '../../../models/constants.model';

@Component({
    selector: 'add-category',
    templateUrl: './add-category.component.html',
    styleUrls: ['./add-category.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AddCategoryComponent implements OnInit {

    submitting = false;

    category: Category;

    constructor(private router: Router, private categoryService: CategoryService, public msgSrv: NzMessageService,
        private modalService: NzModalService) {
    }

    ngOnInit() {
    }

    onTransmitFormValueSubscription(event) {
        if (event) {
            this.submitForm(event)
        }
    }


    submitForm(formValue) {
        if (formValue) {
            this.submitting = true;
            if (formValue.obj.parent && formValue.obj.parent.id == 0) {
                formValue.obj.parent = null
            }
            const messageId = this.msgSrv.loading("正在提交...").messageId;
            this.categoryService.add(formValue.obj).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msgSrv.success("操作成功");
                    this.router.navigate(['/dashboard/category/list']);
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
