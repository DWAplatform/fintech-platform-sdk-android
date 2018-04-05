package com.fintechplatform.ui.profile.db.documents;

import com.fintechplatform.ui.db.PlatformDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PlatformDB.class)
public class Documents extends BaseModel {
    @PrimaryKey
    public String id;
    @Column
    public String doctype;
    @Column
    public String frontPage;
    @Column
    public String backPage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getFrontPage() {
        return frontPage;
    }

    public void setFrontPage(String frontPage) {
        this.frontPage = frontPage;
    }

    public String getBackPage() {
        return backPage;
    }

    public void setBackPage(String backPage) {
        this.backPage = backPage;
    }
}
