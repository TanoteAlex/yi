import {Component, OnInit, ViewChild} from '@angular/core';

const RED_COLOR='#ff334b';
const BOTTOM_LINE='#999';
const LADDER_COLOR='#ffea02';
const TEXT_COLOR='#4056ae';

@Component({
    selector: 'app-cashback-group-purchase',
    templateUrl: './cashback-group-purchase.page.html',
    styleUrls: ['./cashback-group-purchase.page.scss'],
})

export class CashbackGroupPurchasePage implements OnInit {

    cashback = [
        {num: 3, price: 200},
        {num: 4, price: 180},
        {num: 5, price: 160},
        // {num: 6, price: 140},
        // {num: 7, price: 120},
        // {num: 8, price: 100},
    ];

    @ViewChild('canvas') public canvas;
    canvasElement;

    constructor() {
    }

    ngOnInit() {

        this.draw();
    }

    draw() {
        let num = this.cashback.length;

        /*x轴价位点的长度*/
        let pointLength = 20 + (30 / num);

        /*左上绘图区域占总比*/
        let paintRateX = 0.6;
        let paintRateY = 0.6;

        /*箭头超过x轴长度*/
        let overLength = 5;

        /*获取canvas大小，阶梯数量*/
        this.canvasElement = this.canvas.nativeElement;
        let canvasWidth = this.canvasElement.width;
        let canvasHeight = this.canvasElement.height;

        /*底部x轴绘制*/
        let bottomLine = this.canvasElement.getContext('2d');
        bottomLine.beginPath();
        bottomLine.strokeStyle = BOTTOM_LINE;
        bottomLine.moveTo(0,canvasHeight-overLength);
        bottomLine.lineTo(canvasWidth,canvasHeight-overLength);
        bottomLine.closePath();
        bottomLine.stroke();

        /*价位点坐标*/
        let priceArray = [];
        for(let i = 0; i < num; i ++) {
            priceArray.push({x: (canvasWidth * paintRateX - pointLength) * i / (num - 1) + pointLength/2, y: canvasHeight * paintRateY * (i + 1) / num});
        }

        /*折线的折点坐标*/
        let pointArray = [];
        for(let i = 0; i< priceArray.length; i++) {
            pointArray.push({x: priceArray[i].x - pointLength/2, y: priceArray[i].y});
            pointArray.push({x: priceArray[i].x + pointLength/2, y: priceArray[i].y});
        }

        /*虚线绘制,金额人数*/
        let dashed = this.canvasElement.getContext('2d');
        let text = this.canvasElement.getContext('2d');
        for (let i = 0;i < priceArray.length; i++) {
            dashed.beginPath();
            dashed.moveTo(priceArray[i].x, priceArray[i].y);
            dashed.lineTo(priceArray[i].x, canvasHeight-overLength);
            dashed.strokeStyle = BOTTOM_LINE;
            dashed.setLineDash([3]);
            dashed.lineWidth = 1;
            dashed.stroke();

            text.font = 'normal normal normal 16px sans-serif';
            text.fillStyle = TEXT_COLOR;
            text.fillText('￥'+ this.cashback[i].price , priceArray[i].x - 15, priceArray[i].y - 10);
            text.font = 'normal normal normal 12px sans-serif';
            text.fillText( this.cashback[i].num + '人' ,priceArray[i].x + 5, canvasHeight-overLength - 10);
        }

        /*折线绘制*/
        let polygonal = this.canvasElement.getContext('2d');
        polygonal.beginPath();
        polygonal.moveTo(pointArray[0].x,pointArray[0].y);
        for(let i = 1; i<pointArray.length;i++) {
            polygonal.lineTo(pointArray[i].x,pointArray[i].y);
        }
        polygonal.lineWidth = 5;
        polygonal.setLineDash([]);
        polygonal.strokeStyle = RED_COLOR;
        polygonal.lineCap = 'round';
        polygonal.stroke();

        /*箭头坐标，以arrowhead为基准绘制*/
        let arrowhead = {x: canvasWidth - 25, y: canvasHeight-overLength};
        let arrowheadArray = [];
        arrowheadArray.push({x: canvasWidth * paintRateX,y: canvasHeight * paintRateY});
        arrowheadArray.push({x: arrowhead.x-30,y: arrowhead.y-40});
        arrowheadArray.push({x: arrowhead.x-20,y: arrowhead.y-60});
        arrowheadArray.push(arrowhead);
        arrowheadArray.push({x: arrowhead.x-60,y: arrowhead.y-20});
        arrowheadArray.push({x: arrowhead.x-40,y: arrowhead.y-30});
        arrowheadArray.push({x: canvasWidth * paintRateX,y: canvasHeight * paintRateY});

        /*箭头绘制*/
        let arrowheadCxt = this.canvasElement.getContext('2d');
        arrowheadCxt.beginPath();
        arrowheadCxt.moveTo(arrowheadArray[0].x, arrowheadArray[0].y);
        for (let i = 1;i<arrowheadArray.length; i++) {
            arrowheadCxt.lineTo(arrowheadArray[i].x, arrowheadArray[i].y);
        }
        arrowheadCxt.strokeStyle = RED_COLOR;
        arrowheadCxt.fillStyle = RED_COLOR;
        arrowheadCxt.lineWidth = 5;
        arrowheadCxt.fill();
        arrowheadCxt.stroke();

        /**
         * 阶梯价绘制
         */
        /*阶梯价相对基准点位置*/
        let ladderShiftX = 35;
        let ladderShiftY = 35;
        let squarenessWidth = 50;
        let squarenessHeight = 25;
        let ladderPrice = this.canvasElement.getContext('2d');
        ladderPrice.fillRect(canvasWidth * paintRateX + ladderShiftX, canvasHeight * paintRateY - ladderShiftY, squarenessWidth, squarenessHeight);
        drawRoundRect(ladderPrice, canvasWidth * paintRateX + ladderShiftX, canvasHeight * paintRateY - ladderShiftY, squarenessWidth, squarenessHeight, 3);
        ladderPrice.strokeStyle = RED_COLOR;
        ladderPrice.stroke();

        /*阶梯价的小箭头*/
        let arrow = this.canvasElement.getContext('2d');
        arrow.moveTo(canvasWidth * paintRateX + ladderShiftX - 15, canvasHeight * paintRateY - ladderShiftY + squarenessHeight);
        arrow.lineTo(canvasWidth * paintRateX + ladderShiftX,  canvasHeight * paintRateY - ladderShiftY + squarenessHeight/3);
        arrow.lineTo(canvasWidth * paintRateX + ladderShiftX,  canvasHeight * paintRateY - ladderShiftY + squarenessHeight*5/6);
        arrow.closePath();
        arrow.fill();

        let ladderPriceText = this.canvasElement.getContext('2d');
        ladderPriceText.font = 'normal normal normal 14px sans-serif';
        ladderPriceText.fillStyle = LADDER_COLOR;
        ladderPriceText.fillText('阶梯价', canvasWidth * paintRateX + ladderShiftX + 5, canvasHeight * paintRateY - ladderShiftY/2);

        /*实现圆边的方法*/
        function drawRoundRect(cxt, x, y, width, height, radius){
            cxt.beginPath();
            cxt.arc(x + radius, y + radius, radius, Math.PI, Math.PI * 3 / 2);
            cxt.lineTo(width - radius + x, y);
            cxt.arc(width - radius + x, radius + y, radius, Math.PI * 3 / 2, Math.PI * 2);
            cxt.lineTo(width + x, height + y - radius);
            cxt.arc(width - radius + x, height - radius + y, radius, 0, Math.PI / 2);
            cxt.lineTo(radius + x, height +y);
            cxt.arc(radius + x, height - radius + y, radius, Math.PI / 2, Math.PI);
            cxt.closePath();
        }

        /**
         * 右下箭头碎片
         * 基准点 let arrowhead = {x: canvasWidth - 25, y: canvasHeight-overLength};
         */
        // let pieceOne = this.canvasElement.getContext('2d');
        // pieceOne.moveTo(canvasWidth - 30, canvasHeight-overLength);
        // pieceOne.lineTo(canvasWidth - 40, canvasHeight-overLength-5);
        // pieceOne.lineTo(canvasWidth - 38, canvasHeight-overLength-11);
        // pieceOne.strokeStyle = RED_COLOR;
        // pieceOne.closePath();
        // pieceOne.fill();
        // pieceOne.stroke();


    }

}