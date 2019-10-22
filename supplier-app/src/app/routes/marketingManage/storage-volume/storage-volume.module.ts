import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {StorageVolumeRoutingModule} from './storage-volume-routing.module';
import {ListStorageVolumeComponent} from './list-storage-volume/list-storage-volume.component';
import {AddCouponComponent} from './add-storage-volume/add-storage-volume.component';
import {EditStorageVolumeComponent} from './edit-storage-volume/edit-storage-volume.component';
import {FormStorageVolumeComponent} from './form-storage-volume/form-storage-volume.component';
import {ViewCouponComponent} from './view-storage-volume/view-coupon.component';
import {ComponentsModule} from "../../components/components.module";
import {CouponService} from '../../services/coupon.service';
import {CommodityService} from "../../services/commodity.service";

const COMPONENTS = [
  ListStorageVolumeComponent,
  AddCouponComponent,
  EditStorageVolumeComponent,
  FormStorageVolumeComponent,
  ViewCouponComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
StorageVolumeRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[CouponService,CommodityService]
})
export class StorageVolumeModule { }
