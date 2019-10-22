
import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NzTreeNode} from 'ng-zorro-antd';
import {OperateCategoryService} from "../../services/operate-category.service";

@Component({
  selector: 'app-operateTree',
  template: `
    <nz-input-group  *ngIf="showSearch"  [nzSuffix]="suffixIcon">
      <input type="text" nz-input placeholder="Search Tree Node" [(ngModel)]="searchValue">
    </nz-input-group>
    <ng-template  *ngIf="showSearch"  #suffixIcon>
      <i class="anticon anticon-search"></i>
    </ng-template>
    <nz-tree [(ngModel)]="nodes"
             [nzSearchValue]="searchValue"
             [nzCheckable]="nzCheckable"
             [nzDefaultCheckedKeys]="nzDefaultCheckedKeys"
             (nzOnSearchNode)="mouseAction('search',$event)"
             (nzCheckBoxChange)="mouseAction('check',$event)"
             (nzClick)="mouseAction('click', $event)"
             (nzExpandChange)="mouseAction('expand',$event)"
             (nzDblClick)="mouseAction('dblclick',$event)">
    </nz-tree>`
})
export class OperateTreeComponent{
  @Input()
  showSearch = false
  @Input()
  nzCheckable = false
  @Input()
  nzMultiple = false
  @Input()
  expandKeys = [];
  @Input()
  checkedKeys = [];
  @Input()
  selectedKeys = [];
  @Input()
  isSecond=true;
  @Input()
  nzDefaultCheckedKeys = [];
  @Output()
  result: EventEmitter<any> = new EventEmitter()
  searchValue;
  expandDefault = false;
  nodes = [];

  constructor(public operateCategoryService: OperateCategoryService) {
  }

  mouseAction(name: string, e: any): void {
    this.result.emit(e)
  }


  ngOnInit(): void {
    this.getData();
  }

  getData() {
    let node: any = []
    let operateCategory: any = []
    let root = {
      title: '所有分类',
      key: '0',
      children: []
    }
    this.operateCategoryService.getAllCategoryForSupplier().subscribe(data => {
      data.data.forEach(e =>{
        if (!e.parent && e.deleted == 0){
          operateCategory ={
            title: e.name,
            key: e.id,
            children: []
          }
          node.push(operateCategory)
        }
        if(this.isSecond){
          if(e => e.parent && e.deleted == 0){
            e.children.forEach(e1 =>{
              operateCategory.children.push({
                title: e1.name,
                key: e1.id,
                isLeaf: true
              })
            })
          }
        }
      })
      root.children = node
      this.nodes = [new NzTreeNode(root)]
    })
  }
}


