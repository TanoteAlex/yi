function isBlank(str: string) {
  return str === null || str === undefined || "" == str;
}

function isNotBlank(str: string) {
  return !isBlank(str);
}

function isString(str) {
  return (typeof str === 'string') && str.constructor == String;
}


function isEmpty(val) {
  if (val === null || val === undefined || val === "") {
    return true;
  }

  return false;
}

function trim(val: string) {
  if (isEmpty(val)) {
    return val;
  }

  val = val.replace(/(^\s*)|(\s*$)/g, '');
  return val;
}

function trimToNull(val: string) {
  if (isEmpty(val)) {
    return null;
  }

  const result = trim(val);
  return isEmpty(result) ? null : result;
}

function capitalize(str: string) {
  if (isEmpty(str)) {
    return str;
  }

  let ret = str.substring(0, 1).toUpperCase() + str.substring(1, str.length);
  return ret;
}

export const StringUtils = {
  isBlank,
  isNotBlank,
  isString,
  isEmpty,
  trim,
  trimToNull,
  capitalize
};
