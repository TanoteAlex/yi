import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Routes, RouterModule} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {InvitesHomePage} from './invites-home.page';
import {ComponentsModule} from "../../components/components.module";
import {PipesModule} from '../../../pipes/pipes.module';
import {InvitesHomeResolverService} from "./invites-home-resolver.service";
import {InviteActivityProvider} from "../../../services/activity-service/inviteActivity";
import {InvitePrizeProvider} from "../../../services/activity-service/invite-prize";

const routes: Routes = [
    {
        path: '',
        component: InvitesHomePage,
        // resolve: {
        //     data: InvitesHomeResolverService
        // }
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
    declarations: [InvitesHomePage],
    providers:[InviteActivityProvider,InvitePrizeProvider,],
})
export class InvitesHomePageModule {
}
