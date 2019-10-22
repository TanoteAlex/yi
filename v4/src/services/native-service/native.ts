import {Injectable} from "@angular/core";

import {ADDRESS} from "../../app/Constants";
import {ActionSheetController, AlertController, LoadingController, Platform, ToastController} from "@ionic/angular";
import {SaleOrderItem} from "../../domain/original/sale-order-item.model";

import {Camera, CameraOptions} from "@ionic-native/camera/ngx"
import {ImagePicker} from "@ionic-native/image-picker/ngx";
import {FileOpener} from "@ionic-native/file-opener/ngx";
import {Observable} from "rxjs/internal/Observable";
import {File, FileEntry} from "@ionic-native/file/ngx";
import {StatusBar} from "@ionic-native/status-bar/ngx";

// import {Loading} from "@ionic/angular/dist/types/components/loading/loading";

@Injectable()
export class NativeProvider {
    private loading: any;
    private loadingIsOpen: boolean = false;
    private addressErrorMsg: string = "地址出错了..";

    constructor(private platform: Platform,
                private toastCtrl: ToastController,
                private alertCtrl: AlertController,
                private statusBar: StatusBar,
                // private splashScreen: SplashScreen,
                // private appVersion: AppVersion,
                private camera: Camera,
                // private toast: Toast,
                private file: File,
                // private inAppBrowser: InAppBrowser,
                private imagePicker: ImagePicker,
                // private network: Network,
                // private appMinimize: AppMinimize,
                private loadingCtrl: LoadingController,
                // private globalData: GlobalData,
                private fileOpener: FileOpener,
                // public logger: Logger,
                public loadingController: LoadingController,
                private actionSheetCtrl: ActionSheetController,
    ) {
    }

    async showToastFormI4(msg: string, fun: any = undefined, duration: number = 1000, showCloseButton: boolean = false, position: any = 'middle', closeButtonText: string = '关闭') {
        const toast = await this.toastCtrl.create({
            message: msg,
            duration: duration,
            showCloseButton: showCloseButton,
            position: position,
            closeButtonText: closeButtonText,
        });
        toast.onWillDismiss().then(fun);
        toast.present();
    }

    /**
     * 使用默认状态栏
     */
    statusBarStyleDefault(): void {
        // this.statusBar.styleDefault();
    }

    /**
     * 隐藏启动页面
     */
    splashScreenHide(): void {
        // this.splashScreen.hide();
    }

    /**
     * 获取网络类型 如`unknown`, `ethernet`, `wifi`, `2g`, `3g`, `4g`, `cellular`, `none`
     */
    getNetworkType(): string {
        if (!this.isMobile()) {
            return 'wifi';
        }
        // return this.network.type;
    }

    /**
     * 判断是否有网络
     */
    isConnecting(): boolean {
        return this.getNetworkType() != 'none';
    }

    /**
     * 调用最小化app插件
     */
    minimize(): void {
        // this.appMinimize.minimize()
    }

    /**
     * 通过浏览器打开url
     */
    openUrlByBrowser(url: string): void {
        // this.inAppBrowser.create(url, '_system');
    }

    // /**
    //  * 下载安装app
    //  */
    // downloadApp(): void {
    //     if (this.isIos()) {//ios打开网页下载
    //         this.openUrlByBrowser(APP_DOWNLOAD);
    //     }
    //     if (this.isAndroid()) {//android本地下载
    //         let backgroundProcess = false;//是否后台下载
    //         let alert = this.alertCtrl.create({//显示下载进度
    //             title: '下载进度：0%',
    //             enableBackdropDismiss: false,
    //             buttons: [{
    //                 text: '后台下载', handler: () => {
    //                     backgroundProcess = true;
    //                 }
    //             }
    //             ]
    //         });
    //         alert.present();
    //
    //         const fileTransfer: FileTransferObject = this.transfer.create();
    //         const apk = this.file.externalRootDirectory + `android_${Utils.getSequence()}.apk`; //apk保存的目录
    //
    //         //下载并安装apk
    //         fileTransfer.download(APK_DOWNLOAD, apk).then(() => {
    //             window['install'].install(apk.replace('file://', ''));
    //         }, err => {
    //             alert.dismiss();
    //             this.logger.log(err, 'android app 本地升级失败');
    //             this.alertCtrl.create({
    //                 title: '前往网页下载',
    //                 subTitle: '本地升级失败',
    //                 buttons: [
    //                     {
    //                         text: '确定',
    //                         handler: () => {
    //                             this.openUrlByBrowser(APP_DOWNLOAD);//打开网页下载
    //                         }
    //                     }
    //                 ]
    //             }).present();
    //         });
    //
    //         let timer = null;//由于onProgress事件调用非常频繁,所以使用setTimeout用于函数节流
    //         fileTransfer.onProgress((event: ProgressEvent) => {
    //             let progress = Math.floor(event.loaded / event.total * 100);//下载进度
    //             this.globalData.updateProgress = progress;
    //             if (!backgroundProcess) {
    //                 if (progress === 100) {
    //                     alert.dismiss();
    //                 } else {
    //                     if (!timer) {
    //                         timer = setTimeout(() => {
    //                             clearTimeout(timer);
    //                             timer = null;
    //                             let title = document.getElementsByClassName('alert-title')[0];
    //                             title && (title.innerHTML = `下载进度：${progress}%`);
    //                         }, 1000);
    //                     }
    //                 }
    //             }
    //         });
    //     }
    // }
    /**
     * 是否浏览器环境
     */
    isBrowers(): boolean {
        console.log(this.platform);
        return !(this.isAndroid() || this.isIos());
    }

