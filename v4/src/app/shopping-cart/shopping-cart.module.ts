import {IonicModule} from '@ionic/angular';
import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ComponentsModule} from "../components/components.module";
import {ShoppingCartPage} from "./shopping-cart.page";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        ComponentsModule,
        RouterModule.forChild([{path: '', component: ShoppingCartPage}])
    ],
    declarations: [ShoppingCartPage]
})
export class ShoppingCartPageModule {
}
