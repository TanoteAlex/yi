

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS} from "../../../models/constants.model";
import { StockService } from "../../../services/stock.service";
import { Stock } from "../../../models/original/stock.model";
import { Location } from "@angular/common";

@Component({
  selector: 'view-stock',
  templateUrl: './view-stock.component.html',
  styleUrls: ['./view-stock.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewStockComponent implements OnInit {

stock: Stock=new Stock;

    constructor(private route: ActivatedRoute,private location: Location,private stockService: StockService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.stockService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.stock = response.data;
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
