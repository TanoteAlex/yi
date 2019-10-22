import {Component, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {SettingsService} from '@delon/theme';
import {DA_SERVICE_TOKEN, ITokenService} from '@delon/auth';

@Component({
    selector: 'header-user',
    template: `
  <nz-dropdown nzPlacement="bottomRight">
    <div class="item d-flex align-items-center px-sm" nz-dropdown>
      <nz-avatar [nzSrc]="this.headImg" nzSize="small" class="mr-sm"></nz-avatar>
      {{username}}
    </div>
    <div nz-menu class="width-sm">
      <div nz-menu-item [nzDisabled]="true"><a [routerLink]="'/dashboard/user/view/'+this.userId"><i class="anticon anticon-user mr-sm"></i>个人中心</a></div>
      <div nz-menu-item [nzDisabled]="true"><i class="anticon anticon-setting mr-sm"></i>设置</div>
      <li nz-menu-divider></li>
      <div nz-menu-item (click)="logout()"><i class="anticon anticon-setting mr-sm"></i>退出登录</div>
    </div>
  </nz-dropdown>
  `,
})
export class HeaderUserComponent {

    loginUser;
    username: string = '美周壹壹优选';
    headImg: string = "./assets/icon.png";
    userId = 0;

    constructor(
        public settings: SettingsService,
        private router: Router,
        @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
    ) { }

    ngOnInit() {
        this.loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
        if (this.loginUser != null) {
            if (this.loginUser[0].avatar && this.loginUser[0].avatar != null && this.loginUser[0].avatar != '') {
                this.headImg = this.loginUser[0].avatar;
            }
            this.username = this.loginUser[0].username;
            this.userId = this.loginUser[0].id;
        }
    }


    logout() {
        sessionStorage.clear();
        this.tokenService.clear();
        this.router.navigateByUrl(this.tokenService.login_url);
    }
}
