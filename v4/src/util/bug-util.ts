/**
 * 解决ios，在input框弹出键盘后，input框位置实际不能复原的bug
 */
export function  inputHandle(){
    let inputs =  document.getElementsByTagName("input");
    for(let i = 0;i<inputs.length;i++){
        let item = inputs[i];
        item.addEventListener('blur',function(){
            setTimeout(function(){
                window.scrollTo(0, 0)
            },100)
        })
    }
}

export function scrollToZero() {
    setTimeout(function(){
        window.scrollTo(0, 0)
    },100)
}
