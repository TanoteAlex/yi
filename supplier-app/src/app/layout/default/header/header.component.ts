import {Component} from '@angular/core';
import {SettingsService} from '@delon/theme';

@Component({
  selector: 'layout-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {
  searchToggleStatus: boolean;

  //菜单栏显示隐藏状态
  title=false;

  constructor(public settings: SettingsService) {
    this.title=this.settings.layout.collapsed;
  }

  toggleCollapsedSideabar() {
    this.settings.setLayout('collapsed', !this.settings.layout.collapsed);
    this.title=this.settings.layout.collapsed;
  }

  searchToggleChange() {
    this.searchToggleStatus = !this.searchToggleStatus;
  }
}
