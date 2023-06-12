package com.relyme.linkOccupation.service.custaccount.dto;


import com.relyme.linkOccupation.service.custaccount.domain.CustAccount;
import com.relyme.linkOccupation.service.roster.domain.Roster;
import com.relyme.linkOccupation.utils.bean.BaseEntityForMysql;

/**
 * 用户CustEntInfoDto
 * @author shiyinzhi
 */
public class CustEntInfoDto extends BaseEntityForMysql {

    private CustAccount custAccount;
    private Roster roster;

    public CustAccount getCustAccount() {
        return custAccount;
    }

    public void setCustAccount(CustAccount custAccount) {
        this.custAccount = custAccount;
    }

    public Roster getRoster() {
        return roster;
    }

    public void setRoster(Roster roster) {
        this.roster = roster;
    }
}
