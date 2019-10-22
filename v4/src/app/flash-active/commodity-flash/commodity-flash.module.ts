import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {CommodityFlashPage} from './commodity-flash.page';
import {PipesModule} from "../../../pipes/pipes.module";
import {ComponentsModule} from "../../components/components.module";
import {CommodityFlashResolverService} from "./commodity-flash-resolver.service";

const routes: Routes = [
    {
        path: '',
        component: CommodityFlashPage
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
            path: '', component: CommodityFlashPage, resolve: {
                data: CommodityFlashResolverService
            }
        }])
    ],
    declarations: [CommodityFlashPage]
})
export class CommodityFlashPageModule {
}
