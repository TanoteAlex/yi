import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {CommonModule} from "@angular/common";
import {SmallTitleLineComponent} from "./small-title-line.component";
import {FormsModule} from "@angular/forms";

@NgModule({
    imports: [
        IonicModule,
        CommonModule,
        FormsModule,
    ],
    declarations: [
        SmallTitleLineComponent
    ],
    exports: [
        SmallTitleLineComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmallTitleLineModule {
}
