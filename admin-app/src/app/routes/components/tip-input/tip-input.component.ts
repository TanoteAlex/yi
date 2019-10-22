import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-tip-input',
  template: `
    <input [(ngModel)]="showValue" autocomplete="off" *ngIf="!isAttrValue" nz-input aria-describedby="basic-addon1" list="item" (keyup)="input($event)"
           placeholder="请输入">
    <datalist *ngIf="!isAttrValue" id="item">
      <option *ngFor="let item of tips" [value]="item.groupName">
    </datalist>
    <input [(ngModel)]="showValue" autocomplete="off" [id]="id" class="attr-value" *ngIf="isAttrValue" nz-input aria-describedby="basic-addon1" list="1" (keyup)="input($event)" placeholder="请输入">
    <datalist class="attr-value-data" *ngIf="isAttrValue" id="1">
      <option *ngFor="let item of tips" [value]="item.attrValue">
    </datalist>
  `,
  styles: []
})
export class TipInputComponent implements OnInit {

  /**
   * 是否属性值
   * @type {boolean}
   */
  @Input()
  isAttrValue = false;

  /**
   * 提示文字集合
   * @type {any[]}
   */
  @Input()
  tips = [];

  /**
   * 展示输入值
   * @type {string}
   */
  @Input()
  showValue = ''

  /**
   * 唯一id
   * @type {string}
   */
  @Input()
  id = '0'

  /**
   * 回调
   * @type {EventEmitter<any>}
   */
  @Output()
  result: EventEmitter<any> = new EventEmitter(null);

  /**
   * 确保绑定datalist只执行一次
   * @type {boolean}
   * @private
   */
  _isLock = false;

  constructor() {
  }

  /**
   * 确保在属性组选择后调用绑定datalist
   */
  ngDoCheck() {
    if (!this._isLock && this.tips.length != 0) {
      let elementById = document.getElementById(this.id);
      if (elementById != null) {
        elementById.setAttribute('list', this.tips[0].id)
        elementById.nextElementSibling.id = this.tips[0].id
        this._isLock = true;
      }
    }
  }

  /**
   * 输入回调
   * @param e
   */
  input(e) {
    this.result.emit(e.target.value)
  }

  ngOnInit() {
  }

}
