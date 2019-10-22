import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {CategoryService} from "../../services/category.service";
import {NzTreeNode, NzTreeSelectComponent} from "ng-zorro-antd";
import {OperateCategoryService} from "../../services/operate-category.service";
import {DeptService} from "../../services/dept.service";
import {PageQuery} from "../../models/page-query.model";

@Component({
  selector: 'app-dept-tree-select',
  template: `
    <nz-tree-select #nzTreeSelect *ngIf="!isCheck"
                    style="width: 100%"
                    [nzNodes]="_nodes"
                    [(ngModel)]="_value"
                    nzShowSearch
                    nzPlaceHolder="请选择"
                    (nzTreeClick)="_checkBoxChange($event,false)"
                    (nzCleared)="_checkBoxChange($event,false)"
    >
    </nz-tree-select>
    <nz-tree-select #nzTreeSelect *ngIf="isCheck"
                    style="width: 100%"
                    [nzNodes]="_nodes"
                    [(ngModel)]="_value"
                    nzShowSearch
                    nzCheckable
                    nzPlaceHolder="请选择"
                    (nzTreeCheckBoxChange)="_checkBoxChange($event,false)"
                    (nzRemoved)="_checkBoxChange($event,true)"
                    (nzCleared)="_checkBoxChange($event,false)"
    >
    </nz-tree-select>`,
  styles: []
})
export class DeptTreeSelectComponent implements OnInit {

  /**
   * 是否复选
   * @type {boolean}
   */
  @Input()
  isCheck = true;

  /**
   * 是否显示主节点
   * @type {boolean}
   */
  @Input()
  showRoot = true;

  /**
   * 只展示等级
   * @type {boolean}
   */
  @Input()
  nodeLevel = 2;

  /**
   * 回调
   * @type {EventEmitter<any>}
   */
  @Output()
  result: EventEmitter<any> = new EventEmitter();

  /**
   * 树构建
   * @type {any[]}
   */
  private _nodes = [];

  /**
   * 值显示
   * @type {any[]}
   */
  private _value = [];

  /**
   * 保存选择值
   * @type {any[]}
   * @private
   */
  private _choose


  /**
   * nzTreeSelect对象
   */
  @ViewChild('nzTreeSelect')
  private _nzTreeSelect: NzTreeSelectComponent;

  pageQuery: PageQuery = new PageQuery();
  datas: any[] = [];

  constructor(public deptService: DeptService) {
  }


  ngOnInit(): void {
    this._getData();
  }


  /**
   * 调用选择回显参数
   * @param {any[]} checkedKeys input id [] or id single
   */
  select(checkedKeys: any) {
    this._nzTreeSelect.writeValue(checkedKeys)
  }

  /**
   * 值变化回调
   * @param e
   */
  private _checkBoxChange(event: any, isRemove): void {
    if (this.isCheck) {
      // console.log(event.checkedKeys);
      let chooses = []
      if (event && !isRemove) {
        event.checkedKeys.forEach(e1 => {
          // if (e1.children.length != 0) {
          //   chooses.push(...e1.children.map(e => {
          //     return {
          //       id: e.origin.key,
          //       categoryName: e.origin.title,
          //     }
          //   }));
          // } else {
          chooses.push({
            id: e1.key,
            deptName: e1.title,
          });
          // }
        })
      } else {
        chooses = this._choose.filter(e => e.id != event.key)
        // console.log(event);
        // console.log(this._choose);
      }
      // console.log(chooses);
      this._choose = chooses
      this.result.emit(chooses)

    } else {
      let choose = {}
      // console.log(event.selectedKeys);
      if (event) {
        choose = {
          id: event.selectedKeys[0].key,
          deptName: event.selectedKeys[0].title,
        }
      }
      // console.log("this.isCheck false");
      // console.log(choose);
      this._choose = choose
      this.result.emit(choose)
    }
  }

  /**
   * 获取数据源
   */
  private _getData() {
    let node: any = [];
    let dept: any = [];
    let root = {
      title: '所有分类',
      key: '0',
      children: []
    };
    //getAllCategory callback
    this.deptService.query(this.pageQuery).subscribe(response=>{
      this.datas = response['content'];
      this.datas.forEach(e => {
        if (!e.parent && e.deleted == 0) {
          dept = {
            title: e.deptName,
            key: e.id,
            children: []
          };
          node.push(dept)
        }
        if (this.nodeLevel == 2) {
          if (e => e.parent && e.deleted == 0) {
            e.children.forEach(e1 => {
              dept.children.push({
                title: e1.deptName,
                key: e1.id,
                isLeaf: true
              })
            })
          }
        }
      });
      if (this.showRoot) {
        root.children = node;
        this._nodes = [new NzTreeNode(root)]
      } else {
        this._nodes = node.map(e => new NzTreeNode(e));
      }
    })
  }

}
