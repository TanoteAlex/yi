import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {SalesAreaColumnPage} from './sales-area-column.page';
import {ComponentsModule} from '../../components/components.module';
import {PipesModule} from '../../../pipes/pipes.module';
import {SalesAreaColumnResolverService} from './sales-area-column-resolver.service';
import {SalesAreaProvider} from '../../../services/sales-area-service/sales-area';
import {AreaColumnProvider} from '../../../services/sales-area-service/area-column';

const routes: Routes = [
    {
        path: '',
        component: SalesAreaColumnPage,
        resolve: {
            data: SalesAreaColumnResolverService
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
    declarations: [SalesAreaColumnPage],
    providers: [AreaColumnProvider, SalesAreaColumnResolverService],
})
export class SalesAreaColumnPageModule {
}
