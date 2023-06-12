
var layer;
layui.use(['element','form'], function() {

    var element = layui.element;
    layer = layui.layer

    element.on('tabDelete(tabDemo)', function(data){
        // console.log(this); //当前Tab标题所在的原始DOM元素
        // console.log(data.index); //得到当前Tab的所在下标
        // console.log(data.elem); //得到当前的Tab大容器
        tab_count--;
    });



    // addTab("首页", "main.html",false);
});

function addTabFormMenu(e){
    var href = $(e).attr("href");
    // $(e).attr("href","javascript:;")
    addTab($(e).html(), href);
    return false;
}

var tab_count = 1;

//添加选项卡
function addTab(name, url) {
    checkServerSub();
    if(layui.jquery(".layui-tab-title li[lay-id='" + name + "']").length > 0) {
        //选项卡已经存在
        layui.element.tabChange('tabDemo', name);
        // layer.msg('切换-' + name)
    } else {
        if(tab_count == 18){
            layer.alert("窗口过多，为了提高工作效率，请关闭一些！");
            return false;
        }
        tab_count++;
        //动态控制iframe高度
        var tabheight = layui.jquery(window).height() - 68;
        var timestamp = (new Date()).valueOf();
    // <iframe src="backtop.html" frameborder="0" scrolling="no" id="test" onload="this.height=100"></iframe>
        contentTxt = '<iframe id="'+timestamp+'" src="' + url + '" width="100%" style="width: 100%" frameborder="0" scrolling="auto" height="'+tabheight+'px"></iframe>';
        //新增一个Tab项
        layui.element.tabAdd('tabDemo', {
            title: name,
            content: contentTxt,
            id: name
        })
        //切换刷新
        layui.element.tabChange('tabDemo', name)
        // layer.msg('新增-' + name)
        // reinitIframe(timestamp);
    }
}


