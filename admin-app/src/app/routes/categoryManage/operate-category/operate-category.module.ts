


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { OperateCategoryRoutingModule } from './operate-category-routing.module';
import { ListOperateCategoryComponent } from './list-operate-category/list-operate-category.component';
import { AddOperateCategoryComponent } from './add-operate-category/add-operate-category.component';
import { EditOperateCategoryComponent } from './edit-operate-category/edit-operate-category.component';
import { FormOperateCategoryComponent } from './form-operate-category/form-operate-category.component';
import { ViewOperateCategoryComponent } from './view-operate-category/view-operate-category.component';
import { ComponentsModule } from "../../components/components.module";
import { OperateCategoryService } from '../../services/operate-category.service';
import {CommodityService} from "../../services/commodity.service";
import {ArticleService} from "../../services/article.service";

const COMPONENTS = [
  ListOperateCategoryComponent,
  AddOperateCategoryComponent,
  EditOperateCategoryComponent,
  FormOperateCategoryComponent,
  ViewOperateCategoryComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
OperateCategoryRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[OperateCategoryService,CommodityService,ArticleService]
})
export class OperateCategoryModule { }
