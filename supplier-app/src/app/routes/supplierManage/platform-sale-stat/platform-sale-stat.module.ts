import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {PlatformSaleStatRoutingModule} from './platform-sale-stat-routing.module';
import {ListPlatformSaleStatComponent} from './list-platform-sale-stat/list-platform-sale-stat.component';
import {AddPlatformSaleStatComponent} from './add-platform-sale-stat/add-platform-sale-stat.component';
import {EditPlatformSaleStatComponent} from './edit-platform-sale-stat/edit-platform-sale-stat.component';
import {FormPlatformSaleStatComponent} from './form-platform-sale-stat/form-platform-sale-stat.component';
import {ViewPlatformSaleStatComponent} from './view-platform-sale-stat/view-platform-sale-stat.component';
import {ComponentsModule} from "../../components/components.module";
import {PlatformSaleStatService} from '../../services/platform-sale-stat.service';

const COMPONENTS = [
  ListPlatformSaleStatComponent,
  AddPlatformSaleStatComponent,
  EditPlatformSaleStatComponent,
  FormPlatformSaleStatComponent,
  ViewPlatformSaleStatComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
PlatformSaleStatRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[PlatformSaleStatService]
})
export class PlatformSaleStatModule { }
