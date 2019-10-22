


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { GroupBuyOrderRoutingModule } from './group-buy-order-routing.module';
import { ListGroupBuyOrderComponent } from './list-group-buy-order/list-group-buy-order.component';
import { AddGroupBuyOrderComponent } from './add-group-buy-order/add-group-buy-order.component';
import { EditGroupBuyOrderComponent } from './edit-group-buy-order/edit-group-buy-order.component';
import { FormGroupBuyOrderComponent } from './form-group-buy-order/form-group-buy-order.component';
import { ViewGroupBuyOrderComponent } from './view-group-buy-order/view-group-buy-order.component';
import { ComponentsModule } from "../../components/components.module";
import {GroupBuyOrderService} from '../../services/group-buy-order.service';

const COMPONENTS = [
  ListGroupBuyOrderComponent,
  AddGroupBuyOrderComponent,
  EditGroupBuyOrderComponent,
  FormGroupBuyOrderComponent,
  ViewGroupBuyOrderComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
GroupBuyOrderRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[GroupBuyOrderService]
})
export class GroupBuyOrderModule { }
