
/**
 * copy an array without change the original link
 * @param {Array} oldArr
 * @param {Array} newArr
 */
function arrayCopy(oldArr = [], newArr = []) {
  oldArr.length = 0;

  newArr.forEach(x => {
    oldArr.push(x);
  });

  return oldArr;
}

/**
 * @hidden
 * Given a min and max, restrict the given number
 * to the range.
 * @param {?} min the minimum
 * @param {?} n the value
 * @param {?} max the maximum
 * @return {?}
 */
function clamp(min, n, max) {
  return Math.max(min, Math.min(n, max));
}

/**
 * @hidden
 * @param {?} obj
 * @return {?}
 */
function deepCopy(obj) {
  return JSON.parse(JSON.stringify(obj));
}

/**
 * @hidden
 * @param {?} a
 * @param {?} b
 * @return {?}
 */
function deepEqual(a, b) {
  if (a === b) {
    return true;
  }
  return JSON.stringify(a) === JSON.stringify(b);
}

/**
 * @hidden
 * @param {?} fn
 * @param {?} wait
 * @param {?=} immediate
 * @return {?}
 */
function debounce(fn, wait, immediate) {
  if (immediate === void 0) {
    immediate = false;
  }
  let /** @type {?} */ timeout, /** @type {?} */ args, /** @type {?} */ context, /** @type {?} */ timestamp,
    /** @type {?} */ result;
  return function () {
    context = this;
    args = arguments;
    timestamp = Date.now();
    let /** @type {?} */ later = function () {
      let /** @type {?} */ last = Date.now() - timestamp;
      if (last < wait) {
        timeout = setTimeout(later, wait - last);
      }
      else {
        timeout = null;
        if (!immediate)
          result = fn.apply(context, args);
      }
    };
    let /** @type {?} */ callNow = immediate && !timeout;
    if (!timeout) {
      timeout = setTimeout(later, wait);
    }
    if (callNow)
      result = fn.apply(context, args);
    return result;
  };
}

/**
 * @hidden
 * Apply default arguments if they don't exist in
 * the first object.
 * @param {?} dest
 * @param {...?} _args
 * @return {?}
 */
