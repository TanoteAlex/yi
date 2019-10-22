import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {HttpServiceProvider} from "../http-service/http-service";
import {AlertController, ToastController} from "@ionic/angular";
import {AppConfig} from "../../app/app.config";
import {NativeProvider} from "../native-service/native";

import {FilePath} from "@ionic-native/file-path/ngx";
import {File} from "@ionic-native/file/ngx";
import {FileTransfer, FileTransferObject, FileUploadOptions} from "@ionic-native/file-transfer/ngx";

/*
  Generated class for the FileServiceProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class FileServiceProvider extends HttpServiceProvider {


    constructor(private toastCtrl: ToastController, private nativeService: NativeProvider, private filePath: FilePath, private transfer: FileTransfer, private file: File, public appConfig: AppConfig, public http: HttpClient, public alertCtrl: AlertController
                // , public storage: Storage,
    ) {
        super(appConfig, http, alertCtrl, "attachment");
    }


    upload(filePath) {
        const fileTransfer: FileTransferObject = this.transfer.create();
        let options: FileUploadOptions = {
            fileKey: 'file',
            fileName: 'name.jpg',
            headers: {}
        }

        fileTransfer.upload(filePath, this.appConfig.baseURL + 'attachment/upload', options)
            .then((data) => {
                // success
                // alert("success" + JSON.stringify(data))
                return "success"
            }, (err) => {
                // error
                // alert("error" + JSON.stringify(err))
                return "error"
            })
        // const params = new HttpParams().set('memberId', accountId);
        // return this.get("getMember", params);
    }

    /**
     * 上传文件
     * @param path 设备文件路径
     * @param actionUrl 请求路径
     * @param fileName 文件名
     * @returns {Promise<FileUploadResult>}
     */
    async uploadFile(path: string, actionUrl: string = "/attachment/upload.do", fileName: any = ''): Promise<any> {
       return await this.file.resolveLocalFilesystemUrl(path).then(data=>{
            let entry=data
            this.nativeService.showLoading();
            return new Promise((resolve, reject) => {
                entry.getMetadata(metadata => {
                    if (metadata.size > 20971520) {
                        reject("文件太大");
                        this.nativeService.hideLoading();
                        let size = this.toDecimal(metadata.size / 1048576);
                        this.nativeService.showToastFormI4("附件不能超过20M,当前文件" + size + "M!")
                    } else {
                        return this.filePath.resolveNativePath(path).then(filePath => {
                            let name = fileName;
                            // if (fileName == '') {
                            //     name = CompanyServiceProvider.getLoginCompany().accountName;
                            // }

                            let options: FileUploadOptions = {
                                params: null,
                                // fileName: name,
                                // headers: {token: this.globalData.token}
                            };
                            const fileTransfer: FileTransferObject = new FileTransferObject();
                            fileTransfer.onProgress((event) => {

                            });
                            return fileTransfer.upload(path, this.appConfig.baseURL + actionUrl, options).then(
                                result => {
                                    this.nativeService.hideLoading();
                                    // return result.response
                                    resolve(result.response);
                                }
                            ,err=>{
                                    alert(JSON.stringify(err))
                            });
                        },err=>{
                            alert(JSON.stringify(err))
                        })
                            .catch(err => {
                                this.nativeService.hideLoading();
                                alert("reject");
                                reject(err);
                            });
                    }
                })
            });
        },err=>{
            alert(JSON.stringify(err))
        });

    }

    uploadBrowers(body: any) {
        return this.postForm('attachment/uploadBase64.do', new HttpParams().set('base64Str', body), true)

    }

    uploadBrowers1(body: any) {
        return this.postForm('attachment/upload.do', new FormData().append('file', body), true)

    }

    /**
     * 上传文件ios
     * @param path 设备文件路径
     * @param actionUrl 请求路径
     * @param fileName 文件名
     * @returns {Promise<FileUploadResult>}
     */
    async uploadFileIOS(path: string, actionUrl: string = "/attachment/upload.do", fileName: any = ''): Promise<any> {
        let entry = await this.file.resolveLocalFilesystemUrl(path);
        this.nativeService.showLoading();

        return new Promise((resolve, reject) => {
            entry.getMetadata(metadata => {
                if (metadata.size > 20971520) {
                    reject("文件太大");
                    this.nativeService.hideLoading();
                    let size = this.toDecimal(metadata.size / 1048576);
                    this.nativeService.showToastFormI4("附件不能超过20M,当前文件" + size + "M!")
                } else {
                    let options: FileUploadOptions = {
                        params: null,
                    };
                    const fileTransfer: FileTransferObject = new FileTransferObject();
                    fileTransfer.onProgress((event) => {

                    });
                    fileTransfer.upload(path, this.appConfig.baseURL + actionUrl, options).then(
                        result => {
                            this.nativeService.hideLoading();
                            // alert("resolve");
                            resolve(JSON.parse(result.response));
                        }
                    );
                }
            })
        });
    }


    private static getFileType(path: string): string {
        return path.substring(path.lastIndexOf('.') + 1);
    }

    private static getFileName(path: string): string {
        return path.substring(path.lastIndexOf("/") + 1, path.length);
    }

    private toDecimal(x) {
        let f = parseFloat(x);
        if (isNaN(f)) {
            return;
        }
        f = Math.round(x * 100) / 100;
        return f;
    }




}
