export function hashChange(_this) {
    window.addEventListener('hashchange', function () {
        setTimeout(function () {
            var url = window.location.toString();
            var id = url.split("#")[2];
            if (id) {
                if (document.getElementById(id)) {
                    var t = document.getElementById(id).offsetTop;
                    _this.content.scrollByPoint(0, t, 0);
                }
            }
        }, 150)
    });
}