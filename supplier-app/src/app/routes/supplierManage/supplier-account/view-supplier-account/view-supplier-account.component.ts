

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { SupplierAccountService } from "../../../services/supplier-account.service";
import { SupplierAccount } from "../../../models/original/supplier-account.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-supplier-account',
  templateUrl: './view-supplier-account.component.html',
  styleUrls: ['./view-supplier-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSupplierAccountComponent implements OnInit {

supplierAccount: SupplierAccount=new SupplierAccount;

    constructor(private route: ActivatedRoute,private location: Location,private supplierAccountService: SupplierAccountService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierAccountService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierAccount = response.data;
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
