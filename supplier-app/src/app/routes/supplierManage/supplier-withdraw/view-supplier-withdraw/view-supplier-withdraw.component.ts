import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {SupplierWithdrawService} from "../../../services/supplier-withdraw.service";
import {SupplierWithdraw} from "../../../models/original/supplier-withdraw.model";
import {Location} from "@angular/common";

@Component({
    selector: 'view-supplier-withdraw',
    templateUrl: './view-supplier-withdraw.component.html',
    styleUrls: ['./view-supplier-withdraw.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ViewSupplierWithdrawComponent implements OnInit {

    supplierWithdraw: SupplierWithdraw = new SupplierWithdraw;

    constructor(private route: ActivatedRoute, private location: Location, private supplierWithdrawService: SupplierWithdrawService,
        public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, ) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId) {
        this.supplierWithdrawService.getById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierWithdraw = response.data;
            } else {
                this.msg.error(response.message ? response.message : '请求失败');
            }
        }, error => {
            this.msg.error(error.message ? error.message : '请求错误');
        });
    }

    goBack() {
        this.location.back();
    }

}
