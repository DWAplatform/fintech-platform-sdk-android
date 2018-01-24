package com.dwaplatform.android.enterprise.db.documents;

import com.dwaplatform.android.db.PlatformDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PlatformDB.class)
public class DocumentPages extends BaseModel {
    @PrimaryKey (autoincrement = true)
    public int id;
    @Column
    public String page;
    @Column
    public String enterpriseDocuments_id;

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

    public String getEnterpriseDocuments_id() {
        return enterpriseDocuments_id;
    }

    public void setEnterpriseDocuments_id(String enterpriseDocuments_id) {
        this.enterpriseDocuments_id = enterpriseDocuments_id;
    }
}
