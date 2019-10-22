import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'nz-modal-customer',
  template: `
    <a style="color: #0090ff" *ngIf="showButton" (click)="showModal()">{{buttonName}}
      <ng-content></ng-content>
    </a>
    <nz-modal *ngIf="showButton" [(nzVisible)]="isVisible" [nzTitle]="title" (nzOnCancel)="handleCancel()" (nzOnOk)="handleOk()">
      <p>{{content}}</p>
    </nz-modal>
    <button nz-button nzType="primary" type="submit" *ngIf="!showButton" (click)="showModal()">{{buttonName}}
      
    </button>
    <nz-modal *ngIf="!showButton" [(nzVisible)]="isVisible" [nzTitle]="title" (nzOnCancel)="handleCancel()" (nzOnOk)="handleOk()">
      <p>{{content}}</p>
    </nz-modal>
  `,
  styles: []
})
export class ModalComponent {
  isVisible = false;
  @Input()
  title = ''
  @Input()
  content = ''
  @Input()
  buttonName = 'Show Modal'
  @Input()
  showButton = true

  @Output()
  ok: EventEmitter<any> = new EventEmitter();


  constructor() {
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    this.ok.emit("ok");
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

}
