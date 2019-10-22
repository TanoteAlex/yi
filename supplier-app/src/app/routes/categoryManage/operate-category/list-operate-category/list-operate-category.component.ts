import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import {ActivatedRoute, Router} from '@angular/router';
import {PageQuery} from '../../../models/page-query.model';
import {CategoryService} from '../../../services/category.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {SUCCESS} from "../../../models/constants.model";
import {OperateCategoryService} from "../../../services/operate-category.service";

@Component({
  selector: 'list-operate-category',
  templateUrl: './list-operate-category.component.html',
  styleUrls: ['./list-operate-category.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ListOperateCategoryComponent implements OnInit {

  data = [];
  expandDataCache = {};

  collapse(array: any[], data: any, $event: boolean): void {
    if ($event === false) {
      if (data.children) {
        data.children.forEach(d => {
          const target = array.find(a => a.id === d.id);
          target.expand = false;
          this.collapse(array, target, false);
        });
      } else {
        return;
      }
    }
  }

  convertTreeToList(root: object): any[] {
    const stack = [];
    const array = [];
    const hashMap = {};
    stack.push({...root, level: 0, expand: false});
    //console.log(stack);
    while (stack.length !== 0) {
      const node = stack.pop();
      this.visitNode(node, hashMap, array);
      if (node.children) {
        for (let i = node.children.length - 1; i >= 0; i--) {
          stack.push({...node.children[i], level: node.level + 1, expand: false, parent: node});
        }
      }
    }

    return array;
  }

  visitNode(node: any, hashMap: object, array: any[]): void {
    if (!hashMap[node.id]) {
      hashMap[node.id] = true;
      array.push(node);
    }
  }

  pageQuery: PageQuery = new PageQuery();

  searchForm: FormGroup;

  loading = false;

  datas: any[] = [];

  expandForm = false;

  constructor(public route: ActivatedRoute, public router: Router, private operateCategoryService: OperateCategoryService, public msg: NzMessageService,
              private fb: FormBuilder, private modalService: NzModalService) {
    this.buildForm();
  }

  ngOnInit() {
    this.searchData();
    this.operateCategoryService.getAllCategoryForSupplier().subscribe(data => {

      data.data.forEach(e => {
        //null parent
        if (!e.parent && e.deleted == 0) {
          e.children = []
          this.data.push(e)
        }

      })
      data.data.forEach(e => {
        if (e.parent && e.deleted == 0) {
          data.data.filter(e1 => e1.id == e.parent.id)[0].children.push(e)
        }
      })
      this.data = this.data.map(e => {
        return {
          parent: e.parent,
          key: e.id,
          id: e.id,
          categoryName: e.categoryName,
          children: e.children,
          categoryNo: e.categoryNo,
          imgPath: e.imgPath,
          sort: e.sort,
          remark: e.remark,
          createTime: e.createTime
        }
      })
      // this.data = data.data
      this.data.forEach(item => {
        this.expandDataCache[item.id] = this.convertTreeToList(item);
      });
    })


  }

  buildForm() {
    this.searchForm = this.fb.group({
      id: [null],
      guid: [null],
      parentId: [null],
      categoryNo: [null],
      categoryName: [null],
      imgPath: [null],
      state: [null],
      sort: [null],
      createTime: [null],
      remark: [null],
      deleted: [null],
      delTime: [null],
    });
  }

  sort(sort: { key: string, value: string }): void {
    this.pageQuery.addSort(sort.key, sort.value)
    this.searchData();
  }

  searchData(reset: boolean = false): void {
    if (reset) {
      this.pageQuery.resetPage();
    }
    this.configPageQuery(this.pageQuery);
    this.getData();
  }

  getData() {
    this.loading = true;
    this.operateCategoryService.queryForSupplier(this.pageQuery).subscribe(response => {
      this.datas = response['content'];
      this.pageQuery.covertResponses(response);
      this.loading = false;
    }, error => {
      this.loading = false;
      this.msg.error('请求错误' + error.message);
    });

  }

  clearSearch() {
    this.searchForm.reset({
      id: null,
      guid: null,
      parentId: null,
      categoryNo: null,
      categoryName: null,
      imgPath: null,
      state: null,
      sort: null,
      createTime: null,
      remark: null,
      deleted: null,
      delTime: null,
    });
    this.pageQuery.clearFilter();
    this.searchData();
  }


  remove(item, table) {
    this.operateCategoryService.removeById(item.id).subscribe(response => {
      if (response.result == SUCCESS) {
        this.msg.success("删除成功");
        item.del = true
      } else {
        this.msg.error('请求失败' + response.message);
      }
    }, error => {
      this.msg.error('请求失败' + error.message);
    });
  }

  private configPageQuery(pageQuery: PageQuery) {
    pageQuery.clearFilter();
    const searchObj = this.searchForm.value;

    if (searchObj.parentId != null) {
      pageQuery.addOnlyFilter('parent.categoryName', searchObj.parentId, 'contains');
    }
    if (searchObj.categoryNo != null) {
      pageQuery.addOnlyFilter('categoryNo', searchObj.categoryNo, 'contains');
    }
    if (searchObj.categoryName != null) {
      pageQuery.addOnlyFilter('categoryName', searchObj.categoryName, 'contains');
    }
    if (searchObj.remark != null) {
      pageQuery.addOnlyFilter('remark', searchObj.remark, 'contains');
    }

    return pageQuery;
  }


}
