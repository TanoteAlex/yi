import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {SharedModule} from "../../shared/shared.module";
import {ModalSelecetComponent} from "./modal-selecet/modal-selecet.component";
import {NzModalService} from "ng-zorro-antd";
import {ProvinceComponent} from "./province/province.component";
import {TreeComponent} from './tree/tree.component';
import {PurviewTreeComponent} from './purviewTree/purviewTree.component'
import {OperateTreeComponent} from './operateTree/operateTree.component';
import {CategoryService} from "../services/category.service";
import {EditorComponent} from "./editor/editor.component";
import {UploadComponent} from "./upload/upload.component";
import {NzDemoDatePickerPresettedRangesComponent} from "./date-picker/date-picker.component";
import {ModalComponent} from './modal/modal.component';
import {RescService} from "../services/resc.service";
import {CityComponent} from "./city/city.component";
import {DeptTreeSelectComponent} from "./dept-tree-select/dept-tree-select.component";
import {CommentComponent} from "./comment/comment.component";
import {CommentService} from "../services/comment.service";
import {DynamicUploadComponent} from "./dynamic-upload/dynamic-upload.component";
import {DistrictPipe} from "../pipes/district/district";
import {AlticleCommentComponent} from "./article-comment/alticle-comment.component";
import {ArticleCommentService} from "../services/article-comment.service";
import {DeliverGoodsComponent} from "./deliverGoods/deliverGoods.component";
import {SaleOrderService} from "../services/sale-order.service";
import {ExpressService} from "../services/express.service";
import {AreaComponent} from "./area/area.component";
import {AreaService} from "../services/area.service";
import {CategoryTreeSelectComponent} from './category-tree-select/category-tree-select.component';
import {TipInputComponent} from './tip-input/tip-input.component';
import {OperateCategoryService} from "../services/operate-category.service";
import {RegionTreeSelectComponent} from "./region-tree-select/region-tree-select.component";
import {RegionService} from "../services/region.service";
import {RegionGroupComponent} from "./region-group/region-group.component";
import {RegionGroupService} from "../services/region-group.service";
import { AttrGroupComponent } from './attr-group/attr-group.component';
import {AttachmentService} from "../services/attachment.service";
import {MemberService} from "../services/member.service";
import {UserService} from "../services/user.service";

@NgModule({
  imports: [
    SharedModule,
  ],
  declarations: [
    ModalSelecetComponent, ProvinceComponent, TreeComponent, EditorComponent, UploadComponent, OperateTreeComponent, NzDemoDatePickerPresettedRangesComponent,
    ModalComponent, PurviewTreeComponent, CityComponent, DeptTreeSelectComponent, CommentComponent, DynamicUploadComponent, AlticleCommentComponent, DeliverGoodsComponent,
    AreaComponent, CategoryTreeSelectComponent, TipInputComponent,RegionTreeSelectComponent,RegionGroupComponent, AttrGroupComponent
  ],
  exports: [
    ModalSelecetComponent, ProvinceComponent, TreeComponent, EditorComponent, UploadComponent, OperateTreeComponent, NzDemoDatePickerPresettedRangesComponent,
    ModalComponent, PurviewTreeComponent, CityComponent, DeptTreeSelectComponent, CommentComponent, DynamicUploadComponent, AlticleCommentComponent, DeliverGoodsComponent,
    AreaComponent, CategoryTreeSelectComponent, TipInputComponent,RegionTreeSelectComponent,RegionGroupComponent, AttrGroupComponent
  ],
  providers: [NzModalService, CategoryService, RescService, CommentService, DistrictPipe, ArticleCommentService, SaleOrderService, ExpressService, AreaService,OperateCategoryService,RegionService,RegionGroupService,AttachmentService,MemberService,UserService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ComponentsModule {
}
