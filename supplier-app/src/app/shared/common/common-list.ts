import {ActivatedRoute, Router} from "@angular/router";

import {NzMessageService, NzModalService} from "ng-zorro-antd";
import {PageQuery} from "../../routes/models/page-query.model";
import {BaseService} from "../../routes/services/base.service";
import {SUCCESS} from "../../routes/models/constants.model";

export class CommonList {

  pageQuery: PageQuery = new PageQuery();

  selectId: string = "0";

  anys: Array<any>


  constructor(public route: ActivatedRoute, public router: Router, public msg: NzMessageService, public baseService: BaseService, private modalService: NzModalService) {
  }


  getSelect(id, i) {
    this.selectId = id
    /*  let elementsByClassName: any = document.getElementsByName("select");
      elementsByClassName.item(i).checked = true*/
  }

  getDatas() {
    this.baseService.query(this.pageQuery).subscribe(response => {
      this.anys = response.content;
      this.pageQuery.covertResponses(response);
    });
  }

  pageChange(event) {
    this.pageQuery.page = event;
    this.getDatas();
  }

  edit() {
    if (this.selectId !== "0") {
      this.router.navigate(['../edit/' + this.selectId], {relativeTo: this.route});
    } else {
      this.msg.error('error');
    }
  }

  view() {
    if (this.selectId !== "0") {
      this.router.navigate(['../view/' + this.selectId], {relativeTo: this.route});
    } else {
      this.msg.error('error');
    }
  }


  remove() {
    this.modalService.confirm({
      nzTitle: '<i>提示！</i>',
      nzContent: '<b>确认要删除吗？</b>',
      nzOnOk: () => {
        this.baseService.removeById(this.selectId).subscribe(response => {
          if (response.result == SUCCESS) {
            this.msg.success("删除成功");
            // this.pageQuery.requests.page = 1;
            this.getDatas();
          } else {
            this.msg.error('请求失败' + response.message);
          }
        }, error => {
          this.msg.error('请求失败' + error.message);
        });
      }
    });
  }

  clear() {
    this.pageQuery.clearFilter();
    let elementsByClassName: any = document.getElementsByClassName("filter");
    for (let elementsByClassNameKey in elementsByClassName) {
      elementsByClassName.item(Number(elementsByClassNameKey)).value = "";
    }
    this.getDatas();
  }

}
