import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {DistrictsComponent} from "./districts.component";
import {PipesModule} from "../../../pipes/pipes.module";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
        PipesModule,
    ],
    declarations: [
        DistrictsComponent
    ],
    exports: [
        DistrictsComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DistrictsModule {
}