    /**
     * 是否真机环境
     */
    isMobile(): boolean {
        // return this.platform.is('mobile') && !this.platform.is('mobileweb');
        return this.platform.is('mobile');
    }

    /**
     * 是否android真机环境
     */
    isAndroid(): boolean {
        return this.isMobile() && this.platform.is('android');
    }

    /**
     * 是否ios真机环境
     */
    isIos(): boolean {
        return this.isMobile() && (this.platform.is('ios') || this.platform.is('ipad') || this.platform.is('iphone'));
    }

    alert(title: string, subTitle: string = ""): void {
        this.alertCtrl.create({
            // title: title,
            // subTitle: subTitle,
            buttons: [{text: '确定'}]
        });
    }

    alertConfirm(title: string, subTitle: string = "", inputs: Array<any> = []): Promise<any> {
        return new Promise((resolve, reject) => {
            let prompt = this.alertCtrl.create({
                inputs: inputs,
                buttons: [
                    {
                        text: '取消', handler: data => {
                            reject(data);
                        }
                    },
                    {
                        text: '确定', handler: data => {
                            resolve(data);
                        }
                    }
                ]
            });
            prompt;
        });

    }

    // alert(title: string, subTitle: string = "",): void {
    //     this.alertCtrl.create({
    //         title: title,
    //         subTitle: subTitle,
    //         buttons: [{text: '确定'}]
    //     }).present();
    // }

    /**
     * 统一调用此方法显示提示信息
     * @param message 信息内容
     * @param duration 显示时长
     */
    showToast(message: string = '操作完成', duration: number = 2000): void {
        if (this.isMobile()) {
            // this.toast.show(message, String(duration), 'center').subscribe();
        } else {
            this.toastCtrl.create({
                message: message,
                duration: duration,
                position: 'middle',
                showCloseButton: false
            });
        }
    };

    async showLoadingForI4() {
        this.loading = await this.loadingController.create({
            spinner: null,
            message: '加载中...',
            translucent: true,
            cssClass: 'custom-class custom-loading'
        });
        return await this.loading.present();
    }

    async hideLoadingForI4() {
        this.loadingController.dismiss()
    }

    /**
     * 统一调用此方法显示loading
     * @param content 显示的内容
     */
    showLoading(content: string = ''): void {
        // if (!this.globalData.showLoading) {
        //     return;
        // }
        // if (!this.loadingIsOpen) {
        //     this.loadingIsOpen = true;
        //     this.loading = this.loadingCtrl.create({
        //         content: content
        //     });
        //     this.loading.present();
        //     setTimeout(() => {
        //         this.loadingIsOpen && this.loading.dismiss();
        //         this.loadingIsOpen = false;
        //     }, REQUEST_TIMEOUT);
        // }
    };

    /**
     * 关闭loading
     */
    hideLoading(): void {
        // if (!this.globalData.showLoading) {
        //     this.globalData.showLoading = true;
        // }
        // this.loadingIsOpen && this.loading.dismiss();
        // this.loadingIsOpen = false;
    };

