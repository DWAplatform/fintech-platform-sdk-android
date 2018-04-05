package com.fintechplatform.ui.account.balance.db;

import com.fintechplatform.ui.db.PlatformDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PlatformDB.class)
public class Balance extends BaseModel {
    @PrimaryKey
    public String id;
    @Column
    public Long amount;
    @Column
    public String currency;
    @Column
    public String accountId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
