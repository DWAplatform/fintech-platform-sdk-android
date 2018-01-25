package com.dwaplatform.android.enterprise.db.documents;

import com.dwaplatform.android.db.PlatformDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PlatformDB.class)
public class DocumentPages extends BaseModel {

    @PrimaryKey (autoincrement = true)
    public int id;
    @Column
    public String page;
    @ForeignKey(stubbedRelationship = true)
    public EnterpriseDocuments enterpriseDocuments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public EnterpriseDocuments getDocuments() {
        return enterpriseDocuments;
    }

    public void setDocuments(EnterpriseDocuments documents) {
        this.enterpriseDocuments = documents;
    }
}
