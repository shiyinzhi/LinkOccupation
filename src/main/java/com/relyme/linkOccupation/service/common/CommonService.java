package com.relyme.linkOccupation.service.common;

import com.relyme.linkOccupation.service.useraccount.dao.UserAccountDao;
import com.relyme.linkOccupation.service.useraccount.domain.UserAccount;
import com.relyme.linkOccupation.utils.exception.SyzException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonService {

    @Autowired
    UserAccountDao userAccountDao;

    /**
     * 通过uuid 查询用户信息
     * @param userUuid
     * @throws Exception
     */
    public void checkUser(String userUuid) throws Exception{

        if(userUuid == null || userUuid.isEmpty() || userUuid.trim().length() < 36){
            throw new SyzException("请先登录！");
        }

        UserAccount userAccount = userAccountDao.findByUuid(userUuid);
        if(userAccount == null){
            throw new SyzException("用户信息异常");
        }
    }

}
