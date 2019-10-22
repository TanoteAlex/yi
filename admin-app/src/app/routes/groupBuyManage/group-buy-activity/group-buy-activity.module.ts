import {NgModule} from '@angular/core';
import {SharedModule} from '@shared/shared.module';
import {GroupBuyActivityRoutingModule} from './group-buy-activity-routing.module';
import {ListGroupBuyActivityComponent} from './list-group-buy-activity/list-group-buy-activity.component';
import {AddGroupBuyActivityComponent} from './add-group-buy-activity/add-group-buy-activity.component';
import {EditGroupBuyActivityComponent} from './edit-group-buy-activity/edit-group-buy-activity.component';
import {FormGroupBuyActivityComponent} from './form-group-buy-activity/form-group-buy-activity.component';
import {ViewGroupBuyActivityComponent} from './view-group-buy-activity/view-group-buy-activity.component';
import {ComponentsModule} from "../../components/components.module";
import {GroupBuyActivityService} from '../../services/group-buy-activity.service';
import {ProductService} from "../../services/product.service";
import {MemberLevelService} from "../../services/member-level.service";
import {UserService} from "../../services/user.service";
import {CommodityService} from "../../services/commodity.service";

const COMPONENTS = [
  ListGroupBuyActivityComponent,
  AddGroupBuyActivityComponent,
  EditGroupBuyActivityComponent,
  FormGroupBuyActivityComponent,
  ViewGroupBuyActivityComponent];

const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    GroupBuyActivityRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers: [GroupBuyActivityService, ProductService, MemberLevelService, UserService, CommodityService]
})
export class GroupBuyActivityModule {
}
