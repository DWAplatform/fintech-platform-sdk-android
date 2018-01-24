package com.dwaplatform.android.enterprise.db.documents

import com.dwaplatform.android.db.PlatformDB
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction


class DocumentPagesDB {
//    fun findPages(documentId: String): List<DocumentPages?>? {
//        return SQLite.select().from(DocumentPages::class.java).where(DocumentPages_Table.enterpriseDocuments_id.`is`(documentId)).queryList()
//    }

    fun deletePages(page: MutableList<DocumentPages>) {
        page.delete()
    }

    fun savePage(page: DocumentPages) {
        page.save()
    }

    fun savePages(pages: ArrayList<DocumentPages>) {
        FastStoreModelTransaction
                .saveBuilder(FlowManager.getModelAdapter(DocumentPages::class.java))
                        .addAll(pages)
                        .build()
    }

    fun updatePages(pages: ArrayList<DocumentPages>) {
        FastStoreModelTransaction
                .updateBuilder(FlowManager.getModelAdapter(DocumentPages::class.java))
                .addAll(pages)
                .build()
    }
}