    /**
     * 使用cordova-plugin-camera获取照片
     * @param options
     */
    getPicture(options: CameraOptions = {}): Observable<string> {
        let ops: CameraOptions = Object.assign({
            sourceType: this.camera.PictureSourceType.CAMERA,//图片来源,CAMERA:拍照,PHOTOLIBRARY:相册
            destinationType: this.camera.DestinationType.FILE_URI,//默认返回base64字符串,DATA_URL:base64   FILE_URI:图片路径
            // quality: QUALITY_SIZE,//图像质量，范围为0 - 100
            allowEdit: false,//选择图片前是否允许编辑
            encodingType: this.camera.EncodingType.JPEG,
            // targetWidth: IMAGE_SIZE,//缩放图像的宽度（像素）
            // targetHeight: IMAGE_SIZE,//缩放图像的高度（像素）
            saveToPhotoAlbum: false,//是否保存到相册
            correctOrientation: true//设置摄像机拍摄的图像是否为正确的方向
        }, options);
        return Observable.create(observer => {
            this.camera.getPicture(ops).then((imgData: string) => {
                if (ops.destinationType === this.camera.DestinationType.DATA_URL) {
                    observer.next('data:image/jpg;base64,' + imgData);
                } else {
                    observer.next(imgData);
                }
            }).catch(err => {
                if (err == 20) {
                    this.alert('没有权限,请在设置中开启权限');
                    return;
                }
                if (String(err).indexOf('cancel') != -1) {
                    return;
                }
                // this.logger.log(err, '使用cordova-plugin-camera获取照片失败');
                this.alert('获取照片失败');
            });
        });
    };

    /**
     * 通过拍照获取照片
     * @param options
     */
    getPictureByCamera(options: CameraOptions = {}): Observable<string> {
        let ops: CameraOptions = Object.assign({
            sourceType: this.camera.PictureSourceType.CAMERA,
            destinationType: this.camera.DestinationType.FILE_URI//DATA_URL: 0 base64字符串, FILE_URI: 1图片路径
        }, options);
        return this.getPicture(ops);
    };

    /**
     * 通过图库获取照片
     * @param options
     */
    getPictureByPhotoLibrary(options: CameraOptions = {}): Observable<string> {
        let ops: CameraOptions = Object.assign({
            sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
            destinationType: this.camera.DestinationType.FILE_URI//DATA_URL: 0 base64字符串, FILE_URI: 1图片路径
        }, options);
        return this.getPicture(ops);
    };

    /**
     * 通过图库选择多图
     * @param options
     */
    getMultiplePicture(options = {}): Observable<any> {
        let that = this;
        let ops = Object.assign({
            maximumImagesCount: 6,
            // width: IMAGE_SIZE,//缩放图像的宽度（像素）
            // height: IMAGE_SIZE,//缩放图像的高度（像素）
            // quality: QUALITY_SIZE//图像质量，范围为0 - 100
        }, options);
        return Observable.create(observer => {
            this.imagePicker.getPictures(ops).then(files => {
                let destinationType = options['destinationType'] || 0;//0:base64字符串,1:图片url
                if (destinationType === 1) {
                    observer.next(files);
                } else {
                    let imgBase64s = [];//base64字符串数组
                    for (let fileUrl of files) {
                        that.convertImgToBase64(fileUrl).subscribe(base64 => {
                            imgBase64s.push(base64);
                            if (imgBase64s.length === files.length) {
                                observer.next(imgBase64s);
                            }
                        })
                    }
                }
            }).catch(err => {
                // this.logger.log(err, '通过图库选择多图失败');
                this.alert('获取照片失败');
            });
        });
    };
    async getPhActionSheet(observer){
        let actionSheet =await this.actionSheetCtrl.create({
            buttons: [
                {
                    text: '拍照',
                    // role: 'destructive',
                    icon: 'camera',
                    handler: () => {
                        // observer.next('123');
                        this.getPictureByCamera().subscribe(
                            data => {
                                observer.next(data);
                            },
                            error2 => {
                                alert(JSON.stringify(error2));
                                observer.complete()
                            }
                        );
                    }
                }, {
                    text: '相册',
                    icon: 'image',
                    handler: () => {
                        // observer.next('123');
                        this.getPictureByPhotoLibrary().subscribe(
                            data => {
                                this.showToast(data);
                                observer.next(data);
                            },
                            error2 => {
                                alert(JSON.stringify(error2));
                                observer.complete()
                            });
                    }
                }, {
                    text: '取消',
                    role: 'cancel',
                    handler: () => {
                        observer.complete();
                    }
                }
            ]
        });
        await actionSheet.present();
    }


