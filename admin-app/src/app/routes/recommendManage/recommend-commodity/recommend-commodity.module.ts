


import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { RecommendCommodityRoutingModule } from './recommend-commodity-routing.module';
import { ListRecommendCommodityComponent } from './list-recommend-commodity/list-recommend-commodity.component';
import { AddRecommendCommodityComponent } from './add-recommend-commodity/add-recommend-commodity.component';
import { EditRecommendCommodityComponent } from './edit-recommend-commodity/edit-recommend-commodity.component';
import { FormRecommendCommodityComponent } from './form-recommend-commodity/form-recommend-commodity.component';
import { ViewRecommendCommodityComponent } from './view-recommend-commodity/view-recommend-commodity.component';
import { ComponentsModule } from "../../components/components.module";
import { RecommendCommodityService } from '../../services/recommend-commodity.service';

const COMPONENTS = [
    ListRecommendCommodityComponent,
    AddRecommendCommodityComponent,
    EditRecommendCommodityComponent,
    FormRecommendCommodityComponent,
    ViewRecommendCommodityComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        RecommendCommodityRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [RecommendCommodityService]
})
export class RecommendCommodityModule { }