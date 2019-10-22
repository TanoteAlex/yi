import {FormErrorPipe} from './form-error/form-error';
import {SortPipe} from './sort/sort';

import {DistrictPipe} from './district/district';
import {NgModule} from "@angular/core";


@NgModule({
	declarations: [FormErrorPipe,
    SortPipe,
    DistrictPipe],
	imports: [],
	exports: [FormErrorPipe,
    SortPipe,
    DistrictPipe]
})
export class PipesModule {}
