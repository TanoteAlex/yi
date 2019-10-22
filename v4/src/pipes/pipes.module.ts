import {FormErrorPipe} from './form-error/form-error';
import {SortPipe} from './sort/sort';

import {NgModule} from '@angular/core';
import {DistrictPipe} from './district/district';
import {ThumbnailPipe} from "./thumbnail/thumbnail.pipe";


@NgModule({
    declarations: [FormErrorPipe,
        SortPipe,
        DistrictPipe,
        ThumbnailPipe],
    imports: [],
    exports: [FormErrorPipe,
        SortPipe,
        DistrictPipe,
        ThumbnailPipe]
})
export class PipesModule {
}
