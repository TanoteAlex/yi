import {Component, Input, OnInit} from '@angular/core';
import {NativeProvider} from "../../../../services/native-service/native";

@Component({
    selector: 'new-time-count',
    templateUrl: './new-time-count.component.html',
    styleUrls: ['./new-time-count.component.scss']
})
export class NewTimeCountComponent {
    @Input()
    endTime: string;

    countTime = {
        day: '0',
        hours: '00',
        min: '00',
        second: '00'
    };

    timer;

    constructor(public nativeProvider: NativeProvider) {

    }

    ngAfterViewInit() {
        if (this.endTime){
            setTimeout(() => this.setEndTime(this.endTime));
        }
    }

    ngOnDestroy() {
        clearTimeout(this.timer)
    }

    /**
     * 开始倒计时
     * @param endTime 年月日 时分秒
     */
    setEndTime(endTime) {
        let currentTime = new Date().getTime();
        endTime = new Date(endTime.replace(/-/g, "/")).getTime();
        let total = Math.floor((endTime - currentTime) / 1000);
        if (total < 0) {
            total = 0;
        }

        this.countdown(total);
    }

    private countdown(leaveSeconds: number) {
        if (leaveSeconds == 0) return;
        leaveSeconds--;

        let day = Math.floor(leaveSeconds / (3600 * 24));
        let hours = Math.floor((leaveSeconds % (3600 * 24)) / 3600);
        let min = Math.floor((leaveSeconds % 3600) / 60);
        let second = Math.floor(leaveSeconds % 60);

        this.countTime.day = day+"";
        this.countTime.hours = holdDoubleNum(hours);
        this.countTime.min = holdDoubleNum(min);
        this.countTime.second = holdDoubleNum(second);

        this.timer = setTimeout(() => {
            this.countdown(leaveSeconds);
        }, 1000);

        /*保持两位数*/
        function holdDoubleNum(num) {
            if (num < 10) {
                return "0" + num;
            }
            return "" + num;
        }
    }

}
