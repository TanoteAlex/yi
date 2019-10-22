import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {SUCCESS} from "../../../models/constants.model";
import {CommunityService} from "../../../services/community.service";
import {Community} from "../../../models/original/community.model";
import {Location} from "@angular/common";
import {DomSanitizer} from "@angular/platform-browser";
import {MemberService} from "../../../services/member.service";
import {PageQuery} from "../../../models/page-query.model";
import {SaleOrderService} from "../../../services/sale-order.service";

@Component({
  selector: 'view-community',
  templateUrl: './view-community.component.html',
  styleUrls: ['./view-community.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewCommunityComponent implements OnInit {

  community: Community = new Community;

  loading = false;
  memberDatas: any[] = [];
  saleOrderDatas: any[] = [];
  memberPageQuery: PageQuery = new PageQuery();
  saleOrderQuery: PageQuery = new PageQuery();

  constructor(private route: ActivatedRoute, private location: Location, private communityService: CommunityService, public saleOrderService: SaleOrderService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, public sanli: DomSanitizer, private memberService: MemberService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
      let communityId = params["objId"];
      this.memberPageQuery.addOnlyFilter("community.id", communityId, "eq");
      this.memberPageQuery.addOnlyFilter("state", 0, "eq");
      this.memberPageQuery.addOnlyFilter("memberType", 0, "eq");
      this.saleOrderQuery.addOnlyFilter("community.id", communityId, "eq");
      this.saleOrderQuery.addOnlyFilter("orderState", 4, "eq");
    });
    this.getData();
  }

  getById(objId) {
    this.communityService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.community = response.data;
      } else {
        this.msg.error('请求失败', response.message);
      }
    }, error => {
      this.msg.error('请求错误', error.message);
    });
  }

  goBack() {
    this.location.back();
  }

  sortMember(sort: { key: string, value: string }): void {
    this.memberPageQuery.addSort(sort.key, sort.value)
    this.searchDataMember();
  }

  searchDataMember(reset: boolean = false): void {
    if (reset) {
      this.memberPageQuery.resetPage();
    }
    this.getData();
  }

  sortSaleOrder(sort: { key: string, value: string }): void {
    this.saleOrderQuery.addSort(sort.key, sort.value)
    this.searchDataSaleOrder();
  }

  searchDataSaleOrder(reset: boolean = false): void {
    if (reset) {
      this.saleOrderQuery.resetPage();
    }
    this.getData();
  }

  getData() {
    this.memberService.query(this.memberPageQuery).subscribe(response => {
      this.memberDatas = response['content'];
      this.memberPageQuery.covertResponses(response);
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
    this.saleOrderService.query(this.saleOrderQuery).subscribe(response => {
      this.saleOrderDatas = response['content'];
      this.saleOrderQuery.covertResponses(response);
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }

  chooseSwitch(i) {
    let state = this.memberDatas[i].state, id = this.memberDatas[i].id;
    if (state == 0) {
      //禁用
      this.memberService.prohibition(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("禁用成功");
          this.memberDatas[i].state = 1
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    } else if (state == 1) {
      //启用
      this.memberService.prohibition(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("启用成功");
          this.memberDatas[i].state = 0
        } else {
          this.msg.error('请求失败', response.message);
        }
      }, error => {
        this.msg.error('请求错误', error.message);
      });
    }
  }

  remove(memberId) {
    this.memberService.removeById(memberId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("删除成功");
        this.getData();
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

}
