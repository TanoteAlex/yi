import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {BannerRoutingModule} from './banner-routing.module';
import {ListBannerComponent} from './list-banner/list-banner.component';
import {AddBannerComponent} from './add-banner/add-banner.component';
import {EditBannerComponent} from './edit-banner/edit-banner.component';
import {FormBannerComponent} from './form-banner/form-banner.component';
import {ViewBannerComponent} from './view-banner/view-banner.component';
import {ComponentsModule} from "../../components/components.module";
import {BannerService} from '../../services/banner.service';

const COMPONENTS = [
  ListBannerComponent,
  AddBannerComponent,
  EditBannerComponent,
  FormBannerComponent,
  ViewBannerComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
    BannerRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[BannerService]
})
export class BannerModule { }
