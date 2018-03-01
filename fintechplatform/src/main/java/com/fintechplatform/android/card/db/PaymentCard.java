package com.fintechplatform.android.card.db;

import com.fintechplatform.android.db.PlatformDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PlatformDB.class)
public class PaymentCard extends BaseModel {
    @PrimaryKey
    public String id;
    @Column
    public String numberAlias;
    @Column
    public String state;
    @Column
    public String expiration;
    @Column
    public String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberAlias() {
        return numberAlias;
    }

    public void setNumberAlias(String numberAlias) {
        this.numberAlias = numberAlias;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
