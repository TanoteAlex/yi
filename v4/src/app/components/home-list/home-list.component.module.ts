import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HomeListComponent} from "./home-list.component";
import {PipesModule} from "../../../pipes/pipes.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        PipesModule
    ],
    declarations: [
        HomeListComponent
    ],
    exports: [
        HomeListComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HomeListComponentModule {
}
