
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { SUCCESS } from '../../../models/constants.model';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/original/category.model';

@Component({
    selector: 'edit-category',
    templateUrl: './edit-category.component.html',
    styleUrls: ['./edit-category.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EditCategoryComponent implements OnInit {

    submitting = false;

    category: Category;

    constructor(private route: ActivatedRoute, private router: Router, private categoryService: CategoryService, public msgSrv:
        NzMessageService, private modalService: NzModalService) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.categoryService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.category = response.data;
            } else {
                this.msgSrv.error('请求失败', response.message);
            }
        }, error => {
            this.msgSrv.error('请求错误', error.message);
        });
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
            formValue.obj.id = this.category.id;
            this.categoryService.update(formValue.obj).subscribe(response => {
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
