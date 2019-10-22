import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {CommodityGroupPage} from './commodity-group.page';
import {ComponentsModule} from "../../components/components.module";
import {PipesModule} from "../../../pipes/pipes.module";
import {CommodityGroupResolverService} from "./commodity-group-resolver.service";

const routes: Routes = [
    {
        path: '',
        component: CommodityGroupPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        ComponentsModule,
        PipesModule,
        RouterModule.forChild([{
            path: '', component: CommodityGroupPage, resolve: {
                data: CommodityGroupResolverService
            }
        }])
    ],
    declarations: [CommodityGroupPage]
})
export class CommodityGroupPageModule {
}
