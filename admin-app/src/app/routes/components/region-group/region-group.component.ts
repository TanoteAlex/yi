import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {CommentService} from "../../services/comment.service";
import {NzMessageService} from "ng-zorro-antd";
import {RegionGroupService} from "../../services/region-group.service";
import {logger} from "codelyzer/util/logger";
import {log} from "util";

@Component({
  selector: 'app-region-group',
  template: `
    <nz-tag *ngFor="let emitValue of emitValues">{{emitValue.area ? emitValue.area.name : emitValue.province}}</nz-tag>
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
              <th style="width: 8rem">地区</th>
              <th>省</th>
            </tr>
            </thead>
            <tbody *ngFor="let dbRegion of regionGroups;let i=index" style="border-bottom: #bfc2c1 1px dashed">
            <tr>
              <td style="width: 8rem">
                <label nz-checkbox (click)="_selectSub(dbRegion,false)" [(ngModel)]="dbRegion.checked"
                       [nzValue]="dbRegion?.name">{{dbRegion?.name}}</label>&nbsp;&nbsp;
              </td>
              <td>
                <span *ngFor="let dbGroup of dbRegion?.regions">
                  <label nz-checkbox (click)="_selectSub(dbRegion,true)" [(ngModel)]="dbGroup.checked"
                         [nzValue]="dbGroup?.area?.name">{{dbGroup?.area?.name}}</label>&nbsp;&nbsp;&nbsp;&nbsp;
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
export class RegionGroupComponent implements OnChanges {
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

  regionGroups: any[] = []

  emitValues = []

  @Output()
  ok: EventEmitter<any> = new EventEmitter();

  constructor(public commentService: CommentService, public msg: NzMessageService, public regionGroupService: RegionGroupService) {
    this.regionGroupService.getRegionGroups().subscribe(response => {
      this.regionGroups = response['data'];
      this.regionGroups.forEach(e => {
        e.checked = false;
        e.province = e.name;
        e.status = 'all';
        e.regions.forEach(e1 => {
          e1.checked = false;
          e1.status = 'all';
        })
      })
    })
  }

  @Input()
  checkedValues = []

  @Input()
  checkedKeys = []

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.checkedKeys && changes.checkedKeys.currentValue) {
      setTimeout(() => {
        this.select(changes.checkedKeys.currentValue)
      }, 1000)
    }
  }


  /**
   * 调用选择回显参数
   * @param {any[]} checkedKeys input id [] or id single
   */
  select(checkedKeys: any) {
    this.emitValues = checkedKeys;
    this.dataCheck();
  }

  /**
   * 数据勾选状态刷新
   */
  dataCheck() {
    this.regionGroups.forEach(e => {
      e.regions.forEach(e1 => {
        this.emitValues.forEach(e2 => {
          if (e1.area.id == e2.area.id && e.id == e2.regionGroupId) {
            e1.checked = true;
          }
        })
      })
    })
    //子级市全选、省为选中状态
    this.regionGroups.forEach(e1 => {
      let isCheckAll = true;
      e1.regions.forEach(e2 => {
        if (!e2.checked) {
          isCheckAll = false;
        }
      })
      if (isCheckAll) {
        e1.checked = true;
      }
    })
  }

  showModal(): void {
    if (this.checkedValues.length != 0) {
      this.checkedValues.forEach(e => {
        this.regionGroups.forEach(e1 => {
          e1.regions.forEach(e2 => {
            if (e.id == e2.area.id) {
              e2.status = 'readOnly';
            }
          })
        })
      })
    }
    this.regionGroups.forEach(e1 => {
      let isCheckAll = true;
      e1.regions.forEach(e2 => {
        if (e2.status == 'all') {
          isCheckAll = false;
        }
      })
      if (isCheckAll) {
        e1.status = 'readOnly';
      }
    })
    this.isVisible = true;
  }

  handleOk(): void {
    if(this.emitValues){
      this.emitValues.forEach(e=>{
        this.checkedValues.forEach(e1=>{
          if (e.area.id == e1.id) {
            this.checkedValues = this.checkedValues.filter(e2=>e2.id != e1.id);
          }
        })
      })
    }
    let submitState = true;
    let unSubmitName = "";
    if (this.checkedValues.length != 0) {
      this.checkedValues.forEach(e => {
        this.regionGroups.forEach(e1 => {
          e1.regions.forEach(e2 => {
            if (e.id == e2.area.id && e2.status == 'readOnly' && e2.checked == true) {
              unSubmitName= unSubmitName + e2.area.name+"、"
              submitState = false;
            }
          })
        })
      })
    }
    if (submitState) {
      this.isVisible = false;
      let backProvinceAndCity = []
      this.regionGroups.forEach(e => {
        if (e.checked) {
          e.regions.forEach(e1 => {
            backProvinceAndCity.push(e1)
          })
        } else {
          e.regions.forEach(e1 => {
            if (e1.checked) {
              backProvinceAndCity.push(e1)
            }
          })
        }
      })
      this.emitValues = backProvinceAndCity
      this.ok.emit(backProvinceAndCity)
      this.result.emit(backProvinceAndCity)
    }else{
      this.msg.error(unSubmitName+"已被选取使用")
    }
  }

  handleCancel(): void {
    this.regionGroups.forEach(e => {
      e.checked = false;
      e.regions.forEach(e1 => {
        e1.checked = false;
      })
    })
    this.dataCheck();
    this.isVisible = false;
  }

  _selectAll() {
    let isSelect = true
    if (this.regionGroups.every(e => e.checked)) {
      isSelect = false;
    }
    this.regionGroups.forEach(e => {
      e.checked = isSelect
      e.regions.forEach(e1 => {
        e1.checked = isSelect
      })
    })
  }

  _selectSub(dbRegion, isCity) {
    if (!isCity) {
      dbRegion.regions.forEach(e1 => {
        e1.checked = dbRegion.checked
      })
    } else {
      if (dbRegion.regions.every(e => e.checked)) {
        dbRegion.checked = true
      } else {
        dbRegion.checked = false
      }
    }
  }

}
