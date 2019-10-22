import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NzMessageService, UploadFile} from 'ng-zorro-antd';
import {environment} from "@env/environment";

@Component({
  selector: 'app-upload',
  template: `
    <div class="clearfix">
      <nz-upload *ngIf="!pictures"
                 nzAction="{{uploadUrl}}"
                 nzListType="picture-card"
                 [(nzFileList)]="fileList"
                 [nzShowButton]="fileList.length < 1"
                 (nzChange)="change($event)"
                 [nzPreview]="handlePreview"
                 [nzRemove]="eliminate">
        <i class="anticon anticon-plus"></i>
        <div class="ant-upload-text">Upload</div>
      </nz-upload>
      <nz-upload *ngIf="pictures"
                 nzAction="{{uploadUrl}}"
                 nzListType="picture-card"
                 [(nzFileList)]="fileList"
                 [nzMultiple]="true"
                 [nzLimit]="5"
                 [nzShowButton]="fileList.length < 5"
                 (nzChange)="change($event)"
                 [nzPreview]="handlePreview"
                 [nzRemove]="eliminate">
        <i class="anticon anticon-plus"></i>
        <div class="ant-upload-text">Upload</div>
      </nz-upload>
      <nz-modal [nzVisible]="previewVisible" [nzContent]="modalContent" [nzFooter]="null"
                (nzOnCancel)="previewVisible=false">
        <ng-template #modalContent>
          <img [src]="previewImage" [ngStyle]="{ 'width': '100%' }"/>
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
export class UploadComponent {
  @Input()
  fileList = [];
  /**
   * 判断是否多张图片 默认单张
   * @type {boolean}
   */
  @Input()
  pictures = false;

  @Output()
  onFileUploaded: EventEmitter<any> = new EventEmitter();

  @Input()
  uploadUrl = environment.SERVER_URL + "/attachment/upload"

  @Input()
  allowedFileType: Array<String> = ['application', 'image', 'video', 'audio', 'pdf', 'compress', 'doc', 'xls', 'ppt'];
  previewImage = '';
  previewVisible = false;

  constructor(private msg: NzMessageService) {
  }

  ngAfterViewInit() {
    this.fileList = [];
  }

  private genFileUploaderOptions(): any {
    return {
      url: "uploadUrl",
      method: "POST",
      autoUpload: true,
      maxFileSize: 20 * 1024 * 1024,
      queueLimit: 5,
      removeAfterUpload: true,
      // headers: [
      //     {name: "token", value: this.appStorage.loginData.token}
      // ],
      allowedFileType: this.allowedFileType
    };
  }

  handlePreview = (file: UploadFile) => {
    this.previewImage = file.url || file.thumbUrl;
    this.previewVisible = true;
  }

  eliminate = (file: UploadFile) => {
    this.previewImage = file.url || file.thumbUrl;
    if (!this.pictures) {
      this.onFileUploaded.emit('failure')
      return this.previewImage = file.url || file.thumbUrl;
    } else {
      this.fileList = this.fileList.filter(e => file.uid != e.uid || e.url != file.url)
      this.onFileUploaded.emit(this.fileList)
    }
  }


  change(event) {
    if (!this.pictures) {
      if (event.type == 'success') {
        this.onFileUploaded.emit(event.file.response)
      }
    }
    if (this.pictures) {
      this.fileList = event.fileList;
      this.onFileUploaded.emit(this.fileList)
    }
  }

}
