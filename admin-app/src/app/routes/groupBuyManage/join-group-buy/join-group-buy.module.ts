


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { JoinGroupBuyRoutingModule } from './join-group-buy-routing.module';
import { ListJoinGroupBuyComponent } from './list-join-group-buy/list-join-group-buy.component';
import { AddJoinGroupBuyComponent } from './add-join-group-buy/add-join-group-buy.component';
import { EditJoinGroupBuyComponent } from './edit-join-group-buy/edit-join-group-buy.component';
import { FormJoinGroupBuyComponent } from './form-join-group-buy/form-join-group-buy.component';
import { ViewJoinGroupBuyComponent } from './view-join-group-buy/view-join-group-buy.component';
import { ComponentsModule } from "../../components/components.module";
import { JoinGroupBuyService } from '../../services/join-group-buy.service';

const COMPONENTS = [
  ListJoinGroupBuyComponent,
  AddJoinGroupBuyComponent,
  EditJoinGroupBuyComponent,
  FormJoinGroupBuyComponent,
  ViewJoinGroupBuyComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
JoinGroupBuyRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[JoinGroupBuyService]
})
export class JoinGroupBuyModule { }