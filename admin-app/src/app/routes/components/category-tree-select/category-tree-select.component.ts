import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {CategoryService} from "../../services/category.service";
import {NzTreeNode, NzTreeSelectComponent} from "ng-zorro-antd";
import {OperateCategoryService} from "../../services/operate-category.service";

@Component({
  selector: 'app-category-tree-select',
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
                    (nzTreeClick)="_checkBoxClick($event)"
                    (nzRemoved)="_checkBoxChange($event,true)"
                    (nzCleared)="_checkBoxChange($event,false)"
    >
    </nz-tree-select>`,
  styles: []
})
export class CategoryTreeSelectComponent implements OnInit {

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
   * service执行判断
   */
  @Input()
  service = true;

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

  constructor(public categoryService: CategoryService, public operateCategoryService: OperateCategoryService) {
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
    if (this.isCheck) {
      this._choose = []
      this._choose = checkedKeys.map(e => {
        return {id: e}
      })
    }
  }

  /**
   * 值变化回调
   * @param e
   */
  private _checkBoxChange(event: any, isRemove): void {
    if (this.isCheck) {
      let chooses = []
      event.checkedKeys.forEach(e1 => {
        this._ignoreParent(e1, chooses)
        this._filterChildren(chooses);
      })
      this._choose = chooses
      this.result.emit(chooses)
    } else {
      let choose = {}
      if (event) {
        choose = {
          id: event.selectedKeys[0].key,
          categoryName: event.selectedKeys[0].title,
        }
      }
      this._choose = choose
      this.result.emit(choose)
    }
  }

  /**
   * 忽略父级只保留子集
   * @param e1        这个是树的node对象
   * @param chooses   构建集合
   * @private
   */
  private _ignoreParent(e1, chooses) {
    if (e1.children.length != 0) {
      chooses.push(...e1.children.map(e => {
        return {id: e.key}
      }));

    } else {
      chooses.push({
        id: e1.origin.key,
      });
    }
  }

  /**
   * 复选点击文字事件
   * @param event
   * @private
   */
  _checkBoxClick(event: any) {
    let selectedKey = event.selectedKeys[0];
    let chooses = []
    this._ignoreParent(selectedKey, chooses)
    this._filterChildren(chooses);
    this.result.emit(this._choose)
  }

  /**
   * 过滤已选集合
   * @param children
   * @private
   */
  private _filterChildren(children) {
    if(!this._choose){
      this._choose=[]
    }
    children.forEach(e1 => {
      if (this._choose.filter(e => e.id == e1.id).length > 0) {
        this._choose = this._choose.filter(e => e.id != e1.id)
      } else {
        this._choose.push({id: e1.id})
      }
    })
  }

  /**
   * 获取数据源
   */
  private _getData() {
    let node: any = [];
    let category: any = [];
    let root = {
      title: '所有分类',
      key: '0',
      children: []
    };
    //getAllCategory callback
    let callback = data => {
      data.data.forEach(e => {
        if (!e.parent && e.deleted == 0) {
          category = {
            title: e.categoryName,
            key: e.id,
            children: []
          };
          node.push(category)
        }
        if (this.nodeLevel == 2) {
          if (e => e.parent && e.deleted == 0) {
            e.children.forEach(e1 => {
              category.children.push({
                title: e1.categoryName,
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
    }
    if (this.service) {
      this.categoryService.getAllCategory().subscribe(callback)
    } else {
      this.operateCategoryService.getAllCategory().subscribe(callback)
    }
  }

}
