import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {NewTimeCountComponent} from "./new-time-count.component";

@NgModule({
    imports: [
        IonicModule,
        CommonModule
    ],
    declarations: [
        NewTimeCountComponent
    ],
    exports: [
        NewTimeCountComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewTimeCountModule {
}
