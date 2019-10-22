import {NgModule} from '@angular/core';
import {SharedModule} from 'app/shared/shared.module';
import {CommodityToExamineRoutingModule} from './commodity-to-examine-routing.module';
import {ListCommodityToExamineComponent} from './list-commodity-to-examine/list-commodity-to-examine.component';
import {ViewCommodityToExamineComponent} from './view-commodity-to-examine/view-commodity-to-examine.component';
import {ComponentsModule} from "../../components/components.module";
import {CommodityService} from "../../services/commodity.service";

const COMPONENTS = [
    ListCommodityToExamineComponent,
    ViewCommodityToExamineComponent];

const COMPONENTS_NOROUNT = [
];

@NgModule({
    imports: [
        SharedModule,
        CommodityToExamineRoutingModule,
        ComponentsModule
    ],
    declarations: [
        ...COMPONENTS,
        ...COMPONENTS_NOROUNT,
    ],
    entryComponents: COMPONENTS_NOROUNT,
    providers: [CommodityService]
})
export class CommodityToExamineModule { }
