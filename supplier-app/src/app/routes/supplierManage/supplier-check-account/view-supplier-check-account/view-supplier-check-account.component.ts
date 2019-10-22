import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {SupplierCheckAccountService} from "../../../services/supplier-check-account.service";
import {SupplierCheckAccount} from "../../../models/original/supplier-check-account.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-supplier-check-account',
  templateUrl: './view-supplier-check-account.component.html',
  styleUrls: ['./view-supplier-check-account.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSupplierCheckAccountComponent implements OnInit {

supplierCheckAccount: SupplierCheckAccount=new SupplierCheckAccount;

    constructor(private route: ActivatedRoute,private location: Location,private supplierCheckAccountService: SupplierCheckAccountService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierCheckAccountService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierCheckAccount = response.data;
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
