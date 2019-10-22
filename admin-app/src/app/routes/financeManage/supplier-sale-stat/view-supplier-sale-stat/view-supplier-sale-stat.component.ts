import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {SupplierSaleStatService} from "../../../services/supplier-sale-stat.service";
import {Location} from "@angular/common";
import {SupplierSaleStat} from "../../../models/original/supplier-sale-stat.model";

@Component({
  selector: 'view-supplier-sale-stat',
  templateUrl: './view-supplier-sale-stat.component.html',
  styleUrls: ['./view-supplier-sale-stat.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewSupplierSaleStatComponent implements OnInit {

  supplierSaleStat: SupplierSaleStat=new SupplierSaleStat;

    constructor(private route: ActivatedRoute,private location: Location,private supplierSaleStatService: SupplierSaleStatService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.supplierSaleStatService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.supplierSaleStat = response.data;
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
