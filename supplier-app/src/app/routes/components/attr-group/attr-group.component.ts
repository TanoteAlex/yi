import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-attr-group',
  templateUrl: './attr-group.component.html',
  styles: []
})
export class AttrGroupComponent {
  // _selectAttrGroup单个对象数据模型
  // {
  //     label: e.groupName, value: e.id, checked: false, _canAdd: false, _newAttrV: '', attributes: e.attributes.map(e1 => {
  //       return {label: e1.attrValue, value: e1.id, checked: false}
  //     }), checkAll: false, indeterminate: true
  //   }

  attrGroupTip = []

  selectGroups = []

  @Output()
  result: EventEmitter<any> = new EventEmitter<any>();

  _checkOptions = []

  _selectAttrGroup = []

  _newAttrG = ''
  _newAttrGShowMode = '1'
  _newAttrGImgPath = ''
  /**/
  imgPaths = ''
  imgPaths1 = ''

  changeAttachment(fileList, isItem?, index?) {
    let newAttachmentVos = fileList.map(e => {
      if (e.status == 'edit') {
        return e.oldValue
      } else {
        return e.response ? e.response.data[0] : null
      }
    })
    if (isItem) {
      this._selectAttrGroup[index]._newAttrVImgPath = newAttachmentVos[0].url ? newAttachmentVos[0].url : newAttachmentVos[0].filePath
    } else {
      this._newAttrGImgPath = newAttachmentVos[0].url ? newAttachmentVos[0].url : newAttachmentVos[0].filePath

    }
  }

  /**/
  _canAddAttrG = false

  allChecked = false;
  indeterminate = true;
  _attrGError = ''

  _attrGFormError = ''

  setSelect(attrGroupTip, selectGroups) {
    //console.log(attrGroupTip);
    // //监听父组件属性组显示传值变化
    this.attrGroupTip = attrGroupTip
    this._checkOptions = this.attrGroupTip.map(e => {
      return {
        label: e.groupName,
        value: e.id,
        checked: false,
        _canAdd: false,
        _newAttrV: '',
        attributes: e.attributes.map(e1 => {
          return {label: e1.attrValue, value: e1.id, checked: false}
        }),
        checkAll: false,
        indeterminate: true
      }
    });
    this._selectAttrGroup = []
    this.allChecked = false;
    this.indeterminate = true;
    this.updateAllChecked()

    // console.log(selectGroups);
    //监听父组件已选属性组传值变化
    this._checkOptions.forEach((e, i) => {
      if (selectGroups.length != 0) {
        selectGroups.forEach(e1 => {
          if (e.value == e1.id) {
            e.checked = true;
            if (e.attributes.length != 0) {
              e.attributes.forEach((e2, i2) => {
                e1.attributes.forEach(e3 => {
                  if (e2.value == e3.id) {
                    this._checkOptions[i].attributes[i2].checked = true;
                  }
                })
              })
            }
          }
        })
      }
    })
    // console.log(this._checkOptions);
    this.updateSingleChecked()
  }

  /**
   * 更新属性组全选状态
   */
  updateAllChecked(): void {
    this.indeterminate = false;
    if (this.allChecked) {
      this._checkOptions.forEach(item => item.checked = true);
    } else {
      this._checkOptions.forEach(item => item.checked = false);
    }
    this.updateSingleChecked()
  }

  /**
   * 更新单个属性组状态
   */
  updateSingleChecked(): void {
    if (this._checkOptions.every(item => item.checked === false)) {
      this.allChecked = false;
      this.indeterminate = false;
    } else if (this._checkOptions.every(item => item.checked === true)) {
      this.allChecked = true;
      this.indeterminate = false;
    } else {
      this.indeterminate = true;
    }
    this._selectAttrGroup = this._checkOptions.filter(e => e.checked)
    this._selectAttrGroup.forEach((e, i) => {
      this._selectAttrGroup[i].indeterminate = e.attributes.length != 0 ? e.attributes.some(item => item.checked === true) : false
    })
    this.transSelectAttrGroup()
  }

