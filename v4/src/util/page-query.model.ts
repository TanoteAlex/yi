/**
 * 分页参数
 */

export class PageRequest {
    page: number = 1;
    pageSize: number = 10;
    take: number = 0;
    skip: number = 0;

    group: any;
    filter: any;
    sort: any;

    /**
     * 个性化参数
     */
    data: any;

    /**
     * 个性化条件, 比如任务查询时的时间范围
     */
    params: { [key: string]: any; } = {};

    clear() {
        this.resetPage();
        this.params = {};
    }

    resetPage() {
        this.page = 1;
        this.take = 0;
        this.skip = 0;
    }

    pushParams(key, value) {
        if (this.params == null) this.params = {};

        this.params[key] = value;
    }

    clearParams() {
        this.params = {};
    }
}

/**
 * 分页返回的参数
 */
export class PageResponse {
    first: boolean = false;
    last: boolean = true;
    number: number = 1;
    numberOfElements: number = 0;
    size: number = 15;
    sort: any;
    totalElements: number = 0;
    totalPages: number = 1;
}

/**
 * 基础分页查询参数和结果对象
 */
export abstract class AbstractPageQuery<REQ extends PageRequest, RES extends PageResponse> {
    readonly requests: REQ;
    readonly responses: RES;

    constructor(requests: REQ, responses: RES) {
        this.requests = requests;
        this.responses = responses;
    }

    get hasMoreData() {
        return !this.responses.last;
    }

    get page() {
        return this.requests.page;
    }

    set page(newPage) {
        this.requests.page = newPage;
    }

    get pageSize() {
        return this.requests.pageSize;
    }

    set pageSize(newPageSize) {
        this.requests.pageSize = newPageSize;
    }

    /**
     * 除 pageSize 参数外, 其他所有查询参数都设置为空
     */
    clear() {
        this.requests.clear();
    }

    /**
     * 增加自定义查询参数
     * @param key
     * @param value
     */
    pushParams(key, value) {
        this.requests.pushParams(key, value);
    }

    /**
     * 删除所有自定义查询参数
     */
    clearParams() {
        this.requests.clearParams();
    }

    resetRequests() {
        const requests = this.requests;
        requests.page = 1;
        requests.take = 0;
        requests.skip = 0;
        requests.group = null;
        requests.filter = null;
        requests.sort = null;
        requests.data = null;
    }

    covertResponses(data) {
        const responses = this.responses;
        responses.first = data.first;
        responses.last = data.last;
        responses.number = data.number;
        responses.numberOfElements = data.numberOfElements;
        responses.size = data.size;
        responses.sort = data.sort;
        responses.totalElements = data.totalElements;
        responses.totalPages = data.totalPages;
    }

    isLast() {
        return this.responses.last;
    }

    plusPage() {
        this.requests.page++;
    }

    pushParamsRequests(key, value) {
        this.requests.pushParams(key, value);
    }

    clearParamsRequests() {
        this.requests.clearParams();
    }

    clearFilter() {
        let lockfilter = null;
        if (this.requests.filter && this.requests.filter.filters.length != 0) {
            lockfilter = this.requests.filter.filters.filter(e => {
                if (e.lock) {
                    return e
                }
            })
            this.requests.filter.filters = lockfilter;
        }


    }


    addFilter(filedName: string, value: any, operator: string) {
        //operator操作符为：eq等于，neq不等于，gt大于，gte大于等于，lt小于，lte小于等于，startswith以什么开始，endswith以什么结束，contains包含相当于like，doesnotcontain不包含相当于notlike
        const requests = this.requests;
        if (requests.filter == null) {
            requests.filter = {logic: "and", filters: []};
        }
        this.removeFilter(filedName, value);
        requests.filter.filters.push({
            field: filedName,
            operator: operator,
            value: value,
            lock: false
        });
    }

    addOrFilter(filedName: string, value: any, operator: string) {
        //operator操作符为：eq等于，neq不等于，gt大于，gte大于等于，lt小于，lte小于等于，startswith以什么开始，endswith以什么结束，contains包含相当于like，doesnotcontain不包含相当于notlike
        const requests = this.requests;
        if (requests.filter == null) {
            requests.filter = {logic: "or", filters: []};
        }
        this.removeFilter(filedName, value);
        requests.filter.filters.push({
            field: filedName,
            operator: operator,
            value: value,
            lock: false
        });
    }

    removeFilter(filedName, filedValue) {
        const requests = this.requests;
        if (requests.filter != null) {
            let filters = requests.filter.filters;
            for (let i = 0; i < filters.length; i++) {
                if (filters[i].field === filedName && filters[i].value === filedValue) {
                    filters.splice(i, 1);
                    break;
                }
            }
        }
    }

    /**
     *
     * @param filedName
     */
    removeByNameFilter(filedName) {
        const requests = this.requests;
        if (requests.filter != null) {
            let filters = requests.filter.filters;
            for (let i = 0; i < filters.length; i++) {
                if (filters[i].field === filedName) {
                    filters.splice(i, 1);
                    break;
                }
            }
        }
    }

    /**
     * 重置不清除该字段
     * @param filedName
     */
    addLockFilterName(filedName) {
        this.requests.filter.filters.find(e => {
            if (e.field == filedName) {
                e.lock = true;
            }
        })
    }

    /**
     * 每个字段只添加一个
     * @param filedName
     * @param value
     * @param operator
     */
    addOnlyFilter(filedName, value, operator) {
        //operator操作符为：eq等于，neq不等于，gt大于，gte大于等于，lt小于，lte小于等于，startswith以什么开始，endswith以什么结束，contains包含相当于like，doesnotcontain不包含相当于notlike
        const requests = this.requests;
        if (requests.filter == null) {
            requests.filter = {logic: "and", filters: []};
        }
        this.removeByNameFilter(filedName);
        requests.filter.filters.push({
            field: filedName,
            operator: operator,
            value: value
        });
    }

    /**
     * 只添加一个条件
     * @param filedName
     * @param value
     * @param operator
     */
    addOnlyOneFilter(filedName, value, operator) {
        const requests = this.requests;
        requests.filter = {logic: "and", filters: []};
        requests.filter.filters.push({
            field: filedName,
            operator: operator,
            value: value
        });
    }

    addSort(sortName, value) {
        const requests = this.requests;
        if (requests.sort == null) {
            requests.sort = [];
        }
        requests.sort.push({field: sortName, dir: value});
    }

    changeSort(sortName) {
        const requests = this.requests;
        let filter = requests.sort.filter(e => e.field == sortName);
        //desc降序，asc升序
        if (filter.length != 0) {
            if (filter[0].dir == 'desc') {
                filter[0].dir = 'asc'
            } else {
                filter[0].dir = 'desc'
            }
        }
    }

    onlySort(sortName, value) {
        const requests = this.requests;
        if (requests.sort == null) {
            requests.sort = [];
        }
        if(requests.sort.length && requests.sort[0].field == sortName){
            this.changeSort(sortName);
        } else {
            requests.sort = [];
            this.addSort(sortName, value)
        }
    }


    toJsonString() {
        return JSON.stringify(this.requests);
    }
}

/**
 * 分页查询参数和结果对象
 */
export class PageQuery extends AbstractPageQuery<PageRequest, PageResponse> {
    constructor() {
        super(new PageRequest(), new PageResponse());
    }
}
