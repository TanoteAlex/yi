

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { CategoryService } from "../../../services/category.service";
import { Category } from "../../../models/original/category.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-category',
  templateUrl: './view-category.component.html',
  styleUrls: ['./view-category.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCategoryComponent implements OnInit {

category: Category=new Category;

    constructor(private route: ActivatedRoute,private location: Location,private categoryService: CategoryService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.categoryService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.category = response.data;
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    goBack(){
        this.location.back();
    }

}
