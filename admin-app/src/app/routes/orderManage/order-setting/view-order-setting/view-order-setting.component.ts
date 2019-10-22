import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {OrderSettingService} from "../../../services/order-setting.service";
import {OrderSetting} from "../../../models/original/order-setting.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-order-setting',
  templateUrl: './view-order-setting.component.html',
  styleUrls: ['./view-order-setting.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewOrderSettingComponent implements OnInit {

orderSetting: OrderSetting=new OrderSetting;

    constructor(private route: ActivatedRoute,private location: Location,private orderSettingService: OrderSettingService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.orderSettingService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.orderSetting = response.data;
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
