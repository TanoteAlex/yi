import {Component, EventEmitter, Input, Output, ViewEncapsulation} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {environment} from '@env/environment';
import {NzMessageService} from 'ng-zorro-antd';
import {AttachmentService} from "../../services/attachment.service";
import {PageQuery} from "../../models/page-query.model";

declare var $: any;

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.less'],
  encapsulation: ViewEncapsulation.None
})
export class EditorComponent {
  @Input()
  ckeditorContent: any;
  @Input()
  height = 300;
  @Output()
  result = new EventEmitter

  @Output()
  attachmentVos = new EventEmitter

  _attachmentVos = []

  loading = true;
  pageQuery = new PageQuery()
  imgs = [];
  radioValue = '';
  _isDesc = false;
  isVisible = false;
  showButton = true;
  _searchValue = '';

  showModal(): void {
    this.isVisible = true;
  }

  _findByName() {
    this.pageQuery.addOnlyFilter('description', this._searchValue, 'contains')
    this.pageIndexChange()
  }

  _changeSort() {
    this._isDesc = !this._isDesc
    this.pageQuery.requests.sort = []
    this.pageQuery.addSort(this.radioValue == 'name' ? 'description' : 'createTime', this._isDesc ? 'descend' : 'asc')
    this.pageIndexChange()
  }

  _insertImg(url) {
    $('#summernote1').summernote('insertImage', url);
  }

  _changeMenu(categoryMenu) {
    this.categoryMenus.forEach(e => e.click = false);
    categoryMenu.click = true;
    this.pageQuery.removeByNameFilter('description');
    this._searchValue = '';
    this.pageQuery.addOnlyFilter('objectType', categoryMenu.value, 'eq');
    this.pageQuery.requests.page = 1
    this.pageIndexChange()

  }


  uploadImg() {
    let _this = this;
    let baseUrl = environment.SERVER_URL;
    let editorUploadImgInput: any = document.getElementById('editorUploadImgInput');
    editorUploadImgInput.click();
    editorUploadImgInput.onchange = function (e: any) {
      _this.sendFile(editorUploadImgInput.files, baseUrl, _this.msgSrv, _this);
      _this.isVisible = false;
      _this.imgs.forEach(e => e.click = false)
    }


  }

  handleOk(): void {
    this.isVisible = false;
    let filter = this.imgs.filter(e => e.click);
    this._attachmentVos.push(...filter)
    filter.forEach(e => {
      this._insertImg(e.imgPath)
      e.click = false;
    })
    this.attachmentVos.emit(this._attachmentVos);
  }

  handleCancel(): void {
    this.isVisible = false;
  }


  categoryMenus = [
    {click: true, label: '全部', value: ''},
    {click: false, label: '商品', value: 'COMMODITY'},
    {click: false, label: '商品评论', value: 'COMMODITY_COMMENT'},
    {click: false, label: '品牌', value: 'BRAND'},
    {click: false, label: '内容管理', value: 'BANNER'},
    // {click:false,label: '部门', value: 'DEPT'},
    // {click:false,label: '用户', value: 'USER'},
    {click: false, label: '退货单', value: 'RETURN_ORDER'},
    {click: false, label: '退款单', value: 'REFUND_ORDER'},
    {click: false, label: '推荐位', value: 'RECOMMEND'},
    {click: false, label: '广告位', value: 'ADVERTISEMENT'},
    {click: false, label: '爱生活', value: 'ARTICLE'},
    {click: false, label: '爱生活评论', value: 'ARTICLE_COMMENT'},
    {click: false, label: '商品详情', value: 'COMMODITY_DETAIL'},
    {click: false, label: '会员', value: 'MEMBER'},
  ]


  clickImg(i) {
    // this.imgs.forEach(e=>e.click=false)
    this.imgs[i].click = !this.imgs[i].click
  }

  constructor(public sanitizer: DomSanitizer, public msgSrv: NzMessageService, public attachmentService: AttachmentService) {
    this.pageQuery.requests.pageSize = 12;

  }

  pageIndexChange() {
    this.loading = true;
    this.attachmentService.query(this.pageQuery).subscribe(data => {
      this.loading = false;
      this.pageQuery.covertResponses(data);
      this.imgs = data.content.map(e => {
        return {name: e.description, imgPath: e.filePath, id: e.attachId, orgin: e}
      })
      // console.log(data.content);
    })
  }

  ngAfterViewInit() {
    let _this = this
    let baseUrl = environment.SERVER_URL
    $(document).ready(function () {
      var photos = function (context) {
        var ui = $.summernote.ui;

        var button = ui.button({
          container: 'body',
          contents: '<i class="note-icon-picture"/>',  //按钮样式
          tooltip: '图片库',
          click: function () {
            //console.log("lem");
            _this.isVisible = true;
          },
        });

        return button.render();    //按钮渲染
      }
      $('#summernote1').summernote({
        lang: "zh-CN",
        height: _this.height,
        toolbar: [
          ['style', ['style']],
          ['font', ['bold', 'underline', 'clear']],
          ['fontname', ['fontname']],
          ['color', ['color']],
          ['insert', ['link', 'cuPhotos', 'video']],
          // ['insert', ['link', 'picture', 'video']],
          ['view', ['fullscreen', 'codeview', 'help']],
        ],
        popover: [],
        buttons: {
          cuPhotos: photos      //自已定义的按钮函数
        },
        callbacks: {
          onImageUpload: function (files, editor, $editable) {
            _this.sendFile(files, baseUrl, _this.msgSrv, _this);
          },
          onBlur: function () {
            _this.saveAndPush();
          }
        }
      });
    });
    //console.log(_this);
    this.pageIndexChange()
  }

  saveAndPush() {
    this.ckeditorContent = this.sanitizer.bypassSecurityTrustHtml($('#summernote1').summernote('code'));
    this.result.emit(this.ckeditorContent.changingThisBreaksApplicationSecurity)
  }

  sendFile(files, baseUrl, msgSrv, _this) {
    var formData = new FormData();
    if (files && files.length > 0) {
      for (var i = 0; i < files.length; i++) {
        formData.append("img" + i, files[i]);
      }
    }
    $.ajax({
      data: formData,
      type: "POST",
      url: baseUrl + "/attachment/upload", //图片上传出来的url，返回的是图片上传后的路径，http格式
      cache: false,
      contentType: false,
      processData: false,
      dataType: "json",
      success: async function (data) {//data是返回的hash,key之类的值，key是定义的文件名
        if ("SUCCESS" === data.result) {
          _this._attachmentVos.push(...data.data)
          if (data.data && data.data.length > 0) {
            for (var i = 0; i < files.length; i++) {
              try {
                //todo 加载遮罩loading
                await $('#summernote1').summernote('insertImage', data.data[i].url + "_750_650_m");
              } catch (e) {
                msgSrv.error('图片找不到');
              }
            }
          }
          _this.attachmentVos.emit(_this._attachmentVos);
        }
      },
      error: function () {
        msgSrv.error('图片上传失败');
      }
    });
  }

  setContent(content: string) {
    $('#summernote1').summernote('code', content);
  }


}
