package com.relyme.linkOccupation.service.sensitive_word.util;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws Exception {
        //从文件中读取词库中的内容，将内容添加到list集合中
//        List<String> datas= SensitiveWordInit.readSensitiveWordFile();
        List<String> datas= new ArrayList<>();
        datas.add("日");
        datas.add("天气");
        //初始化词库,读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
        SensitiveWordInit.init(datas);
        //输入的字符串
        String str = "你好，帮我查看下今天的天气状况信息";
        //List<String> rep = SensitivewordFilter.getSensitiveWord(datas,str,1);
        String rep = SensitivewordFilter.replaceSensitiveWord(datas,str,1,"*");
        System.out.println(rep);

    }

}
