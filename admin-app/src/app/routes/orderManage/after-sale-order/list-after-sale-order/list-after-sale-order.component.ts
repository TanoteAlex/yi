import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AfterSaleOrderService} from '../../../services/after-sale-order.service';
import {SUCCESS} from "../../../models/constants.model";
import {PageQuery} from "../../../models/page-query.model";
import {AfterSaleOrderListVo} from "../../../models/domain/listVo/after-sale-order-list-vo.model";

@Component({
  selector: 'list-after-sale-order',
  templateUrl: './list-after-sale-order.component.html',
  styleUrls: ['./list-after-sale-order.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListAfterSaleOrderComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  afterSaleOrders: Array<AfterSaleOrderListVo>;

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private afterSaleOrderService: AfterSaleOrderService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      memberName: [null],
      afterSaleType: [null],
      applyState: [null],
    });
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  searchData(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
    this.configPageQuery(this.pageQuery);
    this.getData();
  }

  getData() {
    this.loading = true;
    this.afterSaleOrderService.query(this.pageQuery).subscribe(response => {
      this.afterSaleOrders = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  clearSearch() {
    this.searchForm.reset({
      memberName: null,
      afterSaleType: null,
      applyState: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(afterSaleOrderId) {
    this.afterSaleOrderService.removeById(afterSaleOrderId).subscribe(response => {
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

  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;
    if (searchObj.memberName != null) {
      pageQuery.addOnlyFilter('member.nickname', searchObj.memberName, 'contains');
    }
    if (searchObj.afterSaleType != null) {
      pageQuery.addOnlyFilter('afterSaleType', searchObj.afterSaleType, 'eq');
    }
    if (searchObj.applyState != null) {
      pageQuery.addOnlyFilter('applyState', searchObj.applyState, 'eq');
    }
    return pageQuery;
  }

  types = [
    {id: 1, name: "退款"},
    {id: 2, name: "退货退款"},
    // {id: 3, name: "换货"},
  ]

  states = [
    {id: 1, name: "审核中"},
    {id: 2, name: "处理中"},
    {id: 3, name: "已完成"},
  ]

}
