import {Component, EventEmitter, Input, Output, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {PageQuery} from "../../models/page-query.model";
import {NzModalService} from "ng-zorro-antd";
import {BaseService} from "../../services/base.service";
import {DistrictPipe} from "../../pipes/district/district";

@Component({
    selector: 'app-modal-selecet',
    templateUrl: './modal-selecet.component.html',
    styleUrls: ['./modal-selecet.component.less'],
    encapsulation: ViewEncapsulation.None,
    providers: []
})
export class ModalSelecetComponent {
    /**
     * 搜索项
     * @type {any[]}
     */
    @Input()
    filters: any = [];

    /**
     * 标题
     * @type {string}
     */
    @Input()
    title: string = '请选择';

    /**
     * 打开modal按钮
     * @type {string}
     */
    @Input()
    openButton: string = "请选择";

    /**
     * 确定按钮
     * @type {string}
     */
    @Input()
    sureButton: string = '保存';

    /**
     * 初始已选对象 多选传数组 require
     * @type {number}
     */
    @Input()
    select;

    /**
     * 查询service require
     * @type {null}
     */
    @Input()
    baseService: BaseService;

    /**
     * 显示字段 require
     * @type {any[]}
     */
    @Input()
    showCol = [];

    /**
     * 显示table字段、名称 require
     * @type {any[]}
     */
    @Input()
    showTable = [];

    /**
     * 是否多选 默认单选
     * @type {boolean}
     */
    @Input()
    isMulti = false;

    /**
     * 按钮上的显示值
     * @type {string}
     */
    @Input()
    resultName = '请选择';

    /**
     * true跳query方法 false 跳自定义方法
     * @type {boolean}
     */
    @Input()
    custom = true;

    @Output()
    result = new EventEmitter

    /**
     * tag or table
     * @type {string}
     */
    @Input()
    showMode = 'tag'

    /**
     * 显示对象s
     * @type {any[]}
     */
    _anys = [];

    /**
     * 显示名
     * @type {string}
     */
    _showName = '';

    /**
     * 判断执行地址转换
     * @type {boolean}
     */
    @Input()
    province = false;

    @Input()
    pageQuery: PageQuery = new PageQuery()

    allChecked = false;
    indeterminate = false;

    _backSelect;

    isFirst = false;

    total = 0;       //总记录数
    pageSize: number = 5;  //页的大小，初始值为5
    page: number = 1;       //页号，初始为1
    totalPages = 0; //总页数

    constructor(private modalService: NzModalService, public districtPipe: DistrictPipe) {
    }

    /**
     * 调用选择回显参数
     * @param {any[]} checkedKeys input id [] or id single
     */
    dataRetrieval(checkedKeys: any) {
        this._backSelect = checkedKeys;
    }


    /**
     * 组件加载完后
     */
    ngAfterViewInit() {
        this.getData();
        this.showCol.forEach(e => {
            if (e.isShow) {
                this._showName = e.name
            }
        })
    }

    refreshStatus(item, id): void {
        this._anys.forEach(e => {
            if (id == e.id && item.checked == true) {
                e.checked = false;
            } else if (id == e.id && item.checked == false) {
                e.checked = true;
            }
        })
    }

    checkAll(value: boolean): void {
        this._anys.forEach(e => {
            e.checked = value;
        })
        if (value) {
            let select = []
            this._anys.forEach(e => {
                select.push(e)
            })
            this.select = select
        } else {
            this.select = []
        }
        this.changeResultName();
    }


    pageChange(event) {
        this.pageQuery.page = event;
        this.getData();
    }


    searchData(): void {
        this.filters.filter(e => e.value != '').forEach(e => {
            this.pageQuery.addOnlyFilter(e.filterName, e.value, 'contains')
        })
        //默认从第一页搜索
        this.pageQuery.page = 1;
        this.getData();
    }

    clearSearch() {
        this.filters.forEach(e => {
            e.value = ''
        })
        this.pageQuery.clearFilter();
        this.searchData();
    }

    getData() {
        //this._loading = true;
        this.pageQuery.addOnlyFilter('deleted', 0, 'eq');
        if (this.custom) {
            this.baseService['query'](this.pageQuery).subscribe(response => {
                this._anys = response.content;
                if (this.province) {
                    this._anys.forEach(e => {
                        e.province = this.districtPipe.transform(e.province);
                        e.city = this.districtPipe.transform(e.city);
                        e.district = this.districtPipe.transform(e.district);
                    })
                }
                this.pageQuery.covertResponses(response);
                //this._loading = false;
                this.multiSelect();
            }, error => {
                //this._loading = false;
            });
        } else {
            this.baseService['getAll']().subscribe(response => {
                this._anys = response.data;
                this._backSelect = JSON.stringify(JSON.parse(this.select))

                /* this.pageQuery.covertResponses(response);*/
                //this._loading = false;
                this.multiSelect();
            }, error => {
                //this._loading = false;
            });
        }

    }

    /**
     * 点击选中
     * @param item
     */
    getSelect(item, id) {
        item.checked = !item.checked
        if (item != null) {
            if (!this.isFirst) {
                this._backSelect = JSON.parse(JSON.stringify(this.select))
            }
            if (this.isMulti) {
                if (this.select == null) {
                    this.select = [];
                }
                if (this.getMultCheck(item)) {
                    this.select.splice(this.select.indexOf(this.select.filter(e => e.id == id)[0]), 1);
                } else {
                    this.select.push(item)
                }
                this.changeResultName();
            } else {
                this.select = item
                this.resultName = item[this._showName]
            }
        } else {
            console.error("无初始值 没有也传个空集合过来")
        }
        this.isFirst = true;
    }

    private changeResultName() {
        this.resultName = ""
        this.select.forEach((e, index) => {
            this.resultName += e[this._showName];
            if (this.select.length != index + 1) {
                this.resultName += ","
            }
        })
    }

    /**
     * 多选是否选中
     * @param item
     * @returns {boolean}
     */
    getMultCheck(item) {
        if (this.select == null) {
            return false
        }
        let filter = this.select.filter(e => e.id == item.id);
        return filter.length != 0
    }

    @ViewChild('content') content: TemplateRef<{}>

    /**
     * 打开modal
     * @param content
     */
    open(content) {
        this.multiSelect();
    }

    private multiSelect() {
        if (this.isMulti) {
            this._anys.forEach(e => {
                e.checked = false;
                if (this.select != null) {
                    if (this.select.filter(e1 => e.id == e1.id).length != 0) {
                        e.checked = true;
                    }
                }
            })
        }
    }

    close() {
        this.modalService.closeAll();

    }

    reset() {
        this.resultName = '请选择';
    }

    isVisible = false;

    showModal(content): void {
        this.multiSelect();
        this.isVisible = true;
    }

    handleOk(): void {
        if (this.isMulti) {
            this._backSelect = []
            this.select.forEach(e => {
                this._backSelect.push(e)
            })
        } else {
            this._backSelect = this.select
        }
        this.result.emit(this.select);
        this.isVisible = false;
        this.total = this._backSelect.length;
        this.totalPages = Math.ceil(this.total / this.pageSize);
    }

    handleCancel(): void {
        if (this.select) {
            this.resultName = '请选择';
            if (this.isMulti) {
                this.resultName = ""
                if (this._backSelect) {
                    this._backSelect.forEach((e, index) => {
                        this.resultName += e[this._showName];
                        if (this._backSelect.length != index + 1) {
                            this.resultName += ","
                            this.result.emit(this._backSelect);
                        }
                    })
                    this.select = JSON.parse(JSON.stringify(this._backSelect));
                } else {
                    this.result.emit([]);
                }

            } else {
                if (this.select.id == 0) {
                    this.resultName = this.select[this._showName]
                    this.result.emit(this.select);
                } else {
                    if (!this.isFirst) {
                        this._backSelect = this.select;
                        this.isFirst = true;
                    }
                    if (this._backSelect) {
                        this.resultName = this._backSelect[this._showName]
                        this.result.emit(this._backSelect);
                    } else {
                        this.resultName = '请选择';
                        this.result.emit({});
                    }
                }
            }
        } else {
            this.resultName = '请选择';
            this.select = [];
            this.result.emit(this.select);
        }
        this.isVisible = false;
    }

    //上一页
    previousPage() {
        this.page--;
        this.changePage(this.page);
    }

    // 下一页
    nextPage() {
        this.page++;
        this.changePage(this.page);
    }


    // 首页
    topPage() {
        this.page = 1;
    }

    // 尾页
    endPage() {
        this.page = this.totalPages;
    }

    // 改变页号，更新表格所在页数的数据
    changePage(page: number): void {
        if (page > 0 && page < this.totalPages) { //正常之间的
            this.page = page;
            //以防改变页的大小或总记录数/页大小时不为整，出现小数点形式的
            this.totalPages = Math.ceil(this.total / this.pageSize);
        } else if (page <= 0) { //页号小于首页
            this.page = 1;
        } else { //页号大于尾页号
            this.page = this.totalPages;
        }
    }

    /**
     * 删除方法
     */
    remove(id) {
        this._backSelect = this._backSelect.filter(e => e.id != id);
        this._anys.forEach(e1 => {
            if (e1.id == id) {
                e1.checked = false;
            }
        })
        this.select = this.select.filter(e2 => e2.id != id);
        this.result.emit(this.select);
    }
}
