import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {DeptService} from '../../../services/dept.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {ObjectUtils} from "@shared/utils/ObjectUtils";
import {Dept} from "../../../models/original/dept.model";

@Component({
    selector: 'list-dept',
    templateUrl: './list-dept.component.html',
    styleUrls: ['./list-dept.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ListDeptComponent implements OnInit {

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  dept: Dept;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private deptService: DeptService, public msg: NzMessageService,
              private fb: FormBuilder,private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.deptService.query(this.pageQuery).subscribe(response => {
      this.datas=response['content'];
      this.datas.forEach(e => {
        //null parent
        if (!e.parent && e.deleted == 0) {
          e.children = []
          this.data.push(e)
        }

      })
      this.datas.forEach(e => {
        if (e.parent && e.deleted == 0) {
          this.datas.filter(e1 => e1.id == e.parent.id)[0].children.push(e)
        }
      })
      this.data = this.data.map(e => {
        return {
          parent: e.parent,
          key: e.id,
          id: e.id,
          deptName: e.deptName,
          children: e.children,
          deptNo: e.deptNo,
          description: e.description,
          createTime: e.createTime,
        }
      })
      // this.data = data.data
      this.data.forEach(item => {
        this.expandDataCache[item.id] = this.convertTreeToList(item);
      });
    })

  }

  getData() {
    this.loading = true;
    this.deptService.query(this.pageQuery).subscribe(response => {
      this.datas=response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误'+ error.message);
    });
  }

  submitCheck(): any {
    const commonFormValid = ObjectUtils.checkValidated(this.searchForm);
    if (commonFormValid) {
      return this.searchForm.value;
    }
    return null;
  }

  remove(item, table) {
    this.deptService.removeById(item.id).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("删除成功");
        item.del = true
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }



  formErrors = {
    id: [],
    parent:[
      {
        name: 'maxlength',
        msg: '最大10位长度',
      }
    ],
    deptNo:[
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    deptName:[
      {
        name: 'required',
        msg: '不可为空',
      },
      {
        name: 'maxlength',
        msg: '最大16位长度',
      }
    ],
    description:[
      {
        name: 'maxlength',
        msg: '最大32位长度',
      }
    ],
    name:[]
  };

  buildForm(): void {
    this.searchForm = this.fb.group({
      id: [],
      parent: [
        {
          id:0,
          deptName:"请选择",
        }
      ],
      deptNo: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(10)
        ])
      ],
      deptName: [
        null, Validators.compose([Validators.required,
          Validators.maxLength(16)
        ])
      ],
      description: [
      ],
      name:[]
    });
  }

  data = [];
  expandDataCache = {};

  collapse(array: any[], data: any, $event: boolean): void {
    if ($event === false) {
      if (data.children) {
        data.children.forEach(d => {
          const target = array.find(a => a.id === d.id);
          target.expand = false;
          this.collapse(array, target, false);
        });
      } else {
        return;
      }
    }
  }

  convertTreeToList(root: object): any[] {
    const stack = [];
    const array = [];
    const hashMap = {};
    stack.push({...root, level: 0, expand: false});
    //console.log(stack);
    while (stack.length !== 0) {
      const node = stack.pop();
      this.visitNode(node, hashMap, array);
      if (node.children) {
        for (let i = node.children.length - 1; i >= 0; i--) {
          stack.push({...node.children[i], level: node.level + 1, expand: false, parent: node});
        }
      }
    }

    return array;
  }

  visitNode(node: any, hashMap: object, array: any[]): void {
    if (!hashMap[node.id]) {
      hashMap[node.id] = true;
      array.push(node);
    }
  }



}
