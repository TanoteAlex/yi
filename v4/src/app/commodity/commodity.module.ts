import {NgModule} from '@angular/core';
import {CommodityPage} from './commodity.page';
import {PipesModule} from "../../pipes/pipes.module";
import {ComponentsModule} from "../components/components.module";
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {CommodityResolverService} from "./commodity-resolver.service";


@NgModule({
    entryComponents: [],
    imports: [
        IonicModule,
        PipesModule,
        CommonModule,
        FormsModule,
        ComponentsModule,
        RouterModule.forChild([{
            path: '', component: CommodityPage, resolve: {
                data: CommodityResolverService
            }
        }])
    ],
    declarations: [CommodityPage]
})
export class CommodityPageModule {
}
