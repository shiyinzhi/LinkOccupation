package com.relyme.linkOccupation.service.sensitive_word.util;

import com.relyme.linkOccupation.service.sensitive_word.dao.SensitiveWordDao;
import com.relyme.linkOccupation.service.sensitive_word.domain.SensitiveWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
public class LoadSensitiveWord implements CommandLineRunner {

    @Autowired
    SensitiveWordDao sensitiveWordDao;

    //敏感词字典
    private List<String> sensitiveWords = new ArrayList<>();

    public void setSensitiveWords(List<String> sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<SensitiveWord> all = sensitiveWordDao.findByActive(1);
        for (SensitiveWord sensitiveWord : all) {
            sensitiveWords.add(sensitiveWord.getContent());
        }
        SensitiveWordInit.init(sensitiveWords);
    }

    /**
     * 敏感词替换
     * @param content
     * @return
     */
    public String replaceSensitiveWord(String content){
        String word = SensitivewordFilter.replaceSensitiveWord(sensitiveWords, content, 1, "*");
        return word;
    }
}
