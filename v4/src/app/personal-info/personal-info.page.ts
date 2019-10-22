import {Component, OnInit, ViewChild} from '@angular/core';
import {Member} from "../../domain/original/member.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FileServiceProvider} from "../../services/file-service/file-service";
import {NativeProvider} from "../../services/native-service/native";
import {ActivatedRoute} from "@angular/router";
import {Events, NavController} from "@ionic/angular";
import {MemberProvider} from "../../services/member-service/member";
import {DistrictsComponent} from "../components/districts/districts.component";
import {REFRESH_CUSTOMERCENTER} from "../Constants";
import {AppConfig} from "../app.config";

@Component({
    selector: 'app-personal-info',
    templateUrl: './personal-info.page.html',
    styleUrls: ['./personal-info.page.scss'],
})
export class PersonalInfoPage implements OnInit {

    personalData: Member;
    personalForm: FormGroup;

    multiCity;

    @ViewChild(DistrictsComponent) childDistricts: DistrictsComponent;
    @ViewChild('avatarImage') avatarImage;
    @ViewChild('avatarCamera') avatarCamera;

    constructor(public nativeProvider: NativeProvider, public fb: FormBuilder, public memberProvider: MemberProvider, public navCtrl: NavController,
                public route: ActivatedRoute, public fileServiceProvider: FileServiceProvider, public events: Events, public appConfig: AppConfig) {
        this.personalData = MemberProvider.getLoginMember();
        this.multiCity = this.personalData.province + ' ' + this.personalData.city + ' ' + this.personalData.district
    }

    ngOnInit(): void {
        this.personalForm = this.fb.group({
            id: [''],
            nickname: ['', Validators.compose([Validators.required])],
            sex: [''],
            birthday: [''],
            address: [''],
            province: [''],
            city: [''],
            district: [''],
        });
        this.personalForm.patchValue({
            id: MemberProvider.getLoginMember().id,
            nickname: this.personalData.nickname,
            sex: this.personalData.sex + "",
            address: this.personalData.address,
            province: this.personalData.province,
            city: this.personalData.city,
            district: this.personalData.district
        });
        if (this.personalData.birthday) {
            this.personalForm.patchValue({
                birthday: this.personalData.birthday.substr(0, 10),
            })
        }
    }

    ionViewWillLeave() {
        this.events.publish(REFRESH_CUSTOMERCENTER)
    }

    submit: boolean = false;

    goPersonalCenter() {
        //地址
        this.personalForm.patchValue({
            province: this.childDistricts.provinceCode,
            city: this.childDistricts.cityCode,
            district: this.childDistricts.districtCode,
        });

        //生日
        if (this.personalForm.value.birthday.hasOwnProperty("year")) {
            this.personalForm.value.birthday = `${this.personalForm.value.birthday.year.text}-${this.personalForm.value.birthday.month.text}-${this.personalForm.value.birthday.day.text}`;
        }

        if (!this.personalForm.valid) {
            this.nativeProvider.showToastFormI4("昵称未填写");
            return;
        }
        if (this.submit) {
            this.nativeProvider.showToastFormI4("保存中...");
            return;
        }
        let personalDataBo = this.personalForm.value;
        personalDataBo.avater = this.personalData.avater;
        this.memberProvider.changMember(personalDataBo).then(e => {
            if (e.result == "SUCCESS") {
                this.nativeProvider.showToastFormI4("修改成功", () => {
                    this.memberProvider.setLoginMember(e.data);
                    this.navCtrl.goBack();
                });
            } else {
                this.nativeProvider.showToastFormI4(e.message);
            }
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
        });
    }

    resizeMe(img: any) {
        //压缩的大小
        var max_width = 100;
        var max_height = 100;

        var canvas = document.createElement('canvas');
        var width = img.width;
        var height = img.height;
        if (width > height) {
            if (width > max_width) {
                height = Math.round(height *= max_width / width);
                width = max_width;
            }
        } else {
            if (height > max_height) {
                width = Math.round(width *= max_height / height);
                height = max_height;
            }
        }

        canvas.width = width;
        canvas.height = height;

        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, width, height);
        //压缩率
        return canvas.toDataURL("image/jpeg", 0.92);
    }

    changeAvatar() {
        if (this.appConfig.isApp) {
            this.changeAvatarforApp();
        }else{
            let _this = this;
            document.getElementById('avatarCamera').onchange = function (e: any) {
                let file: any = document.getElementById("avatarCamera");
                // console.log(file);
                let fileReader = new FileReader();
                fileReader.readAsDataURL(file.files[0]);
                fileReader.onload = (e: any) => {
                    // console.log(e.target.result);
                    // _this.personalData.avater = e.target.result;
                    // console.log(e.target.result);
                    let img = new Image()
                    img.src = e.target.result
                    img.onload = e => {
                        var resized = _this.resizeMe(img)
                        // console.log(resized);
                        _this.fileServiceProvider.uploadBrowers(resized).then(data => {
                            if (data.result == 'SUCCESS') {
                                _this.personalData.avater = data.data[0].url;
                                _this.nativeProvider.showToastFormI4("上传头像成功");
                            } else {
                                _this.nativeProvider.showToastFormI4("上传头像失败");
                            }
                        })
                    }
                }
            }
            document.getElementById('avatarImage').onchange = function (e: any) {
                let file: any = document.getElementById("avatarImage");
                // console.log(file);
                let fileReader = new FileReader();
                fileReader.readAsDataURL(file.files[0]);
                fileReader.onload = (e: any) => {
                    // console.log(e.target.result);
                    let img = new Image()
                    img.src = e.target.result
                    img.onload = e => {
                        var resized = _this.resizeMe(img)
                        // console.log(resized);
                        _this.fileServiceProvider.uploadBrowers(resized).then(data => {
                            if (data.result == 'SUCCESS') {
                                _this.personalData.avater = data.data[0].url;
                                _this.nativeProvider.showToastFormI4("上传头像成功");
                            } else {
                                _this.nativeProvider.showToastFormI4("上传头像失败");
                            }
                        })
                    }
                }
            }
            this.nativeProvider.getPhoto().subscribe(data => {
                // console.log(data);
                // alert(JSON.stringify(data));
                if (data == 'camera') {
                    this.avatarCamera.nativeElement.click()
                } else if (data == 'image') {
                    this.avatarImage.nativeElement.click()
                }
            })
        }

    }

    changMultiCity(data){
        this.multiCity = data;
    }

    
    changeAvatarforApp() {
        this.nativeProvider.getPhoto().subscribe(data => {
            console.log(data);
            if (this.nativeProvider.isIos()) {
                this.fileServiceProvider.uploadFileIOS(data).then(data => {
                    if (data.result == 'SUCCESS') {
                        this.personalData.avater = data.data[0].url;
                        this.nativeProvider.showToastFormI4("上传头像成功");
                    } else {
                        this.nativeProvider.showToastFormI4("上传头像失败");
                    }
                })
            } else if (this.nativeProvider.isAndroid()) {
                // alert(JSON.stringify(data))
                this.fileServiceProvider.uploadFile(data).then(data1 => {
                    data1=JSON.parse(data1)
                    if (data1.result == 'SUCCESS') {
                        this.personalData.avater = data1.data[0].url;
                        this.nativeProvider.showToastFormI4("上传头像成功");
                    } else {
                        this.nativeProvider.showToastFormI4("上传头像失败");
                    }
                },err=>{
                    alert(JSON.stringify(err))
                })
            }
        })
    }
}
