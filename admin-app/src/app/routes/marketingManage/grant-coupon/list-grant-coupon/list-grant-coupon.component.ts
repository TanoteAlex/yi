import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CouponService} from '../../../services/coupon.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {CouponReceive} from "../../../models/original/coupon-receive.model";
import {MemberService} from "../../../services/member.service";
import {ObjectUtils} from "@shared/utils/ObjectUtils";
import {Location} from "@angular/common";
import {Coupon} from "../../../models/original/coupon.model";
import {CouponReceiveService} from "../../../services/coupon-receive.service";

@Component({
  selector: 'list-grant-coupon',
  templateUrl: './list-grant-coupon.component.html',
  styleUrls: ['./list-grant-coupon.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListGrantCouponComponent implements OnInit {


  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  couponReceive:CouponReceive =new  CouponReceive;

  constructor(public route: ActivatedRoute, public router: Router,private location: Location,private couponService: CouponService,public memberService: MemberService,public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService, private couponReceiveService:CouponReceiveService) {
    this.buildForm();
  }

  linkTypes=[];



  ngOnInit() {
    this.getCoupon();
  }

  pageQuery1: PageQuery = new PageQuery();

  getCoupon(){
    this.loading = true;
    this.pageQuery1.addFilter("couponType",1,"eq");
    this.pageQuery1.addFilter("deleted",0,"eq");
    this.couponService.query(this.pageQuery).subscribe(response => {
      response['content'].forEach(e=>{
        this.linkTypes.push(e)
      })
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });
  }

  pageQuery: PageQuery = new PageQuery();
  memberType(){
    this.pageQuery.pushParams("couponId",this.searchForm.value.couponId);
  }


  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;
    if (searchObj.couponName != null) {
      pageQuery.addOnlyFilter('couponName', searchObj.couponName, 'contains');
    }
    return pageQuery;
  }


  formErrors = {
    couponId: [
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    grantType:[

    ],
    members:[]
  };


  setCoupon(event){
    this.searchForm.patchValue({
      members:event.map(e =>{
        return {id: e.id,username: e.username}
      })
    });
  }

  buildForm(): void {
    this.searchForm = this.fb.group({
      couponId: [0],
      grantType:[
        2
      ],
      members:[[]]

    });

  }

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.searchForm);
    if (commonFormValid) {
      return this.searchForm.value;
    }
    return null;
  }
  submitting = false;
  onSubmit() {
    const formValue = this.submitCheck();
    if(this.searchForm.value.couponId==0){
      this.msg.warning('请选择发放的优惠券！');
    }
    if(this.searchForm.value.grantType==2 && this.searchForm.value.members==""){
      this.msg.warning('会员不能为空！');
    }
    if (!formValue) {
      this.msg.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    this.submitting = true;
    const messageId = this.msg.loading("正在提交...").messageId;
    this.couponReceiveService.grantCoupon(formValue).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("操作成功");
        this.router.navigate(['/dashboard/grant-coupon/list']);
      } else {
        this.msg.error('请求失败'+response.message);
      }
      this.msg.remove(messageId);
      this.submitting = false;
    }, error => {
      this.msg.error('请求错误'+error.message);
      this.msg.remove(messageId);
      this.submitting = false;
    });
  }
  goBack() {
    this.location.back();
  }




}
