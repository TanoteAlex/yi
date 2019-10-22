import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {InvitesReceivePrizePage} from './invites-receive-prize.page';
import {ComponentsModule} from '../../components/components.module';
import {InvitesReceivePrizeResolverService} from './invites-receive-prize-resolver.service';
import {PipesModule} from '../../../pipes/pipes.module';
import {InvitePrizeProvider} from '../../../services/activity-service/invite-prize';

const routes: Routes = [
    {
        path: '',
        component: InvitesReceivePrizePage,
        resolve: {
            data: InvitesReceivePrizeResolverService
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
    declarations: [InvitesReceivePrizePage],
    providers:[InvitePrizeProvider,InvitesReceivePrizeResolverService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InvitesReceivePrizePageModule {
}