    async getPhotoActionSheet(observer) {
        return Observable.create(async observer => {
            this.getPhActionSheet(observer)
        });
    }

    getPhoto(): Observable<string> {
        return Observable.create(observer => {
            this.getPhActionSheet(observer)
        })
    }


    /**
     * 根据图片绝对路径转化为base64字符串
     * @param path 绝对路径
     */
    convertImgToBase64(path: string): Observable<string> {
        return Observable.create(observer => {
            this.file.resolveLocalFilesystemUrl(path).then((fileEnter: FileEntry) => {
                fileEnter.file(file => {
                    let reader = new FileReader();
                    reader.onloadend = function (e) {
                        observer.next(this.result);
                    };
                    reader.readAsDataURL(file);
                });
            }).catch(err => {
                // this.logger.log(err, '根据图片绝对路径转化为base64字符串失败');
            });
        });
    }

    // /**
    //  * 获得app版本号,如0.01
    //  * @description  对应/config.xml中version的值
    //  */
    // getVersionNumber(): Observable<string> {
    //     return Observable.create(observer => {
    //         this.appVersion.getVersionNumber().then((value: string) => {
    //             observer.next(value);
    //         }).catch(err => {
    //             this.logger.log(err, '获得app版本号失败');
    //         });
    //     });
    // }
    //
    // /**
    //  * 获得app版本号,如1.01
    //  * @description  对应/config.xml中version的值
    //  * @returns {Promise<number>}
    //  */
    // getVersionNumber(): Promise<string> {
    //     return new Promise((resolve) => {
    //         this.appVersion.getVersionNumber().then((versionNumber: string) => {
    //             resolve(versionNumber);
    //         }).catch(err => {
    //             console.log('getVersionNumber:' + err);
    //         });
    //     });
    // }

    // /**
    //  * 获得app name,如现场作业
    //  * @description  对应/config.xml中name的值
    //  */
    // getAppName(): Observable<string> {
    //     return Observable.create(observer => {
    //         this.appVersion.getAppName().then((value: string) => {
    //             observer.next(value);
    //         }).catch(err => {
    //             // this.logger.log(err, '获得app name失败');
    //         });
    //     });
    // }

    // /**
    //  * 获得app包名/id,如com.kit.ionic2tabs
    //  * @description  对应/config.xml中id的值
    //  */
    // getPackageName(): Observable<string> {
    //     return Observable.create(observer => {
    //         this.appVersion.getPackageName().then((value: string) => {
    //             observer.next(value);
    //         }).catch(err => {
    //             // this.logger.log(err, '获得app包名失败');
    //         });
    //     });
    // }

    // /**
    //  * 检查app是否需要升级
    //  */
    detectionUpgrade(currentVersion: string) {
        // if (currentVersion) {
        //   // this.nativeService.showToast("版本是："+this.version);
        //   this.http.get(AppConfig.baseURL+AppConfig.hostDomain+'/app/update.json').subscribe(
        //     data => {
        //       // data as
        //       let latest_versionNumber: string = data.version;
        //       if (currentVersion != latest_versionNumber) {
        //         this.alertConfirm("发现新版本，是否更新？", "", null).then(
        //           ok => {
        //             this.downloadApp(data.url);
        //           }
        //         )
        //       } else {
        //         this.showToast("已经是最新版本了！");
        //       }
        //     }
        //   );
        // } else {
        //   this.showToast("获取app版本信息失败！");
        // }
    }

    // /**
    //  * 下载安装app
    //  */
    // downloadApp(apk_url: string) {
    //     if (this.isAndroid()) {
    //         let alert = this.alertCtrl.create({
    //             title: '下载进度：0%',
    //             enableBackdropDismiss: false,
    //             buttons: ['后台下载']
    //         });
    //         alert.present();
    //
    //         const fileTransfer: FileTransferObject = new FileTransferObject();
    //         const file: File = new File();
    //         const apk = file.externalRootDirectory + 'isee.apk'; //apk保存的目录
    //
    //         fileTransfer.download(apk_url, apk).then(() => {
    //             this.fileOpener.open(apk, 'application/vnd.android.package-archive').then(() => {
    //                 // file.removeFile(file.tempDirectory, 'yoursunion.apk');
    //             });
    //         });
    //
    //         fileTransfer.onProgress((event: ProgressEvent) => {
    //             let num = Math.floor(event.loaded / event.total * 100);
    //             if (num === 100) {
    //                 alert.dismiss();
    //                 this.showToast("下载成功！安装包保存在" + apk.replace('file://', ''), 3000);
    //             } else {
    //                 let title = document.getElementsByClassName('alert-title')[0];
    //                 title && (title.innerHTML = '下载进度：' + num + '%');
    //             }
    //         });
    //     }
    //
    //     if (this.isIos()) {
    //         this.openUrlByBrowser("");
    //     }
    // }

