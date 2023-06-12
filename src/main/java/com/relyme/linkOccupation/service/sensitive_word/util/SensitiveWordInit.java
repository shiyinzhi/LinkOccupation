package com.relyme.linkOccupation.service.sensitive_word.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
/**
 * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @Project：test
 * @Author : SPF
 * @version 1.0
 */
public class SensitiveWordInit {
    private static String ENCODING = "UTF-8"; //字符编码

    @SuppressWarnings("rawtypes")
    public static HashMap sensitiveWordMap;

    public SensitiveWordInit(){
        super();
    }

    /**
     * 初始化词库
     * @param datas 敏感词集合
     * @return
     */
    public static HashMap init(List<String> datas) {
        addSensitiveWord(datas);
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
     * */
    private static void addSensitiveWord(List<String> datas) {
        sensitiveWordMap = new HashMap(datas.size());
        Iterator<String> iterator = datas.iterator();
        Map<String, Object> now = null;
        Map now2 = null;
        while (iterator.hasNext()) {
            now2 = sensitiveWordMap;
            String word = iterator.next().trim(); //敏感词
            for (int i = 0; i < word.length(); i++) {
                char key_word = word.charAt(i);
                Object obj = now2.get(key_word);
                if (obj != null) { //存在
                    now2 = (Map) obj;
                } else { //不存在
                    now = new HashMap<String, Object>();
                    now.put("isEnd","0");
                    now2.put(key_word, now);
                    now2 = now;
                }
                if (i == word.length() - 1) {
                    now2.put("isEnd","1");
                }
            }
        }
    }

    /**
     * 获取内容中的敏感词
     * @param text 内容
     * @param matchType 匹配规则 1=不最佳匹配，2=最佳匹配
     * @return
     */
    public static List<String> getSensitiveWord(String text, int matchType) {
        List<String> words = new ArrayList<String>();
        Map now = sensitiveWordMap;
        int count = 0; //初始化敏感词长度
        int start = 0; //标志敏感词开始的下标
        for (int i = 0; i < text.length(); i++) {
            char key = text.charAt(i);
            now = (Map) now.get(key);
            if (now != null) { //存在
                count++;
                if (count ==1) {
                    start = i;
                }
                if ("1".equals(now.get("isEnd"))) { //敏感词结束
                    now = sensitiveWordMap; //重新获取敏感词库
                    words.add(text.substring(start, start + count)); //取出敏感词，添加到集合
                    count = 0; //初始化敏感词长度
                }
            } else { //不存在
                now = sensitiveWordMap;//重新获取敏感词库
                if (count == 1 && matchType == 1) { //不最佳匹配
                    count = 0;
                } else if (count == 1 && matchType == 2) { //最佳匹配
                    words.add(text.substring(start, start + count));
                    count = 0;
                }
            }
        }
        return words;
    }

    /**
     * 从文件中读取词库中的内容，将内容添加到list集合中
     * @author zhiju.li
     * @date 2019年4月12日 下午2:31:18
     * @return
     * @version 1.0
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static List<String> readSensitiveWordFile() throws Exception{
        List<String> list = null;
        //File file = new File("msc/SensitiveWord.txt"); //读取文件
        File file = new File("D:\\SensitiveWord.txt"); //读取文件
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
        try {
            if(file.isFile() && file.exists()){ //文件流是否存在
                list = new ArrayList<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while((txt = bufferedReader.readLine()) != null){ //读取文件，将文件内容放入到set中
                    String[] strSplit=txt.trim().split(",");
                    for(String str:strSplit){
                        list.add(str);
                    }
                }
            }else{ //不存在抛出异常信息
                throw new Exception("词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        }finally{
            read.close(); //关闭文件流
        }
        return list;
    }

}
