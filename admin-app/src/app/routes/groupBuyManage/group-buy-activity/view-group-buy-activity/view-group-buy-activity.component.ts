import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {_HttpClient} from '@delon/theme';
import {GroupBuyActivityService} from "../../../services/group-buy-activity.service";
import {GroupBuyActivityVo} from '../../../models/domain/vo/group-buy-activity-vo.model';
import {Location} from "@angular/common";
import {SUCCESS} from "../../../models/constants.model";
import {MemberLevelService} from "../../../services/member-level.service";
import {PageQuery} from "../../../models/page-query.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {ObjectUtils} from "@shared/utils/ObjectUtils";

@Component({
  selector: 'view-group-buy-activity',
  templateUrl: './view-group-buy-activity.component.html',
  styleUrls: ['./view-group-buy-activity.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ViewGroupBuyActivityComponent implements OnInit {

  groupBuyActivity: GroupBuyActivityVo = new GroupBuyActivityVo;

  memberLevelPageQuery: PageQuery = new PageQuery();
  userPageQuery: PageQuery = new PageQuery();
  memberLevelDatas: any[] = [];
  memberLevels: any[] = [];

  commonForm: FormGroup;

  formErrors = {

    auditUser: [
      {
        name: 'required',
        msg: '不可为空',
      },
    ],

  };

  constructor(private route: ActivatedRoute, private router: Router, private location: Location, private groupBuyActivityService: GroupBuyActivityService, private fb: FormBuilder,
              public msgSrv: NzMessageService, public http: _HttpClient, public msg: NzMessageService, public memberLevelService: MemberLevelService, public userService: UserService) {
    this.buildForm()
  }

  ngOnInit() {
    this.route.params.subscribe((params: ParamMap) => {
      this.getById(params["objId"]);
    });
    this.userPageQuery.addOnlyOneFilter("state", 0, "eq");
  }

  buildForm(): void {
    this.commonForm = this.fb.group({
      auditUser: [null, Validators.compose([Validators.required])]
    });
  }

  getById(objId) {
    this.groupBuyActivityService.getVoById(objId).subscribe(response => {
      if (response.result == SUCCESS) {
        this.groupBuyActivity = response.data;
        this.memberLevelService.query(this.memberLevelPageQuery).subscribe(response => {
          this.memberLevelDatas = response['content'];
          if(this.groupBuyActivity.groupBuyActivityMember.memberLevel){
            this.memberLevelDatas.forEach(e => {
              let memberLevelArray = this.groupBuyActivity.groupBuyActivityMember.memberLevel.split(",");
              memberLevelArray.forEach(e1 => {
                if (e.id == e1) {
                  this.memberLevels.push({id: e.id, name: e.name});
                }
              })
            })
          }
        }, error => {
          this.msgSrv.error('会员等级获取失败' + error.message);
        });
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

  setUser(event) {
    if (event.fullName && event.length != 0) {
      this.commonForm.patchValue({
        auditUser: event
      });
    }
  }

  total = 0;       //总记录数
  pageSize: number = 10;  //页的大小，初始值为5
  page: number = 1;       //页号，初始为1
  totalPages = 0; //总页数

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

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
    if (commonFormValid) {
      return this.commonForm.value;
    }
    return null;
  }

  auditing(groupBuyActivityId) {
    const formValue = this.submitCheck();
    if (!formValue) {
      this.msgSrv.warning('校验尚未通过无法提交，请确认输入项！');
      return;
    }
    this.groupBuyActivityService.auditing(groupBuyActivityId, this.commonForm.value.auditUser.id).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msgSrv.success('审核通过')
        this.router.navigate(['/dashboard/group-buy-activity/list']);
      } else {
        this.msgSrv.error('请求失败', response.message);
      }
    }, error => {
      this.msgSrv.error('请求错误', error.message);
    });
  }

}