  /**
   * 更新属性值全选状态
   * @param checkAll
   * @param attributes
   * @param indeterminate
   * @param i
   */
  updateAttrValueAllChecked(checkAll, attributes, indeterminate, i): void {
    this._selectAttrGroup[i].indeterminate = false;
    if (checkAll) {
      attributes.forEach(item => item.checked = true);
    } else {
      attributes.forEach(item => item.checked = false);
    }
    this.transSelectAttrGroup()
  }

  /**
   * 更新单个属性值状态
   */
  updateAttrValueSingleChecked(checkAll, attributes, indeterminate, i) {
    if (attributes.length != 0) {
      if (attributes.every(item => item.checked === false)) {
        this._selectAttrGroup[i].checkAll = false;
        this._selectAttrGroup[i].indeterminate = false;
      } else if (attributes.every(item => item.checked === true)) {
        this._selectAttrGroup[i].checkAll = true;
        this._selectAttrGroup[i].indeterminate = false;
      } else {
        this._selectAttrGroup[i].indeterminate = true;
      }
      this.transSelectAttrGroup()
    }
  }

  /**
   * 转换数据并提交给父组件
   */
  transSelectAttrGroup() {
    let result = this._selectAttrGroup.filter(e3 => e3.checked).map(e => {
      return {
        imgPath: e.imgPath,
        showMode: e.showMode,
        id: e.value,
        groupName: e.label,
        attributes: e.attributes.filter(e2 => e2.checked).map(e1 => {
          return {imgPath: e1.imgPath, showMode: e1.showMode, id: e1.value, attrName: e.label, attrValue: e1.label,}
        })
      }
    })
    // console.log(`i am attrg result`, result);
    if (result.every(e => e.attributes.length != 0)) {
      this._attrGFormError = ''
      this.result.emit(result)
    } else {
      this._attrGFormError = '已选属性组必须选择属性值'

    }
    // console.log(this._selectAttrGroup);
  }

  addAttrG() {
    this._canAddAttrG = true
  }

  cancelAddAttrG() {
    this._canAddAttrG = false;
  }

  addAttrV(i) {
    this._selectAttrGroup[i]._canAdd = true
  }

  cancelAddAttrV(i) {
    this._selectAttrGroup[i]._canAdd = false
  }

  sureAddAttrG() {
    if (this._newAttrG != "") {
      if (this._checkOptions.every(e => e.label != this._newAttrG)) {
        this._canAddAttrG = false
        this._checkOptions.push({
          imgPath: this._newAttrGImgPath,
          showMode: this._newAttrGShowMode,
          label: this._newAttrG,
          value: 0,
          checked: false,
          _canAdd: false,
          _newAttrV: '',
          _newAttrVImgPath: '',
          attributes: []
        })
        this._newAttrG = ''
        this._newAttrGShowMode = '1'
        this._newAttrGImgPath = ''
        this.updateSingleChecked();
        this._attrGError = ''
      } else {
        this._attrGError = "名字不能重复"
      }
    } else {
      this._attrGError = "名字不能为空"
    }
  }

  sureAddAttrV(i) {
    let attrGroup = this._selectAttrGroup[i];
    if (attrGroup._newAttrV != "") {
      if (attrGroup.attributes.every(e => e.label != attrGroup._newAttrV)) {
        attrGroup._canAdd = false
        attrGroup.attributes.push({imgPath:attrGroup._newAttrVImgPath,label: attrGroup._newAttrV, value: 0, checked: false})
        attrGroup._newAttrV = ''
        attrGroup._attrVError = ''
        this.updateAttrValueSingleChecked(attrGroup.checkAll, attrGroup.attributes, attrGroup.indeterminate, i);
      } else {
        attrGroup._attrVError = '名字不能重复'
        // console.log("名字不能重复");
      }
    } else {
      attrGroup._attrVError = '名字不能为空'
    }
  }
}
