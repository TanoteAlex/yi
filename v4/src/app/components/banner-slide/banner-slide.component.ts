import {Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {IonSlides, NavController} from "@ionic/angular";

@Component({
    selector: 'banner-slide',
    templateUrl: './banner-slide.component.html',
    styleUrls: ['./banner-slide.component.scss']
})
export class BannerSlideComponent implements OnChanges {

    @Input()
    banners = [];

    @Input() height:string='100%';

    @ViewChild('slides') slides;

    slideOpts = {
        // effect: 'flip',  //轮播效果
        autoplay: {
            delay: 6000,
            disableOnInteraction: false,
            waitForTransition:false
        },

    };

    timer;

    constructor(public navCtrl: NavController,) {
    }

    ionViewWillEnter() {
        // this.slides.startAutoplay();

    }

    ionViewWillLeave() {
        // this.slides.stopAutoplay();
    }

    ngAfterViewInit() {
        // this.autoPlay()
        // this.slides.startAutoplay();
    }

    ngOnDestroy() {
        clearTimeout(this.timer);
    }

    slideChange(event){
        //this.slides.update();
    }

    autoPlay() {
        let _home = this;

        // this.timer = setTimeout(() => {
        //     if (_home.slides) {
        //         _home.slides.isEnd().then(data => {
        //             if (data) {
        //                 _home.slides.slideTo(0);
        //             } else {
        //                 _home.slides.slideNext(100);
        //             }
        //         })
        //     }
        //     this.autoPlay();
        // }, 5000);
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.banners.currentValue) {
            this.banners = changes.banners.currentValue.map(e => {
                return {
                    filePath: e.imgPath ? e.imgPath : e.filePath,
                    linkType: e.linkType,
                    linkId: e.linkId
                }
            })
        }
    }

    goUrl(banner) {
        // 1商品2文章
        if (banner.linkType == 1) {
            window.location.href = './#/app/tabs/home';
            setTimeout(() => {
                this.navCtrl.navigateForward(["CommodityPage", {id: banner.linkId}])
            }, 100)
        } else if (banner.linkType == 2) {
            window.location.href = './#/app/tabs/home';
            setTimeout(() => {
                this.navCtrl.navigateForward(["ArticleDetailPage", {"articleId": banner.linkId}])
            }, 100)
        } else {
            return
        }
    }
}
