package com.ilaotan.model;

import java.util.Date;


/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/15 10:21
 */
public class TestModel implements java.io.Serializable{

    private static final long serialVersionUID = 7158911668568000392L;

    private String name;

    private String email;

    private int age;

    private Date loginDate;

    private Date expiryDate;

    private Date updateDate;

    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private int num5;
    private int num6;
    private int num7;

    public final int getNum1() {
        return this.num1;
    }

    public final void setNum1(int num1) {
        this.num1 = num1;
    }

    public final int getNum2() {
        return this.num2;
    }

    public final void setNum2(int num2) {
        this.num2 = num2;
    }

    public final int getNum3() {
        return this.num3;
    }

    public final void setNum3(int num3) {
        this.num3 = num3;
    }

    public final int getNum4() {
        return this.num4;
    }

    public final void setNum4(int num4) {
        this.num4 = num4;
    }

    public final int getNum5() {
        return this.num5;
    }

    public final void setNum5(int num5) {
        this.num5 = num5;
    }

    public final int getNum6() {
        return this.num6;
    }

    public final void setNum6(int num6) {
        this.num6 = num6;
    }

    public final int getNum7() {
        return this.num7;
    }

    public final void setNum7(int num7) {
        this.num7 = num7;
    }

    public final Date getUpdateDate() {
        if (this.updateDate != null) {
            return new Date(this.updateDate.getTime());
        }
        else {
            return null;
        }
    }

    public final void setUpdateDate(Date updateDate) {
        if (updateDate != null) {
            this.updateDate = (Date) updateDate.clone();
        }
        else {
            this.updateDate = null;
        }
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final int getAge() {
        return this.age;
    }

    public final void setAge(int age) {
        this.age = age;
    }

    public final Date getLoginDate() {
        if (this.loginDate != null) {
            return new Date(this.loginDate.getTime());
        }
        else {
            return null;
        }
    }

    public final void setLoginDate(Date loginDate) {
        if (loginDate != null) {
            this.loginDate = (Date) loginDate.clone();
        }
        else {
            this.loginDate = null;
        }
    }

    public final Date getExpiryDate() {
        if (this.expiryDate != null) {
            return new Date(this.expiryDate.getTime());
        }
        else {
            return null;
        }
    }

    public final void setExpiryDate(Date expiryDate) {
        if (expiryDate != null) {
            this.expiryDate = (Date) expiryDate.clone();
        }
        else {
            this.expiryDate = null;
        }
    }
}
