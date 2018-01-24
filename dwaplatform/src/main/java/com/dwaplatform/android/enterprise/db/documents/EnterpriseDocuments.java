package com.dwaplatform.android.enterprise.db.documents;

import android.support.annotation.NonNull;

import com.dwaplatform.android.db.PlatformDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.List;

@Table(database = PlatformDB.class)
public class EnterpriseDocuments extends BaseModel {
    @PrimaryKey
    public String id;
    @Column
    public String doctype;

    @ForeignKey(stubbedRelationship = true)
    public DocumentPages documentPages;

    public List<DocumentPages> pages;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "pages")
    public List<DocumentPages> getPages() {
        SQLite.select()
                .from(DocumentPages.class)
                .where(DocumentPages_Table.enterpriseDocuments_id.eq(id))
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<DocumentPages>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<DocumentPages> tResult) {
                        pages = tResult;
                    }
                })
                .execute();
        return pages;
    }


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

    public void setPages(List<DocumentPages> pages) {
        this.pages = pages;
    }
}
