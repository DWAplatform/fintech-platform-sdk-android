package com.fintechplatform.ui.enterprise.db.documents

import com.raizlabs.android.dbflow.sql.language.SQLite

class EnterpriseDocumentsDB {
    fun findDocuments(accountId: String): EnterpriseDocuments? {
        return SQLite.select().from(EnterpriseDocuments::class.java).querySingle()
    }

    fun saveDocuments(documents: EnterpriseDocuments) {
        documents.save()
    }

    fun deleteDocuments() {
        SQLite.delete().from(EnterpriseDocuments::class.java).execute()
    }
}