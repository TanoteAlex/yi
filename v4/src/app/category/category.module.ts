import {IonicModule} from '@ionic/angular';
import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {CategoryPage} from './category.page';
import {ComponentsModule} from "../components/components.module";
import {PipesModule} from "../../pipes/pipes.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        ComponentsModule,
        PipesModule,
        RouterModule.forChild([{path: '', component: CategoryPage}])
    ],
    declarations: [CategoryPage]
})
export class CategoryPageModule {
}
