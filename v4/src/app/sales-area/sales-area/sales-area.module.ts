import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {SalesAreaPage} from './sales-area.page';
import {PipesModule} from '../../../pipes/pipes.module';
import {ComponentsModule} from '../../components/components.module';
import {SalesAreaResolverService} from './sales-area-resolver.service';
import {SalesAreaProvider} from '../../../services/sales-area-service/sales-area';

const routes: Routes = [
    {
        path: '',
        component: SalesAreaPage,
        resolve: {
            data: SalesAreaResolverService
        }
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        PipesModule,
        ComponentsModule,
        RouterModule.forChild(routes)
    ],
    declarations: [SalesAreaPage],
    providers:[SalesAreaProvider,SalesAreaResolverService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SalesAreaPageModule {
}
