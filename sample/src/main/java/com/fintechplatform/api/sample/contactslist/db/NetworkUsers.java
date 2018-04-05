package com.fintechplatform.api.sample.contactslist.db;

import com.fintechplatform.api.sample.db.SampleDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = SampleDB.class)
public class NetworkUsers extends BaseModel {
    @PrimaryKey
    public String userid;
    @Column
    public String accountid;
    @Column
    public String tenantid;
    @Column
    public String name;
    @Column
    public String surname;
    @Column
    public String photo;
    @Column
    public String aspName;
    @Column
    public String accountType;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getTenantid() {
        return tenantid;
    }

    public void setTenantid(String tenantid) {
        this.tenantid = tenantid;
    }

    public String getAspName() {
        return aspName;
    }

    public void setAspName(String aspName) {
        this.aspName = aspName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
