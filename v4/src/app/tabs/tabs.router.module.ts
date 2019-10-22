import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {TabsPage} from './tabs.page';
import {HomePage} from '../home/home.page';
import {ShoppingCartPage} from '../shopping-cart/shopping-cart.page';
import {LoveLifePage} from '../love-life/love-life.page';
import {CustomerCenterPage} from '../customer-center/customer-center.page';
import {CategoryPage} from "../category/category.page";

const routes: Routes = [
    {
        path: 'tabs',
        component: TabsPage,
        children: [
            {
                path: '',
                redirectTo: '/app/tabs/home',
                pathMatch: 'full'
            },
            {
                path: 'home',
                children: [
                    {
                        path: '',
                        component: HomePage,
                    }
                ]
            },
            {
                path: 'category',
                children: [
                    {
                        path: '',
                        component: CategoryPage,
                    }
                ]
            },
            {
                path: 'shoppingCart',
                children: [
                    {
                        path: '',
                        component: ShoppingCartPage,
                    }
                ]
            },
            {
                path: 'loveLife',
                children: [
                    {
                        path: '',
                        component: LoveLifePage,
                    }
                ]
            },
            {
                path: 'customerCenter',
                children: [
                    {
                        path: '',
                        component: CustomerCenterPage,
                    }
                ]
            }
        ]
    }
];



@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TabsPageRoutingModule {
}
