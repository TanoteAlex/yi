import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SUCCESS } from '../../../models/constants.model';
import { MemberService } from '../../../services/member.service';
import { Member } from '../../../models/original/member.model';
import { Location } from '@angular/common';
import { PageQuery } from '../../../models/page-query.model';
import { MemberCommissionService } from '../../../services/memberCommission.service';

@Component({
  selector: 'view-member',
  templateUrl: './view-member.component.html',
  styleUrls: ['./view-member.component.less'],
  encapsulation: ViewEncapsulation.None,
})
export class ViewMemberComponent implements OnInit {

  member: Member = new Member;

  dataSet;
  switch: boolean = true;
  loading: boolean = false;
  isV: boolean = true;
  consumeAmount = 0;
  orderQuantity = 0;
  pageQuery: PageQuery = new PageQuery();
  memberCommissionPageQuery: PageQuery = new PageQuery();
  members: any[] = [];
  memberCommissions: any[] = [];

  constructor(private route: ActivatedRoute, private location: Location, private memberService: MemberService, private memberCommissionService: MemberCommissionService,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params['objId'], this.isV);
    });
  }

  total = 0;       //总记录数
  pageSize: number = 10;  //页的大小，初始值为10
  page: number = 1;       //页号，初始为1
  totalPages = 1; //总页数

  //上一页
  previousPage() {
    this.page--;
    this.changePage(this.page);
  }

  // 下一页
  nextPage() {
    this.page++;
    this.changePage(this.page);
  }


  // 首页
  topPage() {
    this.page = 1;
  }

  // 尾页
  endPage() {
    this.page = this.totalPages;
  }

  // 改变页号，更新表格所在页数的数据
  changePage(page: number): void {
    if (page > 0 && page < this.totalPages) { //正常之间的
      this.page = page;
      //以防改变页的大小或总记录数/页大小时不为整，出现小数点形式的
      this.totalPages = Math.ceil(this.total / this.pageSize);
    } else if (page <= 0) { //页号小于首页
      this.page = 1;
    } else { //页号大于尾页号
      this.page = this.totalPages;
    }
  }

  addressTotal = 0;       //总记录数
  addressPage: number = 1;       //页号，初始为1
  addressTotalPages = 1; //总页数

  //上一页
  addressPreviousPage() {
    this.addressPage--;
    this.changePage(this.addressPage);
  }

  // 下一页
  addressNextPage() {
    this.addressPage++;
    this.changePage(this.addressPage);
  }


  // 首页
  addressTopPage() {
    this.addressPage = 1;
  }

  // 尾页
  addressEndPage() {
    this.addressPage = this.addressTotalPages;
  }

  // 改变页号，更新表格所在页数的数据
  addressChangePage(addressPage: number): void {
    if (addressPage > 0 && addressPage < this.addressTotalPages) { //正常之间的
      this.addressPage = addressPage;
      //以防改变页的大小或总记录数/页大小时不为整，出现小数点形式的
      this.addressTotalPages = Math.ceil(this.addressTotal / this.pageSize);
    } else if (addressPage <= 0) { //页号小于首页
      this.addressPage = 1;
    } else { //页号大于尾页号
      this.addressPage = this.addressTotalPages;
    }
  }

  getById(objId, isV) {
    this.memberService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.consumeAmount = 0;
        this.orderQuantity = 0;
        this.member = response.data;
        this.addressTotal = this.member.shippingAddresss.length;
        this.addressTotalPages = Math.ceil(this.addressTotal / this.pageSize);
        this.member.accountRecords.forEach(e => {
          if (e.operateType == 2) {
            this.consumeAmount = this.consumeAmount + e.tradeAmount;
            this.orderQuantity++;
          }
        });
        this.dataSet = this.member.shippingAddresss;
        this.switch = isV;
        this.page = 1;
        if (isV) {
          let accountRecord = [];
          this.member.accountRecords.forEach(e => {
            if (e.operateType == 2) {
              accountRecord.push(e);
            }
          });
          this.member.accountRecords = accountRecord;
          this.total = this.member.accountRecords.length;
        } else {
          this.total = this.member.accountRecords.length;
        }
        this.totalPages = Math.ceil(this.total / this.pageSize);
        this.getMember();
        this.getMemberCommissionService();
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

  searchMemberLevels(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
    this.getMember();
  }

  getMember() {
    this.pageQuery.addOnlyFilter('parent.id', this.member.id, 'eq');
    this.memberService.query(this.pageQuery).subscribe(response => {
      this.members = response['content'];
      this.pageQuery.covertResponses(response);
    });
  }

  searchCommissions(reset: boolean = false): void {
    if (reset) {
      this.memberCommissionPageQuery.resetPage();
    }
    this.getMemberCommissionService();
  }

  getMemberCommissionService() {
    this.memberCommissionPageQuery.addOnlyOneFilter('member.id', this.member.id, 'eq');
    this.memberCommissionService.query(this.memberCommissionPageQuery).subscribe(response => {
      this.memberCommissions = response['content'];
      this.memberCommissionPageQuery.covertResponses(response);
    });
  }

  menus = [
    { name: '一级', value: 1 },
    { name: '二级', value: 2 },
  ];

  onItemClick1(i) {
    this.pageQuery.clearFilter();
    this.pageQuery.resetPage();
    if (i == 1) {
      this.pageQuery.addOnlyFilter('parent.id', this.member.id, 'eq');
      this.pageQuery.addOnlyFilter('deleted', 0, 'eq');
      this.memberService.query(this.pageQuery).subscribe(response => {
        this.members = response['content'];
        this.pageQuery.covertResponses(response);
      });
    } else {
      this.pageQuery.addOnlyFilter('parent.parent.id', this.member.id, 'eq');
      this.pageQuery.addOnlyFilter('deleted', 0, 'eq');
      this.memberService.query(this.pageQuery).subscribe(response => {
        this.members = response['content'];
        this.pageQuery.covertResponses(response);
      });
    }
  }

}
