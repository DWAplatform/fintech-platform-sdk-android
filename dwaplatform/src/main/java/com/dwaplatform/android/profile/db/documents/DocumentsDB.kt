package com.dwaplatform.android.profile.db.documents

import com.raizlabs.android.dbflow.sql.language.SQLite

/**
 * Created by ingrid on 10/01/18.
 */
class DocumentsDB {
    fun findDocs(): Documents? {
        return SQLite.select().from(Documents::class.java).querySingle()
    }

    fun deleteDocuments() {
        SQLite.delete().from(Documents::class.java).execute()
    }

    fun saveDocs(docs: Documents) {
        docs.save()
    }
}