import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {BannerService} from '../../../services/banner.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {Advertisement} from "../../../models/original/advertisement.model";
import {AdvertisementService} from "../../../services/advertisement.service";

@Component({
  selector: 'list-advertisement',
  templateUrl: './list-advertisement.component.html',
  styleUrls: ['./list-advertisement.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListAdvertisementComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private advertisementService: AdvertisementService , public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
  }

  buildForm() {
    this.searchForm = this.fb.group({
      title: [null],
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
    this.advertisementService.query(this.pageQuery).subscribe(response => {
      this.datas = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }
  type(e){
    alert(e )
  }

  clearSearch() {
    this.searchForm.reset({
      title: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }

  remove(bannerId) {
    this.advertisementService.removeById(bannerId).subscribe(response => {
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

    if (searchObj.title != null) {
      pageQuery.addOnlyFilter('title', searchObj.title, 'contains');
    }
    return pageQuery;
  }

  chooseSwitch(i) {
    let state =  this.datas[i].state, id =  this.datas[i].id;
    if (state == 0) {
      //禁用
      this.advertisementService.updateStateDisable(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("禁用成功");
          this.datas[i].state=1
        } else {
          this.msg.error('请求失败' + response.message);
        }
      }, error => {
        this.msg.error('请求错误' + error.message);
      });
    } else if (state == 1) {
      //启用
      this.advertisementService.updateStateEnable(id).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("启用成功");
          this.datas[i].state=0
        } else {
          this.msg.error('请求失败' + response.message);
        }
      }, error => {
        this.msg.error('请求错误' + error.message);
      });
    }
  }


}
