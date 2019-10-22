import { _HttpClient, SettingsService } from '@delon/theme';
import {Component, Inject, OnDestroy, Optional} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NzMessageService, NzModalService} from 'ng-zorro-antd';
import { DA_SERVICE_TOKEN, ITokenService, JWTTokenModel, SocialOpenType, SocialService, TokenService } from '@delon/auth';
import {ReuseTabService} from '@delon/abc';
import {environment} from '@env/environment';
import {StartupService} from '@core/startup/startup.service';
import {LoginService} from "../../services/login.service";
import { HttpGlobalHeader } from '../../configs/http-global-header';

@Component({
    selector: 'passport-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.less'],
    providers: [SocialService],
})
export class UserLoginComponent implements OnDestroy {
    form: FormGroup;
    error = '';
    type = 0;
    loading = false;

    constructor(
        public loginService: LoginService,
        fb: FormBuilder,
        private router: Router,
        public msg: NzMessageService,
        private modalSrv: NzModalService,
        private settingsService: SettingsService,
        private socialService: SocialService,
        @Optional()
        @Inject(ReuseTabService)
        private reuseTabService: ReuseTabService,
        @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
        public http: _HttpClient,
        private startupSrv: StartupService,
    ) {
        this.form = fb.group({
            userName: ["", [Validators.required, Validators.minLength(5)]],
            password: ["", Validators.required],
            mobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
            captcha: [null, [Validators.required]],
            remember: [true],
        });
        modalSrv.closeAll();
    }

    // region: fields

    get userName() {
        return this.form.controls.userName;
    }
    get password() {
        return this.form.controls.password;
    }
    get mobile() {
        return this.form.controls.mobile;
    }
    get captcha() {
        return this.form.controls.captcha;
    }

    // endregion

    switch(ret: any) {
        this.type = ret.index;
    }

    // region: get captcha

    count = 0;
    interval$: any;

    getCaptcha() {
        this.count = 59;
        this.interval$ = setInterval(() => {
            this.count -= 1;
            if (this.count <= 0) clearInterval(this.interval$);
        }, 1000);
    }

    // endregion

    submit() {
      this.error = '';
      if (this.type === 0) {
        this.userName.markAsDirty();
        this.userName.updateValueAndValidity();
        this.password.markAsDirty();
        this.password.updateValueAndValidity();
        if (this.userName.invalid || this.password.invalid) return;
      } else {
        this.mobile.markAsDirty();
        this.mobile.updateValueAndValidity();
        this.captcha.markAsDirty();
        this.captcha.updateValueAndValidity();
        if (this.mobile.invalid || this.captcha.invalid) return;
      }

      // **注：** DEMO中使用 `setTimeout` 来模拟 http
      // 默认配置中对所有HTTP请求都会强制[校验](https://ng-alain.com/auth/getting-started) 用户 Token
      // 然一般来说登录请求不需要校验，因此可以在请求URL加上：`/login?_allow_anonymous=true` 表示不触发用户 Token 校验
      this.loading = true;

      this.loginService.loginForSupplier(this.userName.value, this.password.value).subscribe(data => {
        this.loading = false;
        if (data.result == 'SUCCESS') {
          // 清空路由复用信息
          this.reuseTabService.clear();
          // 设置用户Token信息
          this.tokenService.set({token:data.token});

          HttpGlobalHeader.setToken(data.token);
          localStorage.setItem("user",data.loginUser);
          localStorage.setItem("loginSupplier",JSON.stringify(data.loginUser));
          localStorage.setItem("supplier-token",data.token);

          this.startupSrv.load().then(() => {
            this.router.navigate(['/dashboard']);
           });
        } else if (data.result == 'FAILURE') {
          this.error = data.message ? data.message : "账户名或密码错误";
          return;
        } else {
          this.error = "系统异常，请联系管理员处理";
          return;
        }
      })
    }

    // region: social


    // endregion

    ngOnDestroy(): void {
        if (this.interval$) clearInterval(this.interval$);
    }
}
