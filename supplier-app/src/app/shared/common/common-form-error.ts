export const COMMODITY_FORM_ERRORS = {
  attrValue: [
    {
      name: 'required',
      msg: '不可为空',
    },
  ],
  groupName: [
    {
      name: 'required',
      msg: '不可为空',
    },
  ],
  commodityNo: [
    {
      name: 'maxlength',
      msg: '最大32位长度',
    }
  ],
  commodityName: [
    {
      name: 'required',
      msg: '不可为空',
    },
    {
      name: 'maxlength',
      msg: '最大64位长度',
    }
  ],
  supplier: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  commodityShortName: [
    {
      name: 'required',
      msg: '不可为空',
    },
    {
      name: 'maxlength',
      msg: '最大64位长度',
    }
  ],
  imgPath: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  sort: [],
  distribution: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  integralRate: [
    {
      name: 'required',
      msg: '不可为空',
    },
    {
      name: 'maxlength',
      msg: '最大4位长度',
    }
  ],
  freightSet: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  unifiedFreight: [
    {
      name: 'maxlength',
      msg: '最大15位长度',
    }
  ],
  freightTemplate: [
    {
      name: 'freightTemplateRequire',
      msg: '请选择运费模版',
    }
  ],
  stockSet: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  shelfState: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  volume: [
    {
      name: '',
      msg: '',
    }
  ],
  weight: [
    {
      name: '',
      msg: '',
    }
  ],
  description: [
    {
      name: 'maxlength',
      msg: '最大65535位长度',
    }
  ],
  attributeGroups: [{
    name: 'required',
    msg: '不可为空',
  }
  ],
  attrGroup: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  regions: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  attachmentVos: [],
  category: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ],
  operateCategories: [],
  commodityLevelDiscount: [],
  commodityType: [
    {
      name: 'required',
      msg: '不可为空',
    }
  ]
}
export const REGION_FORM_ERRORS={}
