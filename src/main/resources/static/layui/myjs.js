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

function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

/** 表单序列化成json字符串的方法  */
function form2JsonString(formId) {
    var paramArray = $('#' + formId).serializeArray();
    /*请求参数转json对象*/
    var jsonObj={};
    $(paramArray).each(function(){
        jsonObj[this.name]=this.value;
    });
    console.log(jsonObj);
    // json对象再转换成json字符串
    return JSON.stringify(jsonObj);
}


// $(function () {
//     checkServer();
// });

function checkServer(){
    $.ajax({
        type: 'POST',
        url: 'useraccount/checekServer',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result,status,xhr) {
            if(xhr.status==10000){
                // layer.alert("网络连接超时，请重新登陆！");
                layer.msg('网络连接超时，请重新登陆！', {
                    offset: '15px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    window.location.href= "login";
                });
            }
        },
        error: function (Requst) {
            if(Requst.status==10000){
                layer.msg('网络连接超时，请重新登陆！', {
                    offset: '15px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    window.location.href= "login";
                });
            }
        }
    });
}


function checkServerSub(){
    $.ajax({
        type: 'POST',
        url: '../useraccount/checekServer',
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result,status,xhr) {
            if(xhr.status==10000){
                // layer.alert("网络连接超时，请重新登陆！");
                layer.msg('网络连接超时，请重新登陆！', {
                    offset: '15px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    window.location.href= "login";
                });
            }
        },
        error: function (Requst) {
            if(Requst.status==10000){
                layer.msg('网络连接超时，请重新登陆！', {
                    offset: '15px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    window.location.href= "login";
                });
            }
        }
    });
}

function  getDaysBetween(dateString1,dateString2){
    var  startDate = Date.parse(dateString1);
    var  endDate = Date.parse(dateString2);
    var days=(endDate - startDate)/(1*24*60*60*1000);
    // alert(days);
    days = parseInt(days);
    return  days;
}

function  getDaysBetweenSecond(dateString1,dateString2){
    var  startDate = Date.parse(dateString1);
    var  endDate = Date.parse(dateString2);
    var senond=(endDate - startDate)/(1000);
    // alert(days);
    senond = parseInt(senond);
    return  senond;
}


function isAssetTypeAnImage(ext) {
    return [
        'png', 'jpg', 'jpeg', 'bmp', 'gif', 'webp', 'psd', 'svg', 'tiff'].
    indexOf(ext.toLowerCase()) !== -1;
}

function checkFilePre(filePath) {
    //获取最后一个.的位置
    var index= filePath.lastIndexOf(".");
    //获取后缀
    var ext = filePath.substr(index+1);
    //判断是否是图片
    return isAssetTypeAnImage(ext);
}

function checkFileHouz(filePath){
    var index= filePath.lastIndexOf(".");
    if(index == -1){
        return false;
    }else{
        return true;
    }
}

function checkNumber(e,txt)

{
    var key = window.event ? e.keyCode : e.which;
    var keychar = String.fromCharCode(key);
    reg = /\d|\./;
    var result = reg.test(keychar);
    if(result)
    {
        //只能输入10位数字
        result = false;
        if(txt.value.length < 10){
            if(e.keyCode==46)
                result=!(txt.value.split('.').length>1);
            else
                result=!(txt.value.split('.').length>1 && txt.value.split('.')[1].length>1);
        }
    }
    if(!result)
    {
        return false;
    }
    else
    {
        return true;
    }
}


function getAge(str){
    var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/);
    if(r==null)return   false;

    var d= new Date(r[1],r[3]-1,r[4]);
    var returnStr = "输入的日期格式错误！";

    if(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]){

        var date = new Date();
        var yearNow = date.getFullYear();
        var monthNow = date.getMonth() + 1;
        var dayNow = date.getDate();

        var largeMonths = [1,3,5,7,8,10,12], //大月， 用于计算天，只在年月都为零时，天数有效
            lastMonth = monthNow -1>0?monthNow-1:12,  // 上一个月的月份
            isLeapYear = false, // 是否是闰年
            daysOFMonth = 0;    // 当前日期的上一个月多少天

        if((yearNow%4===0&&yearNow%100!==0)||yearNow%400===0){  // 是否闰年， 用于计算天，只在年月都为零时，天数有效
            isLeapYear = true;
        }

        if(largeMonths.indexOf(lastMonth)>-1){
            daysOFMonth = 31;
        }else if(lastMonth===2){
            if(isLeapYear){
                daysOFMonth = 29;
            }else{
                daysOFMonth = 28;
            }
        }else{
            daysOFMonth = 30;
        }

        var Y = yearNow - parseInt(r[1]);
        var M = monthNow - parseInt(r[3]);
        var D = dayNow - parseInt(r[4]);
        if(D < 0){
            D = D + daysOFMonth; //借一个月
            M--;
        }
        if(M<0){  // 借一年 12个月
            Y--;
            M = M + 12; //
        }

        if(Y<0){
            returnStr = "出生日期有误！";

        }else if(Y===0){
            if(M===0){
                returnStr = D+"D";
            }else{
                returnStr = M+"M";
            }
        }else{
            if(M===0){
                returnStr = Y+"Y";
            }else{
                returnStr = Y+"Y"+M+"M";
            }
            returnStr = Y;
        }

    }

    if(returnStr == undefined || returnStr =="undefined"){
        returnStr = "";
    }
    return returnStr;
}


function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

function getFormatDate(date) {
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }
    var currentDate = date.getFullYear() + "-" + month + "-" + strDate
        + " " + hour + ":" + minutes + ":" + seconds;
    return currentDate;
}

function getFormatDate() {
    var date = new Date();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }
    var currentDate = date.getFullYear() + "-" + month + "-" + strDate
        + " " + hour + ":" + minutes + ":" + seconds;
    return currentDate;
}


function getNowFormatTime() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var hour = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }
    var currentdate = hour + ":" + minutes + ":" + seconds;
    return currentdate;
}

/*'yyyy-MM-dd HH:mm:ss'格式的字符串转日期*/

function stringToDate(str){

    var tempStrs = str.split(" ");

    var dateStrs = tempStrs[0].split("-");

    var year = parseInt(dateStrs[0], 10);

    var month = parseInt(dateStrs[1], 10) - 1;

    var day = parseInt(dateStrs[2], 10);

    var timeStrs = tempStrs[1].split(":");

    var hour = parseInt(timeStrs [0], 10);

    var minute = parseInt(timeStrs[1], 10);

    var second = parseInt(timeStrs[2], 10);

    var date = new Date(year, month, day, hour, minute, second);

    return date;

}