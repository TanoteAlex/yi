import {AbstractControl, ValidationErrors} from "@angular/forms";

/**
 * 返回errors告诉控件报错的有那些 为null代表没错误
 * return null 代表没错误 ，{freightTemplateRequire: {}} 会让他去找formError里对应FormControl的 freightTemplateRequire
 */

 const failure = "FAILURE";

/**
 * 自定义商品运费模版校验
 * @param {AbstractControl} freightSetControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function freightTemplateValidator(freightSetControl: AbstractControl) {

  //根据freightSetControl的值在提交时校验 freightSetControl.value == 2 时  freightTemplate必填
  return (control: AbstractControl): ValidationErrors | null => {
    const freightTemplate = freightSetControl.value == 2 ? !!control.value : true;
    return freightTemplate ? null : {freightTemplateRequire: {}};
  };
}

/**
 * 自定义链接商品表单
 * @param {AbstractControl} linkTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function commodityValidator(linkTypeControl: AbstractControl) {

  //根据linkTypeControl的值在提交时校验 linkTypeControl.value == 1 时  commodity必填
  return (control: AbstractControl): ValidationErrors | null => {
    const commodity = linkTypeControl.value == 1 ? !!control.value : true;
    return commodity ? null : {commodityRequire: {}};
  };
}

/**
 * 自定义链接商品表单
 * @param {AbstractControl} linkTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function productValidator(linkTypeControl: AbstractControl) {

  //根据linkTypeControl的值在提交时校验 linkTypeControl.value == 1 时  product必填
  return (control: AbstractControl): ValidationErrors | null => {
    const product = linkTypeControl.value == 1 ? !!control.value : true;
    return product ? null : {productRequire: {}};
  };
}

/**
 * 自定义链接文章表单
 * @param {AbstractControl} linkTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function articleValidator(linkTypeControl: AbstractControl) {

  //根据linkTypeControl的值在提交时校验 linkTypeControl.value == 2 时  article必填
  return (control: AbstractControl): ValidationErrors | null => {
    const article = linkTypeControl.value == 2 ? !!control.value : true;
    return article ? null : {articleRequire: {}};
  };
}

/**
 * 自定义时间表单
 * @param {AbstractControl} validTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function timeValidator(validTypeControl: AbstractControl) {

  //根据validTypeControl的值在提交时校验 validTypeControl.value == 1 时  startTime必填
  return (control: AbstractControl): ValidationErrors | null => {
    const startTime = validTypeControl.value == 1 ? !!control.value : true;
    return startTime ? null : {timeRequire: {}};
  };
}

/**
 * 自定义固定天数表单
 * @param {AbstractControl} validTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function fixedDayValidator(validTypeControl: AbstractControl) {

  //根据validTypeControl的值在提交时校验 validTypeControl.value == 2 时  fixedDay必填
  return (control: AbstractControl): ValidationErrors | null => {
    const fixedDay = validTypeControl.value == 2 ? !!control.value : true;
    return fixedDay ? null : {fixedDayRequire: {}};
  };
}

/**
 * 自定义价格表单
 * @param {AbstractControl} useConditionTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function fullMoneyValidator(useConditionTypeControl: AbstractControl) {

  //根据useConditionTypeControl的值在提交时校验 useConditionTypeControl.value == 1 时  fullMoney必填
  return (control: AbstractControl): ValidationErrors | null => {
    const fullMoney = useConditionTypeControl.value == 1 ? !!control.value : true;
    return fullMoney ? null : {moneyRequire: {}};
  };
}

/**
 * 自定义件数表单
 * @param {AbstractControl} useConditionTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function fullQuantityValidator(useConditionTypeControl: AbstractControl) {

  //根据useConditionTypeControl的值在提交时校验 useConditionTypeControl.value == 2 时  fullQuantity必填
  return (control: AbstractControl): ValidationErrors | null => {
    const fullQuantity = useConditionTypeControl.value == 2 ? !!control.value : true;
    return fullQuantity ? null : {quantityRequire: {}};
  };
}

/**
 * 自定义优惠卷表单
 * @param {AbstractControl} rewardTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function couponValidator(rewardTypeControl: AbstractControl) {

  //根据rewardTypeControl的值在提交时校验 rewardTypeControl.value == 2 时  coupon
  return (control: AbstractControl): ValidationErrors | null => {
    const coupon = rewardTypeControl.value == 2 ? !!control.value : true;
    return coupon ? null : {couponRequired: {}};
  };
}

/**
 * 自定义会员等级表单
 * @param {AbstractControl} memberTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function memberLevelsValidator(memberTypeControl: AbstractControl) {

  //根据rewardTypeControl的值在提交时校验 rewardTypeControl.value == 2 时  coupon
  return (control: AbstractControl): ValidationErrors | null => {
    const memberLevels = memberTypeControl.value == 2 ? !!control.value : true;
    return memberLevels ? null : {memberLevelsRequired: {}};
  };
}

/**
 * 自定义链接商品表单
 * @param {AbstractControl} prizeTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function prizeTypeIntegralValidator(prizeTypeControl: AbstractControl) {

  //根据prizeTypeControl的值在提交时校验 prizeTypeControl.value == 1 时  integral
  return (control: AbstractControl): ValidationErrors | null => {
    const ele = prizeTypeControl.value == 1 ? !!control.value : true;
    return ele ? null : {integralRequire: {}};
  };
}

/**
 * 自定义链接文章表单
 * @param {AbstractControl} prizeTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function prizeTypeCommodityValidator(prizeTypeControl: AbstractControl) {

  //根据prizeTypeControl的值在提交时校验 linkTypeControl.value == 2 时  article必填
  return (control: AbstractControl): ValidationErrors | null => {
    const ele = prizeTypeControl.value == 2 ? !!control.value : true;
    return ele ? null : {commodityRequire: {}};
  };
}

/**
 * 自定义链接文章表单
 * @param {AbstractControl} prizeTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function prizeTypeProductValidator(prizeTypeControl: AbstractControl) {

  //根据prizeTypeControl的值在提交时校验 linkTypeControl.value == 2 时  article必填
  return (control: AbstractControl): ValidationErrors | null => {
    const ele = prizeTypeControl.value == 2 ? !!control.value : true;
    return ele ? null : {productRequire: {}};
  };
}


/**
 * 自定义链接文章表单
 * @param {AbstractControl} prizeTypeControl
 * @returns {(control: AbstractControl) => (ValidationErrors | null)}
 */
export function prizeTypeCouponValidator(prizeTypeControl: AbstractControl) {

  //根据linkTypeControl的值在提交时校验 linkTypeControl.value == 2 时  article必填
  return (control: AbstractControl): ValidationErrors | null => {
    const ele = prizeTypeControl.value == 3 ? !!control.value : true;
    return ele ? null : {couponRequire: {}};
  };
}

