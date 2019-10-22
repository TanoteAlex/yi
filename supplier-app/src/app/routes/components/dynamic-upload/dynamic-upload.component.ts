import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {NzMessageService, UploadFile} from 'ng-zorro-antd';
import {environment} from "@env/environment";

@Component({
  selector: 'app-dynamic-upload',
  template: `
    <div class="clearfix">
      <nz-upload *ngIf="!multi"
                 nzAction="{{_uploadUrl}}"
                 nzListType="picture-card"
                 [(nzFileList)]="fileList"
                 [nzShowButton]="fileList.length < 1"
                 (nzChange)="change($event)"
                 (nzFileListChange)="changeFileList()"
      >
        <i class="anticon anticon-plus"></i>
        <div class="ant-upload-text">Upload</div>
      </nz-upload>
      <nz-upload *ngIf="multi"
                 nzAction="{{_uploadUrl}}"
                 nzListType="picture-card"
                 [(nzFileList)]="fileList"
                 [nzMultiple]="true"
                 [nzLimit]="5"
                 [nzShowButton]="fileList.length < 5"
                 (nzChange)="change($event)"
                 (nzFileListChange)="changeFileList()"
      >
        <i class="anticon anticon-plus"></i>
        <div class="ant-upload-text">Upload</div>
      </nz-upload>
      <nz-modal [nzVisible]="_previewVisible" [nzContent]="modalContent" [nzFooter]="null"
                (nzOnCancel)="_previewVisible=false">
        <ng-template #modalContent>
          <img [src]="_previewImage" [ngStyle]="{ 'width': '100%' }"/>
        </ng-template>
      </nz-modal>
    </div>
  `,
  styles: [
      `
      :host ::ng-deep i {
        font-size: 32px;
        color: #999;
      }

      :host ::ng-deep .ant-upload-text {
        margin-top: 8px;
        color: #666;
      }
    `
  ]
})
export class DynamicUploadComponent implements OnChanges {
  /**
   * 文件list
   * @type {any[]}
   */
  @Input()
  fileList = [];
  /**
   * 判断是否多张图片 默认单张
   * @type {boolean}
   */
  @Input()
  multi = false;

  /*/!**
   * ！！将删除      判断是否多张图片 默认单张
   * @type {boolean}
   *!/
  @Output()
  onFileUploaded: EventEmitter<any> = new EventEmitter();*/
  /**
   * fileList Change
   * @type {EventEmitter<any>}
   */
  @Output()
  onFileListChange: EventEmitter<any> = new EventEmitter();

  _uploadUrl = environment.SERVER_URL + "/attachment/upload"

  _previewImage = '';
  _previewVisible = false;


  constructor(private msg: NzMessageService) {
  }

  handlePreview = (file: UploadFile) => {
    this._previewImage = file.url || file.thumbUrl;
    this._previewVisible = true;
  }

  change(event) {
    if (event.type == 'success') {
      // failure
      if (event.file.response.result == 'FAILURE') {
        // this.fileList.splice(this.fileList.filter(e=>e.))
        event.status='error'
        let filter = this.fileList.filter(e=>e.name==event.file.name);
        if (filter.length != 0) {
          filter[0].status='error'
          this.msg.error(filter[0].response.message+"图片上传失败,请更换图片")
        }
      } else {
        this.changeFileList()
      }
      // this.onFileUploaded.emit(event.file.response)
      // this.onFileListChange.emit(this.fileList)
    }
  }

  changeFileList() {
    if (this.fileList.every(e => e.status == 'done' || e.status == 'edit')) {
      // console.log('onFileListChange');
      //console.log(this.fileList);
      this.onFileListChange.emit(this.fileList)
    }
    if (this.fileList.length == 0) {
      this.onFileListChange.emit([])
      this.fileList = []
    }
  }

  ngOnChanges(value): void {
    if (this.fileList.length == 0) {
      this.fileList = []
    } else {
      this.fileList.forEach((e, i) => {
        e.uid = i
      })
    }
  }

}
