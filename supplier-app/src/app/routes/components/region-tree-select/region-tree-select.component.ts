import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommentService} from "../../services/comment.service";
import {NzMessageService} from "ng-zorro-antd";
import {Area} from "../../models/original/area.model";
import {AreaService} from "../../services/area.service";
import {RegionService} from "../../services/region.service";
import {PageQuery} from "../../models/page-query.model";

@Component({
  selector: 'app-region-tree-select',
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
        <nz-form-label nzXs="10" nzSm="2" nzRequired>选择区域</nz-form-label>
        <nz-form-control nzXs="16" nzSm="8" nzMd="20">
          <div>
             <span *ngFor="let dbArea of areas.province;let i=index">
                <label nz-checkbox [(ngModel)]="dbArea.checked" [nzValue]="dbArea?.name">{{dbArea?.name}}
                </label>&nbsp;&nbsp;&nbsp;&nbsp;
            </span>
          </div>

        </nz-form-control>
      </nz-form-item>
    </nz-modal>
  `,
  styles: [],
})
export class RegionTreeSelectComponent {
  isVisible = false;
  @Input()
  title = ''
  /**
   * 已选对象 多选传数组 require
   * @type {number}
   */
  @Input()
  select;
  @Input()
  buttonName = 'Show Modal'

  /**
   * 按钮上的显示值
   * @type {string}
   */

  @Output()
  result = new EventEmitter

  provinces: any[] = []

  areas = {
    province: []
  };
  emitValues = [];

  @Output()
  ok: EventEmitter<any> = new EventEmitter();

  constructor(public commentService: CommentService, public msg: NzMessageService, public areaService: AreaService) {
    this.areaService.getProvinces().subscribe(response => {
      this.provinces = response['data'];
      this.provinces.forEach((e, index) => {
        e.checked = false
        this.areas.province.push(e)
      })
    })
  }

  /**
   * 调用选择回显参数
   * @param {any[]} checkedKeys input id [] or id single
   */
  showSelect(checkedKeys: any) {
    checkedKeys.forEach(e => {
      e.name = e.province;
      this.areas.province.forEach(e1 => {
        if (e1.name == e.name) {
          e1.checked = true;
        }
      })
    })
    this.emitValues = checkedKeys;
  }

  showModal(): void {
    if (this.select != null) {
      this.areas.province.forEach(e => {
        this.select.forEach(e1 => {
          if (e.name == e1.province) {
            e.checked = true;
          }
        })
      })
    }
    this.isVisible = true;
  }

  handleOk(): void {
    this.isVisible = false;
    let backProvinceAndCity = []
    this.areas.province.forEach(e => {
      if (e.checked) {
        backProvinceAndCity.push(e)
      }
      /*else{
              e.city.forEach(e1 => {
                if (e1.checked) {
                  backProvinceAndCity.push(e1)
                }
              })
            }*/
    })
    this.emitValues = backProvinceAndCity;
    this.result.emit(backProvinceAndCity)
  }

  handleCancel(): void {
    this.areas.province.forEach(e => {
      e.checked = false;
      this.emitValues.forEach(e1 => {
        if (e.name == e1.name) {
          e.checked = true;
        }
      })
    })
    this.isVisible = false;
  }

  _selectAll() {
    let isSelect = true
    if (this.areas.province.every(e => e.checked)) {
      isSelect = false;
    }
    this.areas.province.forEach(e => {
      e.checked = isSelect
    })
  }

}
