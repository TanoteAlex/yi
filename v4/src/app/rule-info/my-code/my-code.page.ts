import {Component, OnInit} from '@angular/core';
import {NavController} from "@ionic/angular";

@Component({
    selector: 'app-my-code',
    templateUrl: './my-code.page.html',
    styleUrls: ['./my-code.page.scss'],
})
export class MyCodePage implements OnInit {
    constructor(public navCtrl: NavController) {
        this.checkImgs();
    }

    ngOnInit() {
    }

    checkImgs() {
        const imgs = document.querySelectorAll('.my-photo');
        Array.from(imgs).forEach(el => {
            // if (isInSight(el)) {
                this.loadImg(el);
            // }
        })
    }

    loadImg(el) {
        if (!el.src) {
            const source = el.dataset.src;
            el.src = source;
        }
    }

    // io = new IntersectionObserver(ioes => {
    //     ioes.forEach(ioe => {
    //         const el = ioe.target;
    //         const intersectionRatio = ioe.intersectionRatio;
    //         if (intersectionRatio > 0 && intersectionRatio <= 1) {
    //             this.loadImg(el);
    //         }
    //         el.onload = el.onerror = () => this.io.unobserve(el);
    //     });
    // });




}
