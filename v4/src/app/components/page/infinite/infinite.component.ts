import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PageQuery} from "../../../../util/page-query.model";
import {HttpServiceProvider} from "../../../../services/http-service/http-service";

@Component({
    selector: 'app-infinite',
    templateUrl: './infinite.component.html',
    styleUrls: ['./infinite.component.scss']
})
export class InfiniteComponent implements OnInit {
    @Input()
    pageQuery: PageQuery;
    @Input()
    provider: HttpServiceProvider;
    @Input()
    interfaceName: string;

    @Output()
    result = new EventEmitter();

    constructor() {
    }

    ngOnInit() {
    }

    /**
     * 下拉加载
     * @param infiniteScroll
     */
    doInfinite(infiniteScroll) {
        if (this.pageQuery.page == 0 || (!this.pageQuery.isLast() && this.pageQuery.page < 5)) {
            this.pageQuery.plusPage();
            this.provider[this.interfaceName](this.pageQuery.requests).then(data => {
                    this.pageQuery.covertResponses(data);
                    this.result.emit({data:data.content,pageQuery: this.pageQuery})
                    infiniteScroll.target.complete();
                },
                err => infiniteScroll.target.complete()
            );
        } else {
            infiniteScroll.target.complete();
        }
    }

}
