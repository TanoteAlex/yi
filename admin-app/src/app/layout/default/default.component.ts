import {Component, EventEmitter, Injector} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationError, RouteConfigLoadStart, Router,} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd';
import {MenuService, ScrollService, SettingsService} from '@delon/theme';

@Component({
  selector: 'layout-default',
  templateUrl: './default.component.html',
})
export class LayoutDefaultComponent {
  isFetching = false;
  isRefresh = false;

  constructor(
    private menuService: MenuService,
    router: Router,
    scroll: ScrollService,
    _message: NzMessageService,
    public menuSrv: MenuService,
    public settings: SettingsService,
    private injector: Injector
  ) {
    // scroll to top in change page
    router.events.subscribe(evt => {
      if(sessionStorage.getItem('menus')==null){
        this.goTo('/passport/login');
      }
      if (!this.isRefresh) {
        if(sessionStorage.getItem('menus')!=null){
          this.menuService.add(JSON.parse(sessionStorage.getItem('menus')));
        }
      }
      this.isRefresh = true;

      if (!this.isFetching && evt instanceof RouteConfigLoadStart) {
        this.isFetching = true;
      }
      if (evt instanceof NavigationError || evt instanceof NavigationCancel) {
        this.isFetching = false;
        if (evt instanceof NavigationError) {
          _message.error(`无法加载${evt.url}路由`, {nzDuration: 1000 * 3});
        }
        return;
      }
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      setTimeout(() => {
        scroll.scrollToTop();
        this.isFetching = false;
      }, 100);
    });
  }

  private goTo(url: string) {
    setTimeout(() => this.injector.get(Router).navigateByUrl(url));
  }
}
