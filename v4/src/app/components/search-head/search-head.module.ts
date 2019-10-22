import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {SearchHeadComponent} from "./search-head";
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {PipesModule} from "../../../pipes/pipes.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        PipesModule,
        FormsModule,
    ],
    declarations: [
        SearchHeadComponent
    ],
    exports: [
        SearchHeadComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SearchHeadModule {
}
