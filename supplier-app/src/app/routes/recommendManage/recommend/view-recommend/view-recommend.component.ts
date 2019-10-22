import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {RecommendService} from "../../../services/recommend.service";
import {Recommend} from "../../../models/original/recommend.model";
import {Location} from "@angular/common";

@Component({
  selector: 'view-recommend',
  templateUrl: './view-recommend.component.html',
  styleUrls: ['./view-recommend.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewRecommendComponent implements OnInit {

recommend: Recommend=new Recommend;

    constructor(private route: ActivatedRoute,private location: Location,private recommendService: RecommendService,
        public msgSrv: NzMessageService,public http: _HttpClient,public msg: NzMessageService,) { }

    ngOnInit() {
        this.route.params.subscribe((params: ParamMap) => {
            this.getById(params["objId"]);
        });
    }

    getById(objId){
        this.recommendService.getVoById(objId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.recommend = response.data;
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
