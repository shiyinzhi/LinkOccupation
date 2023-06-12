

function GetUrlParam(paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");

    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;

        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");

            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}

function GetUrlParamAll() {
    var url = document.location.toString();
    var arrUrl = url.split("?");

    var para = arrUrl[1];
    return para;
}

$(function () {
    checkServerSub();
});
function checkServerSub(){
    $.ajax({
        type: 'POST',
        url: '../worker/checekServer',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        // data: JSON.stringify(data.field),
        success: function (result,status,xhr) {
            if(xhr.status==1000){
                // windows.location.href = 'login'; //后台主页
                // window.location.reload()
                window.top.location.href= "login";
            }
        },
        error: function (Requst) {
            if(Requst.status==1000){
                window.top.location.href= "login";
            }
        }
    });
}