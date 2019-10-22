import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from "../../services/native-service/native";
import {NavController} from "@ionic/angular";
import {ActivatedRoute} from "@angular/router";
import {inputHandle} from "../../util/bug-util";

@Component({
    selector: 'app-change-pwd',
    templateUrl: './change-pwd.page.html',
    styleUrls: ['./change-pwd.page.scss'],
})
export class ChangePwdPage {

    commonForm: FormGroup;

    constructor(public fb: FormBuilder, public memberProvider: MemberProvider, public nativeProvider: NativeProvider, public navCtrl: NavController,
                public route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.commonForm = this.fb.group({
            oldword: ["", Validators.compose([Validators.required])],
            password: ["", Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(20)])],
            configword: ["", Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(20)])]
        });

        inputHandle();
    }

    /*防止重复提交*/
    submit: boolean = false;

    goPersonalCenter() {
        if (this.commonForm.value.password === this.commonForm.value.configword) {
            if (this.commonForm.value.oldword === MemberProvider.getLoginMember().password) {
                if (this.commonForm.valid) {
                    let memberBo = {
                        id: MemberProvider.getLoginMember().id,
                        password: this.commonForm.value.password
                    };
                    this.memberProvider.changePwd(memberBo).then(e => {
                        if (e.result == "SUCCESS") {
                            this.nativeProvider.showToastFormI4("密码修改成功", () => {
                                this.memberProvider.setLoginMember(e.data);
                                this.navCtrl.goBack();
                            });
                        } else {
                            this.nativeProvider.showToastFormI4("密码修改失败，请稍后重试")
                        }
                    }, err => {
                        this.nativeProvider.showToastFormI4(err.message)
                    });
                } else {
                    this.nativeProvider.showToastFormI4("密码格式不正确，请输入6-20位数字或字母");
                    return
                }
            } else {
                this.nativeProvider.showToastFormI4("旧密码错误，请重新输入")
            }
        } else {
            this.nativeProvider.showToastFormI4("两次密码不一致")
        }
    }

}
