import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {AdvertisementRoutingModule} from './advertisement-routing.module';
import {ListAdvertisementComponent} from './list-advertisement/list-advertisement.component';
import {AddAdvertisementComponent} from './add-advertisement/add-advertisement.component';
import {EditAdvertisementComponent} from './edit-advertisement/edit-advertisement.component';
import {FormAdvertisementComponent} from './form-advertisement/form-advertisement.component';
import {ViewAdvertisementComponent} from './view-advertisement/view-advertisement.component';
import {ComponentsModule} from "../../components/components.module";
import {BannerService} from '../../services/banner.service';
import {AdvertisementService} from "../../services/advertisement.service";
import {PositionService} from "../../services/position.service";
import {ArticleService} from "../../services/article.service";
import {CommodityService} from "../../services/commodity.service";

const COMPONENTS = [
  ListAdvertisementComponent,
  AddAdvertisementComponent,
  EditAdvertisementComponent,
  FormAdvertisementComponent,
  ViewAdvertisementComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
    AdvertisementRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[AdvertisementService,PositionService,ArticleService,CommodityService]
})
export class AdvertisementModule { }
