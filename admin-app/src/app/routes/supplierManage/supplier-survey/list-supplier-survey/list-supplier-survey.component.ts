import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {SupplierService} from '../../../services/supplier.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {Homepage} from "../../../models/original/home-data.model";
import {HomepageService} from "../../../services/homepage-service";
import {SupplierData} from "../../../models/original/supplier-data.model";
import {SupplierpageService} from "../../../services/supplierpage-service";

@Component({
  selector: 'list-supplier-survey',
  templateUrl: './list-supplier-survey.component.html',
  styleUrls: ['./list-supplier-survey.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListSupplierSurveyComponent implements OnInit {

  supplierData: SupplierData = new SupplierData();

  constructor(public msg: NzMessageService, public supplierService: SupplierService) {
  }
  /*querySupplierData*/
  ngOnInit() {
    this.supplierService.querySupplierData().subscribe(response => {
      this.supplierData = response.data;
    }, error => {
      this.msg.error('请求错误' + error.message);
    });
  }

}
