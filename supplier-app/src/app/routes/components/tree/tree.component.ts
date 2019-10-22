import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NzTreeNode} from 'ng-zorro-antd';
import {CategoryService} from "../../services/category.service";

@Component({
  selector: 'app-tree',
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
export class TreeComponent {
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

  constructor(public categoryService: CategoryService) {
  }

  checkSelects = [];

  mouseAction(name: string, e: any): void {
    if(name == "check"){
      if(e.node.origin.key == "0"){
        e.node.children.forEach(e4 =>{
          e4.children.forEach(e5 =>{
            if(e5.isChecked) {
              this.checkSelects.forEach(e7 =>{
                if(e7.key == e5.key){
                  this.checkSelects.splice(this.checkSelects.indexOf(e7), 1);
                }
              })
              this.checkSelects.push(e5)
            }else{
              this.checkSelects.splice(this.checkSelects.indexOf(e5), 1);
            }
          })
        })
      }else{
        if(e.node.parentNode.origin.key == "0"){
          e.node.children.forEach(e1 => {
            if(e1.isChecked){
              this.checkSelects.forEach(e6 =>{
                if(e6.key == e1.key){
                  this.checkSelects.splice(this.checkSelects.indexOf(e6), 1);
                }
              })
              this.checkSelects.push(e1)
            }else {
              this.checkSelects.forEach(e2 =>{
                if(!e2.isChecked){
                  this.checkSelects.splice(this.checkSelects.indexOf(e2), 1);
                }
              })
            }
          })
          e=this.checkSelects;
        }else{
          if(e.node.isChecked){
            this.checkSelects.push(e.node)
          }else{
            this.checkSelects.forEach(e3 =>{
              if(!e3.isChecked){
                this.checkSelects.splice(this.checkSelects.indexOf(e3), 1);
              }
            })
          }
          e=this.checkSelects;
        }
      }
    }
    this.result.emit(e)
  }


  ngOnInit(): void {
    this.getData();
  }

   getData() {
    let node: any = []
    let category: any = []
    let root = {
      title: '所有分类',
      key: '0',
      children: []
    }
    this.categoryService.getAllCategoryForSupplier().subscribe(data => {
      data.data.forEach(e => {
        if (!e.parent && e.deleted == 0) {
          category = {
            title:e.categoryName,
            key:e.id,
            children:[]
          }
          node.push(category)
        }
        if (this.isSecond) {
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
      })
      root.children = node
      this.nodes = [new NzTreeNode(root)]
    })
  }
}

