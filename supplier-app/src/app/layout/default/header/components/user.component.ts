import {Component, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {SettingsService} from '@delon/theme';
import {DA_SERVICE_TOKEN, ITokenService} from '@delon/auth';

@Component({
  selector: 'header-user',
  template: `
  <nz-dropdown nzPlacement="bottomRight">
    <div class="item d-flex align-items-center px-sm" nz-dropdown>
      <nz-avatar [nzSrc]="'./assets/icon.png'" nzSize="small" class="mr-sm"></nz-avatar>
      {{supplierName}}
    </div>
    <div nz-menu class="width-sm">
      <div nz-menu-item [nzDisabled]="true"><a [routerLink]="'/dashboard/account-information'"><i class="anticon anticon-user mr-sm"></i>个人中心</a></div>
      <!-- <div nz-menu-item [nzDisabled]="true"><i class="anticon anticon-setting mr-sm"></i>设置</div> -->
      <li nz-menu-divider></li>
      <div nz-menu-item (click)="logout()"><i class="anticon anticon-setting mr-sm"></i>退出登录</div>
    </div>
  </nz-dropdown>
  `,
})
export class HeaderUserComponent {

  loginSupplier;
  supplierId = 0;
  supplierName: string = '美周壹壹优选';
  headImg: string = "./assets/icon.png";

  constructor(public settings: SettingsService,
              private router: Router,
              @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,) {
  }

  ngOnInit() {
    this.loginSupplier = JSON.parse(localStorage.getItem("loginSupplier"));
    if (this.loginSupplier != null) {
      this.supplierName = this.loginSupplier.supplierName;
      this.supplierId = this.loginSupplier.id;
    }
  }

  logout() {
    sessionStorage.clear();
    this.tokenService.clear();
    this.router.navigateByUrl(this.tokenService.login_url);
  }
}
