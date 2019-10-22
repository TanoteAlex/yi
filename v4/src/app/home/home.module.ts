import {IonicModule} from '@ionic/angular';
import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {HomePage} from './home.page';
import {PipesModule} from "../../pipes/pipes.module";
import {ComponentsModule} from "../components/components.module";

@NgModule({
    imports: [
        IonicModule,
        PipesModule,
        CommonModule,
        FormsModule,
        ComponentsModule,
        RouterModule.forChild([{path: '', component: HomePage}])
    ],
    declarations: [HomePage]
})
export class HomePageModule {
}
