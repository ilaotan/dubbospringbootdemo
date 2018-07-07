package com.ilaotan.entity;

import java.util.Date;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/7/5 14:23
 */
public class SUser {

    private String id;

    private String mobileNum;

    private Date createTime;

    private Date loginTime;

    private String sec;


    public SUser(String id, String mobileNum, Date createTime, Date loginTime, String sec) {
        this.id = id;
        this.mobileNum = mobileNum;
        this.createTime = createTime;
        this.loginTime = loginTime;
        this.sec = sec;
    }

    public final String getSec() {
        return this.sec;
    }

    public final void setSec(String sec) {
        this.sec = sec;
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(String id) {
        this.id = id;
    }


    public final String getMobileNum() {
        return this.mobileNum;
    }

    public final void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public final Date getCreateTime() {
        if (this.createTime != null) {
            return new Date(this.createTime.getTime());
        }
        else {
            return null;
        }
    }

    public final void setCreateTime(Date createTime) {
        if (createTime != null) {
            this.createTime = (Date) createTime.clone();
        }
        else {
            this.createTime = null;
        }
    }

    public final Date getLoginTime() {
        if (this.loginTime != null) {
            return new Date(this.loginTime.getTime());
        }
        else {
            return null;
        }
    }

    public final void setLoginTime(Date loginTime) {
        if (loginTime != null) {
            this.loginTime = (Date) loginTime.clone();
        }
        else {
            this.loginTime = null;
        }
    }
}
