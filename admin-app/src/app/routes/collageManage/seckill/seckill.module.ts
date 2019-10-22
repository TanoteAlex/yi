


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { SeckillRoutingModule } from './seckill-routing.module';
import { ListSeckillComponent } from './list-seckill/list-seckill.component';
import { AddSeckillComponent } from './add-seckill/add-seckill.component';
import { EditSeckillComponent } from './edit-seckill/edit-seckill.component';
import { FormSeckillComponent } from './form-seckill/form-seckill.component';
import { ViewSeckillComponent } from './view-seckill/view-seckill.component';
import { ComponentsModule } from "../../components/components.module";
import { SeckillService } from '../../services/seckill.service';
import {ProductService} from "../../services/product.service";

const COMPONENTS = [
  ListSeckillComponent,
  AddSeckillComponent,
  EditSeckillComponent,
  FormSeckillComponent,
  ViewSeckillComponent];

const COMPONENTS_NOROUNT = [
  ];

@NgModule({
  imports: [
    SharedModule,
SeckillRoutingModule,
    ComponentsModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT,
  ],
  entryComponents: COMPONENTS_NOROUNT,
  providers:[SeckillService,ProductService]
})
export class SeckillModule { }