function defaults(dest) {
  let _args = [];
  for (let _i = 1; _i < arguments.length; _i++) {
    _args[_i - 1] = arguments[_i];
  }
  for (let /** @type {?} */ i = arguments.length - 1; i >= 1; i--) {
    let /** @type {?} */ source = arguments[i];
    if (source) {
      for (let /** @type {?} */ key in source) {
        if (source.hasOwnProperty(key) && !dest.hasOwnProperty(key)) {
          dest[key] = source[key];
        }
      }
    }
  }
  return dest;
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isBoolean(val) {
  return typeof val === 'boolean';
}

function isDate(val) {
  return Object.prototype.toString.call(val) === '[object Date]';
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isString(val) {
  return typeof val === 'string';
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isNumber(val) {
  return typeof val === 'number';
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isFunction(val) {
  return typeof val === 'function';
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isDefined(val) {
  return typeof val !== 'undefined';
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isUndefined(val) {
  return typeof val === 'undefined';
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isPresent(val) {
  return val !== undefined && val !== null;
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isBlank(val) {
  return val === undefined || val === null || (isString(val) && val.trim() === "");
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isObject(val) {
  return typeof val === 'object';
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */

function isArray(val) {
  return Array.isArray(val);
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isPrimitive(val) {
  return isString(val) || isBoolean(val) || (isNumber(val) && !isNaN(val));
}

/**
 * @hidden
 * @param {?} val
 * @return {?}
 */
function isTrueProperty(val) {
  if (typeof val === 'string') {
    val = val.toLowerCase().trim();
    return (val === 'true' || val === 'on' || val === '');
  }
  return !!val;
}

/**
 * 检查是否没有任何属性的对象, 如果 undefined, null 时返回 true
 * @param val
 * @returns {boolean}
 */
function isEmptyObject(val) {
  if (val == null) return true;

  for (var prop in val) {
    if (val.hasOwnProperty(prop)) {
      return false;
    }
  }

  return true;
}

/**
 * 检查是否为null, 空字符串, 空数组, 空对象
 * @param val
 * @returns {boolean | arg is Array<any>}
 */
function isEmpty(val) {
  return val === undefined || val === null
    || (isArray(val) && val.length === 0)
    || (isString(val) && val.trim() === "")
    || (isObject(val) && isEmptyObject(val));
}

/**
 * 检查是否为非null, 非空字符串, 非空数组, 非空对象
 * @param val
 * @returns {boolean | arg is Array<any>}
 */
function isNotEmpty(val) {
  return !isEmpty(val);
}

function getLength(object) {
  var count = 0;
  for (var i in object) count++;
  return count;
}

function compare(objA, objB) {
  if (!isObject(objA) || !isObject(objB)) return false; //判断类型是否正确
  if (getLength(objA) != getLength(objB)) return false; //判断长度是否一致
  let result = compareObj(objA, objB, true);//默认为true
  if (result) {
    return objA
  } else {
    return null;
  }
}

function compareObj(objA, objB, flag) {
  for (var key in objA) {
    if (!flag) //跳出整个循环
      break;
    if (!objB.hasOwnProperty(key)) {
      flag = false;
      break;
    }
    if (!isArray(objA[key])) { //子级不是数组时,比较属性值
      if (objB[key] != objA[key]) {
        flag = false;
        break;
      }
    } else {
      if (!isArray(objB[key])) {
        flag = false;
        break;
      }
      var oA = objA[key], oB = objB[key];
      if (oA.length != oB.length) {
        flag = false;
        break;
      }
      for (var k in oA) {
        if (!flag) //这里跳出循环是为了不让递归继续
          break;
        flag = compareObj(oA[k], oB[k], flag);
      }
    }
  }
  return flag;
}

function compareByKey(objA, objB, key) {
  if (isEmpty(objA) || isEmpty(objB)) {
    return false;
  }
  return objA && objB ? objA[key] === objB[key] : objA === objB;
}

/**
 * converts an object to x-www-form-urlencoded serialization.
 * http://victorblog.com/2012/12/20/make-angularjs-http-service-behave-like-jquery-ajax/
 * @param {Object} obj
 * @return {String}
 */
function encodeFormData(obj) {
  if (obj == null || isString(obj)) {
    return obj;
  }

  let query = '';
  let name, value, innerObj;

  for (name in obj) {
    if (!obj.hasOwnProperty(name)) continue;
    value = obj[name];

    if (isArray(value)) {
      // 数组数据时, 重复添加数据到query字符串, 比如 name=111&name=222&name=333
      for (let i = 0; i < value.length; ++i) {
        // fullSubName = name + '[' + i + ']';
        innerObj = {};
        innerObj[name] = value[i];
        query += encodeFormData(innerObj) + '&';
      }

    } else if (isDate(value)) {
      // moment().toISOString() // 2013-02-04T22:44:30.652Z
      query += encodeURIComponent(name) + '=' + new Date(value).toISOString() + '&';

    } else if (isObject(value)) {
      // 对象数据时, 直接转换成JSON字符串, 需要后台自己解析
      query += encodeURIComponent(name) + '=' + encodeURIComponent(JSON.stringify(value)) + '&';

      // for (subName in value) {
      //   subValue = value[subName];
      //   fullSubName = name + '[' + subName + ']';
      //   innerObj = {};
      //   innerObj[fullSubName] = subValue;
      //   query += encodeFormData(innerObj) + '&';
      // }

    } else if (value !== undefined && value !== null) {
      query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';

    } else {
      query += encodeURIComponent(name) + '=&';
    }
  }

  return query.length ? query.substr(0, query.length - 1) : query;
}

function checkValidated(form) {
  if (!form) {
    return;
  }
  for (let c in form.controls) {
    let cForm = form.controls[c];
    cForm.markAsTouched();
    if (cForm.controls) {
      this.checkValidated(cForm);
    }
  }
  return form.valid;
}

export const ObjectUtils = {
  arrayCopy,
  deepCopy,
  deepEqual,
  clamp,
  debounce,
  defaults,
  isString,
  isBoolean,
  isFunction,
  isDefined,
  isUndefined,
  isPresent,
  isBlank,
  isDate,
  isObject,
  isArray,
  isPrimitive,
  isTrueProperty,
  isEmptyObject,
  isEmpty,
  isNotEmpty,
  encodeFormData,
  compare,
  compareByKey,
  checkValidated
};
