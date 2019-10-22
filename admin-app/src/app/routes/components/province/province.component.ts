import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AreaService} from "../../services/area.service";
import {PageQuery} from "../../models/page-query.model";

const options = [{
  value: 'zhejiang',
  label: 'Zhejiang',
  children: [{
    value: 'hangzhou',
    label: 'Hangzhou',
    children: [{
      value: 'xihu',
      label: 'West Lake',
      isLeaf: true
    }]
  }, {
    value: 'ningbo',
    label: 'Ningbo',
    isLeaf: true
  }]
}, {
  value: 'jiangsu',
  label: 'Jiangsu',
  children: [{
    value: 'nanjing',
    label: 'Nanjing',
    children: [{
      value: 'zhonghuamen',
      label: 'Zhong Hua Men',
      isLeaf: true
    }]
  }]
}];

@Component({
  selector: 'nz-province-basic',
  template: `
    <nz-cascader
      [nzOptions]="nzOptions"
      [(ngModel)]="values"
      (ngModelChange)="onChanges($event)"
      [nzPlaceHolder]="'请选择'"
    >
    </nz-cascader>
  `,
  styles: [
      `
      .ant-cascader-picker {
        width: 300px;
      }

      .change-options {
        display: inline-block;
        font-size: 12px;
        margin-top: 8px;
      }
    `
  ]
})
export class ProvinceComponent implements OnInit {
  /** init data */
  public nzOptions = null;

  /** ngModel value */
  @Input()
  public values: any[] = null;
  @Output()
  public result: EventEmitter<any> = new EventEmitter()

  pageQuery: PageQuery = new PageQuery();

  constructor(public areaService: AreaService){

  }

  ngOnInit(): void {
    let options = []
    let datas = []
    this.areaService.getAllAreas(this.pageQuery).subscribe(response => {
      datas = response.data;
      datas.forEach(e=>{
        let province = {
          value: e.areaCode,
          label: e.name,
          children: []
        }
        e.children.forEach(e1=>{
          let city = {
            value: e1.areaCode,
            label: e1.name,
            children: []
          }
          e1.children.forEach(e2=>{
            city.children.push({
              value: e2.areaCode,
              label: e2.name,
              isLeaf: true
            })
          })
          province.children.push(city)
        })
        options.push(province)
      })
      setTimeout(() => {
        this.nzOptions = options;
      }, 100);
    })
  }

  public onChanges(values: any): void {
    this.result.emit(values)
  }
}

