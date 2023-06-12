// function rememberPassword() {
//     //存储变量
//     this.username = $('#userName').val();
//     //md5 密码
//     this.password = hex_md5($('#passWord').val());
//     this.cookie;
//     if (!!$.cookie('user')) {
//         this.cookie = eval($.cookie('user'));
//     } else {
//         $.cookie('user', '[]');
//         this.cookie = eval($.cookie('user'));
//     };
// }
// rememberPassword.prototype = {
//     cookieInit: function() { //初始化
//         var temp = this.cookie,
//             username = this.username,
//             start = false;
//         console.log(temp);
//         if (temp.length > 0) {
//             $.each(temp, function(i, item) {
//                 if (item.first == true) {
//                     $('#userName').val(item.username);
//                     $('#passWord').val(item.password);
//                     $('#remember-password').attr('checked', true)
//                 }
//             });
//         }
//         $('#userName').blur(function() {
//             console.log('失去焦点');
//             //检查是否存在该用户名,存在则赋值，不存在则不做任何操作
//             $.each(temp, function(i, item) {
//                 if (item.username == $('#userName').val()) {
//                     $('#userName').val(item.username);
//                     $('#passWord').val(item.password);
//                     $('#remember-password').attr('checked', true)
//                     start = true;
//                     return false;
//                 } else {
//                     $('#passWord').val('');
//                 }
//
//             });
//         });
//     },
//     //记住密码
//     cookieRemeber: function() {
//         var temp = this.cookie,
//             username = this.username,
//             password = this.password,
//             start = false;
//         //检测用户是否存在
//         $.each(temp, function(i, item) {
//             if (item.username == username) {
//                 //记录最后一次是谁登录的
//                 item.first = true;
//                 $('#passWord').val(item.password);
//                 start = true;
//                 return;
//             } else {
//                 item.first = false;
//             }
//         });
//         //不存在就把用户名及密码保存到cookie中
//         if (!start) {
//             temp.push({
//                 username: username,
//                 password: password,
//                 first: true
//             });
//         }
//         //存储到cookie中
//         $.cookie('user', JSON.stringify(temp));
//     },
//     //删除密码
//     cookieDelete: function() {
//         var temp = this.cookie,
//             username = this.username,
//             num = 0;
//         //检测用户是否存在
//         $.each(temp, function(i, item) {
//             if (item.username === username) {
//                 num = i;
//             }
//         });
//         //删除里面的密码
//         temp.splice(num, 1);
//         //存储到cookie中
//         $.cookie('user', JSON.stringify(temp));
//     }
// }


$(document).ready(function () {
    loadPwd();

    $('#remember').click(function () {
        if (!$("#remember").is(":checked")) {
            delPassWord();
        }else{
            var userName = $('#LAY-user-login-username').val();
            //md5 密码
            // var passWord = hex_md5($('#LAY-user-login-password').val());
            var passWord = $('#LAY-user-login-password').val();
            savePassWord(userName,passWord);
        }
    });
});

function loadPwd(){
    if($.cookie("rmbUser") == "true"){
        $("#LAY-user-login-username").val($.cookie("userName"));
        $("#LAY-user-login-password").val($.cookie("passWord"));
        $("#remember").attr("checked", true);
        $("#LAY-user-login-password").focus();
    }else{
        $("#LAY-user-login-username").focus();
    }
}

function savePassWord(userName,passWord){
    $.cookie("rmbUser", "true", {expires : 7,path:"/"});
    $.cookie("userName", userName, {expires : 7,path:"/"});
    $.cookie("passWord", passWord, {expires : 7,path:"/"});
}

function delPassWord(){
    // $.cookie("rmbUser", "false", {expires: -1,path:"/"});
    // $.cookie("userName", "", {expires: -1,path:"/"});
    // $.cookie("passWord", "", {expires: -1,path:"/"});
    $.cookie('rmbUser', null,{expires: -1, path: '/' });
    $.cookie("userName", null,{expires: -1, path: '/' });
    $.cookie("passWord", null,{expires: -1, path: '/' });
    // $.removeCookie('rmbUser', { path: '/' });
    // $.removeCookie('userName', { path: '/' });
    // $.removeCookie('passWord', { path: '/' });
}