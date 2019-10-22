import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {NativeProvider} from "../../services/native-service/native";
import {inputHandle} from "../../util/bug-util";

@Component({
    selector: 'app-change-payment-pwd',
    templateUrl: './change-payment-pwd.page.html',
    styleUrls: ['./change-payment-pwd.page.scss'],
})
export class ChangePaymentPwdPage {

    commonForm: FormGroup;

    haspaypsw: boolean = false;

    a

    constructor(public navCtrl: NavController,
                public fb: FormBuilder,
                public memberProvider: MemberProvider,
                public nativeProvider: NativeProvider,) {
        // console.log(document.getElementsByTagName('input'));
        // this.a = document.getElementsByTagName('input')
    }

    ngOnInit(): void {
        this.commonForm = this.fb.group({
            oldword: [""],
            password: ["", Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(20)])],
            configword: ["", Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(20)])]
        });
        inputHandle();

    }

    ionViewWillEnter() {
        this.haspaypsw = MemberProvider.getLoginMember().payPassword != null;
    }

    goAccountsSecurity() {
        if (this.commonForm.value.password === this.commonForm.value.configword) {
            if (this.commonForm.value.oldword === MemberProvider.getLoginMember().payPassword || MemberProvider.getLoginMember().payPassword == null) {
                if (this.commonForm.valid) {
                    let memberBo = {
                        id: MemberProvider.getLoginMember().id,
                        payPassword: this.commonForm.value.oldword,
                        newPayPassword: this.commonForm.value.password,
                    };
                    this.memberProvider.modifyPayPassword(memberBo).then(e => {
                        if (e.result == "SUCCESS") {
                            this.memberProvider.setLoginMember(e.data);
                            this.nativeProvider.showToastFormI4("设置支付密码成功", ()=>{
                                this.navCtrl.goBack();
                            });
                        } else {
                            this.nativeProvider.showToastFormI4("修改失败，请稍后重试")
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
