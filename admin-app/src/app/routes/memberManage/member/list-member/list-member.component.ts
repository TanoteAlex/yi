import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {MemberService} from '../../../services/member.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {MemberLevelService} from "../../../services/member-level.service";
import { ModalSelecetComponent } from '../../../components/modal-selecet/modal-selecet.component';

@Component({
    selector: 'list-member',
    templateUrl: './list-member.component.html',
    styleUrls: ['./list-member.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListMemberComponent implements OnInit {

    pageQuery: PageQuery = new PageQuery();

    levelPageQuery: PageQuery = new PageQuery();

    searchForm: FormGroup;

    loading = false;

    datas: any[] = [];

    expandForm = false;

    constructor(public route: ActivatedRoute, public router: Router, public memberService: MemberService, public msg: NzMessageService,
        private fb: FormBuilder, private modalService: NzModalService, private memberLevelService: MemberLevelService) {
        this.buildForm();
    }

    menus = [
        { name: "全部会员", value: "" },
        { name: "在线会员", value: 0 },
        { name: "冻结会员", value: 1 },
    ];

    memberTypes = [
        { name: "普通会员", id: 0 },
        { name: "管理员", id: 1 },
    ];
    /*/**/

    menuss = [""]
    onItemClick(i) {
//        this.pageQuery.clearFilter();
        this.pageQuery.resetPage();
        this.configPageQuery(this.pageQuery);
        this.pageQuery.addOnlyFilter('state', this.menus[i].value, 'eq');
        this.pageQuery.addLockFilterName('state');
        this.getData();
    }

    ngOnInit() {
        this.searchData();
        this.memberLevel()
    }

    memberLevel() {
        this.levelPageQuery.addFilter("deleted", 0, "eq");
        this.memberLevelService.query(this.levelPageQuery).subscribe(response => {
            response['content'].forEach(e => {
                this.menuss.push(e)
            })
            this.levelPageQuery.covertResponses(response);
            this.loading = false;
        }, error => {
            this.loading = false;
            this.msg.error('请求错误' + error.message);
        });
    }

    buildForm() {
        this.searchForm = this.fb.group({
            username: [null],
            memberLevel: [null],
            memberType: [null],
            consumeNum: [null],
            loginNum: [null],
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
        this.memberService.query(this.pageQuery).subscribe(response => {
            if (this.pageQuery.page != 1 && response['content'] == "") {
                this.pageQuery.page = this.pageQuery.page - 1;
                this.getData();
            } else {
                this.datas = response['content'];
                this.pageQuery.covertResponses(response);
                this.loading = false;
            }
        }, error => {
            this.loading = false;
            this.msg.error('请求错误' + error.message);
        });
    }

    clearSearch() {
        this.searchForm.reset({
            username: null,
            memberLevel: null,
            memberType: null,
            consumeNum: null,
            loginNum: null,
        });
        this.pageQuery.clearFilter();
        this.searchData();
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

  resetPassword(memberId){
      this.memberService.resetPassword(memberId).subscribe(response => {
        if (response.result == SUCCESS) {
          this.msg.success("密码重置成功，密码为该会员的手机号后6位");
        } else {
          this.msg.error('请求失败' + response.message);
        }
      }, error => {
        this.msg.error('请求失败' + error.message);
      });
    }

    chooseSwitch(i) {
        let state = this.datas[i].state, id = this.datas[i].id;
        if (state == 0) {
            //禁用
            this.memberService.prohibition(id).subscribe(response => {
                if (response.result == SUCCESS) {
                    this.msg.success("禁用成功");
                    this.datas[i].state = 1
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
                    this.datas[i].state = 0
                } else {
                    this.msg.error('请求失败', response.message);
                }
            }, error => {
                this.msg.error('请求错误', error.message);
            });
        }
    }

    frozen(memberId) {
        this.memberService.prohibition(memberId).subscribe(response => {
            if (response.result == SUCCESS) {
                this.msg.success("操作成功");
                this.getData();
            } else {
                this.msg.error('请求失败', response.message);
            }
        }, error => {
            this.msg.error('请求错误', error.message);
        });
    }

    private configPageQuery(pageQuery: PageQuery) {
        pageQuery.clearFilter();
        const searchObj = this.searchForm.value;
        if (searchObj.username != null) {
            pageQuery.addOnlyFilter('username', searchObj.username, 'contains');
        }
        if (searchObj.memberLevel != null) {
            pageQuery.addOnlyFilter('memberLevel', searchObj.memberLevel, 'eq');
        }
        if (searchObj.memberType != null) {
            pageQuery.addOnlyFilter('memberType', searchObj.memberType, 'eq');
        }
        if (searchObj.consumeNum != null) {
            pageQuery.addOnlyFilter('consumeNum', searchObj.consumeNum, 'gte');
        }
        if (searchObj.loginNum != null) {
            pageQuery.addOnlyFilter('loginNum', searchObj.loginNum, 'gte');
        }
        return pageQuery;
    }

}
