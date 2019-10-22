import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {RescService} from "../../services/resc.service";

@Component({
  selector: 'app-purviewTree',
  template: `
    <nz-tag *ngFor="let name of emitValues">{{name.name}}</nz-tag>
    <button nz-button
            class="editable-tag"
            (click)="showModal()">
      <i nz-icon type="plus"></i>{{buttonName}}
    </button>
    <nz-modal nzWidth="60rem" [(nzVisible)]="isVisible" [nzTitle]="title"
              (nzOnCancel)="handleCancel()" (nzOnOk)="handleOk()">
      <nz-form-item>
        <button nz-button class="ml-sm" type="button" (click)="_selectAll()">全选</button>
        <nz-form-label nzXs="10" nzSm="2" nzRequired>地址</nz-form-label>
        <nz-form-control nzXs="16" nzSm="8" nzMd="20">
          <table style="width: 100%">
            <thead>
            <tr>
              <th style="width: 8rem">一级菜单</th>
              <th>二级菜单</th>
            </tr>
            </thead>
            <tbody *ngFor="let dbResc of resc;let i=index" style="border-bottom: #bfc2c1 1px dashed">
            <tr>
              <td style="width: 8rem">
                <label nz-checkbox (click)="_selectSub(dbResc,false)" [(ngModel)]="dbResc.checked"
                       [nzValue]="dbResc?.name">{{dbResc?.name}}</label>&nbsp;&nbsp;
              </td>
              <td>
                <span *ngFor="let rescs of dbResc?.children">
                  <label nz-checkbox (click)="_selectSub(dbResc,true)" [(ngModel)]="rescs.checked"
                         [nzValue]="rescs?.name">{{rescs?.name}}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                </span>
              </td>
            </tr>
            </tbody>
          </table>
        </nz-form-control>
      </nz-form-item>
    </nz-modal>
  `,
  styles: [/*`
    ::ng-deep .ant-modal-content {
      max-height: 800px !important;
      overflow: auto;
    }
  `*/]
})
/*`.editable-tag ::ng-deep .ant-tag {
    background: rgb(255, 255, 255);
    border-style: dashed;
  }`*/
export class PurviewTreeComponent {
  isVisible = false;
  @Input()
  title = ''
  @Input()
  content = ''
  @Input()
  buttonName = 'Show Modal'

  /**
   * 按钮上的显示值
   * @type {string}
   */
  @Input()
  resultName = [];

  @Output()
  result = new EventEmitter

  resc: any[] = []

  emitValues = []

  @Output()
  ok: EventEmitter<any> = new EventEmitter();

  constructor(public rescService: RescService) {
    this.rescService.getAllResc().subscribe(response => {
      this.resc = response['data'];
      this.resc.forEach(e => {
        e.checked = false;
        e.children.forEach(e1 => {
          e1.checked = false;
        })
      })
    })
  }

  /**
   * 调用选择回显参数
   * @param {any[]} checkedKeys input id [] or id single
   */
  select(checkedKeys: any) {
    this.emitValues = checkedKeys;
    setTimeout(() => {
      this.dataCheck();
    }, 1000);

  }

  /**
   * 数据勾选状态刷新
   */
  dataCheck() {
    this.resc.forEach(e => {
      this.emitValues.forEach(e1 => {
        if (e.name == e1.name) {
          e.checked = true;
        }
      })
      e.children.forEach(e1 => {
        this.emitValues.forEach(e2 => {
          if (e1.name == e2.name) {
            e1.checked = true;
          }
        })
      })
    })
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    this.isVisible = false;
    let backProvinceAndCity = []
    this.resc.forEach(e => {
      let isAdd = true;
      if (e.checked) {
        backProvinceAndCity.push(e)
        e.children.forEach(e1 => {
          backProvinceAndCity.push(e1)
        })
      } else {
        e.children.forEach(e1 => {
          if (e1.checked) {
            backProvinceAndCity.push(e1)
            if(isAdd){
              backProvinceAndCity.push(e)
              isAdd = false;
            }
          }
        })
      }
    })
    this.emitValues = backProvinceAndCity
    this.ok.emit(backProvinceAndCity)
    this.result.emit(backProvinceAndCity)
  }

  handleCancel(): void {
    this.resc.forEach(e => {
      e.checked = false;
      e.children.forEach(e1 => {
        e1.checked = false;
      })
    })
    this.dataCheck();
    this.isVisible = false;
  }

  _selectAll() {
    let isSelect = true
    if (this.resc.every(e => e.checked)) {
      isSelect = false;
    }
    this.resc.forEach(e => {
      e.checked = isSelect
      e.children.forEach(e1 => {
        e1.checked = isSelect
      })
    })
  }

  _selectSub(dbResc, isCity) {
    if (!isCity) {
      dbResc.children.forEach(e1 => {
        e1.checked = dbResc.checked
      })
    } else {
      if (dbResc.children.every(e => e.checked)) {
        dbResc.checked = true
      } else {
        dbResc.checked = false
      }
    }

  }
}


