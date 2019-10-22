import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommentService} from "../../services/comment.service";
import {NzMessageService} from "ng-zorro-antd";
import {Area} from "../../models/original/area.model";
import {AreaService} from "../../services/area.service";

@Component({
  selector: 'app-area',
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
          <table>
            <thead>
            <tr>
              <th style="width: 8rem">省</th>
              <th>市</th>
            </tr>
            </thead>
            <tbody *ngFor="let dbArea of areas.province;let i=index">
            <tr>
              <td style="width: 8rem">
                <label nz-checkbox (click)="_selectSub(dbArea,false)" [(ngModel)]="dbArea.checked" [nzValue]="dbArea?.areaCode">{{dbArea?.name}}</label>&nbsp;&nbsp;
              </td>
              <td>
                <span *ngFor="let dbCity of dbArea?.city">
                  <label nz-checkbox (click)="_selectSub(dbArea,true)" [(ngModel)]="dbCity.checked" [nzValue]="dbCity?.areaCode">{{dbCity?.name}}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                </span>
              </td>
            </tr>
            </tbody>
          </table>
        </nz-form-control>
      </nz-form-item>
    </nz-modal>
  `,
  styles: [
      `.editable-tag ::ng-deep .ant-tag {
      background: rgb(255, 255, 255);
      border-style: dashed;
    }`
  ]
})
export class AreaComponent {
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
  resultName = '请选择区域';

  @Output()
  result = new EventEmitter

  provinces: any[] = []
  citys: any[] = []
  districts: any[] = []

  areas = {
    province: [
      //   {
      //   city: [{}],
      //   checked: false,
      // }
    ]
  };
  emitValues = []

  @Output()
  ok: EventEmitter<any> = new EventEmitter();

  constructor(public commentService: CommentService, public msg: NzMessageService, public areaService: AreaService) {
    this.areaService.getAllAreas(new Area()).subscribe(response => {
      this.provinces = response['data'];
      this.provinces.forEach((e, index) => {
        e.checked = false
        // console.log(e)
        e.children.forEach( e1=>{
          e1.checked = false
        })
        e.city = e.children
        this.areas.province.push(e)
        /*this.areaService.getAreas(e).subscribe(response => {
          this.citys = response['data'];
          this.citys.forEach(e1 => {
            e1.checked = false
          })
          e.city = this.citys
          this.areas.province.push(e)
        })*/
      })
    })
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    this.isVisible = false;
    let backProvinceAndCity = []
    this.areas.province.forEach(e => {
      if (e.checked) {
        backProvinceAndCity.push(e)
      } else {
        e.city.forEach(e1 => {
          if (e1.checked) {
            backProvinceAndCity.push(e1)
          }
        })
      }
    })
    this.emitValues = backProvinceAndCity
    this.ok.emit(backProvinceAndCity)
    this.result.emit(backProvinceAndCity)
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  _selectAll() {
    let isSelect = true
    if (this.areas.province.every(e => e.checked)) {
      isSelect = false;
    }
    this.areas.province.forEach(e => {
      e.checked = isSelect
      e.city.forEach(e1 => {
        e1.checked = isSelect
      })
    })
  }

  _selectSub(dbArea, isCity) {
    if (!isCity) {
      dbArea.city.forEach(e1 => {
        e1.checked = dbArea.checked
      })
    } else {
      if (dbArea.city.every(e => e.checked)) {
        dbArea.checked = true
      } else {
        dbArea.checked = false
      }
    }

  }
}
