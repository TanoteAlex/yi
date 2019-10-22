import {Component, Input, OnInit} from '@angular/core';
import {NativeProvider} from "../../../services/native-service/native";

@Component({
    selector: 'time-count',
    templateUrl: './time-count.component.html',
    styleUrls: ['./time-count.component.scss']
})
export class TimeCountComponent {
    countTime = {
        hours: "00",
        min: "00",
        second: "00"
    };

    @Input()
    isDefault: boolean = false;

    @Input()
    endTime: string;

    timer;

    constructor(public nativeProvider: NativeProvider) {

    }

    ngAfterViewInit() {
        // if (this.endTime){
        //     setTimeout(() => this.setEndTime(this.endTime));
        // }
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
        endTime = new Date(endTime.replace(/-/g,"/")).getTime();
        let total = (endTime - currentTime) / 1000;
        if(total<0){
            total = 0;
        }
        this.countdown(total)
    }

    /**
     * 传入剩余时间,时：分：秒
     */
    setLeaveTimeHMS(string) {
        let timeArray = string.split(":");
        this.setLeaveTime(timeArray[0]*60*60+timeArray[1]*60+timeArray[2]);
    }

    /**
     * 开始倒计时
     * 传入剩余时间，单位秒
     */
    setLeaveTime(second){
        this.countdown(second)
    }

    private countdown(leaveSeconds) {
        if(leaveSeconds == 0) return;

        leaveSeconds--;
        let hours = Math.floor(leaveSeconds / 3600);
        let min = Math.floor((leaveSeconds % 3600) / 60);
        let second = Math.floor(leaveSeconds % 60);

        this.countTime.hours = this.holdDoubleNum(hours);
        this.countTime.min = this.holdDoubleNum(min);
        this.countTime.second = this.holdDoubleNum(second);

        this.timer = setTimeout(() => {
            this.countdown(leaveSeconds);
        }, 1000);
    }

    /*保持两位数*/
    private holdDoubleNum(num) {
        if (num < 10) {
            return "0" + num;
        }
        return "" + num;
    }


}