    /*省市区代码转换成文字*/
    transform(value: string) {
        for (let province in this.address) {
            if (province == value) {
                return this.address[province].name;
            }
            for (let city in this.address[province].child) {
                if (city == value) {
                    return this.address[province].child[city].name;
                }
                for (let district in this.address[province].child[city].child) {
                    if (district == value) {
                        return this.address[province].child[city].child[district]
                    }
                }
            }
        }
        return "";
    }

    address = ADDRESS;

    /**
     *
     * @param consignee 传回的 省市区地址code 转成 文字省市区地址
     * @returns {any}
     */
    transferAddress(consignee) {
        if (consignee.toString().length > 18) {
            let province = consignee.slice(0, 6);
            let city = consignee.slice(6, 12);
            let district = consignee.slice(12, 18);
            let address = consignee.slice(18);
            return this.transform(province) + this.transform(city) + this.transform(district) + address;
        } else {
            return consignee;
        }
    }

    /**
     * 将城市名转成城市code
     * @param Address
     * @returns {string}
     */
    transferAddressToCode(Address) {
        for (let province in this.address) {
            for (let city in this.address[province].child) {
                if (this.address[province].child[city].name == Address) {
                    return city
                }
            }
        }
        return this.addressErrorMsg;
    }

    /**
     *
     * @param Address
     * @returns {string}
     */
    transferAddressTopcd(Address) {
        let AddressCode = this.transferAddressToCode(Address);
        if (AddressCode == this.addressErrorMsg) {
            return this.addressErrorMsg
        }
        return AddressCode.substr(0, 2) + "0000" + ' ' + AddressCode
    }


    /**
     * 将订单内容，转化成组件可用形式的字段，一般用order.productList接收
     * @param {SaleOrderItem[]} saleOrderItems    order.saleOrderItems订单中的商品信息
     * @returns {{id: number; imgPath: string; productName: string; productShortName: string; price: number; quantity: number}[]}
     */
    productListChange(saleOrderItems: SaleOrderItem[]) {
        if (Array.isArray(saleOrderItems)) {
            return saleOrderItems.map(e => {
                return {
                    id: e.product.id,
                    imgPath: e.product.productImgPath,
                    productName: e.product.productName,
                    productShortName: e.product.productShortName,
                    // discountInfo: e.product.commodity.discountInfo,
                    price: e.product.currentPrice,
                    quantity: e.quantity,
                    actualPrice: e.price
                }
            });
        }
    }

    /**
     * endTime与当前时间的差值
     * @param endTime 日期 时分秒
     * @returns {string}  时（可大于24）：分：秒
     */
    remainingTime(endTime) {
        let currentTime = new Date().getTime();
        endTime = new Date(endTime).getTime();
        let total = (endTime - currentTime) / 1000;

        if (total < 0) {
            return '00:00:00'
        }

        let day = Math.floor(total / (24 * 60 * 60));//计算整数天数
        let afterDay = total - day * 24 * 60 * 60;//取得算出天数后剩余的秒数

        let hour = Math.floor(afterDay / (60 * 60));//计算整数小时数
        let afterHour = total - day * 24 * 60 * 60 - hour * 60 * 60;//取得算出小时数后剩余的秒数
        let hourInclude = Math.floor(afterDay / (60 * 60)) + day * 24;//计算整数小时数(没有天数，总小时数

        let min = Math.floor(afterHour / 60);//计算整数分
        let sconends = Math.floor(total - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);//取得算出分后剩余的秒数

        // console.log(`D:${day},H:${holdDoubleNum(hour)},M:${holdDoubleNum(min)},S:${holdDoubleNum(sconends)}`);
        // console.log(`H:${holdDoubleNum(hourInclude)},M:${holdDoubleNum(min)},S:${holdDoubleNum(sconends)}`);

        /*保持两位数*/
        function holdDoubleNum(num) {
            if (num < 10) {
                return "0" + num;
            }
            return "" + num;
        }

        return holdDoubleNum(hourInclude) + ":" + holdDoubleNum(min) + ":" + holdDoubleNum(sconends);
    };


}

