import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpServiceProvider} from "../../../../services/http-service/http-service";
import {NativeProvider} from "../../../../services/native-service/native";

@Component({
    selector: 'app-refresh',
    templateUrl: './refresh.component.html',
    styleUrls: ['./refresh.component.scss']
})
export class RefreshComponent implements OnInit {
    @Input()
    provider: HttpServiceProvider;
    @Input()
    interfaceName: string;

    @Output()
    result = new EventEmitter();

    constructor(public nativeProvider: NativeProvider) {
    }

    ngOnInit() {
    }

    /**
     * 上拉刷新
     */
    doRefresh(refresher) {
        this.provider[this.interfaceName]().then(data => {
            this.result.emit(data);
            this.refresherComplete(refresher)
        }, err => {
            this.nativeProvider.showToastFormI4(err.message);
            this.refresherComplete(refresher)
        });
    }

    /**
     * 刷新完成
     * @param refresher
     */
    refresherComplete(refresher) {
        if (refresher) {
            refresher.target.complete()
        }
    }
}
