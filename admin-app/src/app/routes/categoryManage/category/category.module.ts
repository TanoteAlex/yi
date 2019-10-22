


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { CategoryRoutingModule } from './category-routing.module';
import { ListCategoryComponent } from './list-category/list-category.component';
import { AddCategoryComponent } from './add-category/add-category.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';
import { FormCategoryComponent } from './form-category/form-category.component';
import { ViewCategoryComponent } from './view-category/view-category.component';
import { ComponentsModule } from "../../components/components.module";
import { CategoryService } from '../../services/category.service';

const COMPONENTS = [
  ListCategoryComponent,
  AddCategoryComponent,
  EditCategoryComponent,
  FormCategoryComponent,
  ViewCategoryComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
CategoryRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CategoryService]
})
export class CategoryModule { }