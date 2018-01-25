package com.dwaplatform.android.profile.db.documents

import com.dwaplatform.android.profile.models.UserDocuments
import javax.inject.Inject

class DocumentsPersistanceDB @Inject constructor(val dbDocs: DocumentsDB){
    fun getDocuments(): UserDocuments? {
        val docs = dbDocs.findDocs()
        return docs?.let {
            val documents = arrayOf(it.frontPage, it.backPage)
            UserDocuments(
                    doctype = it.doctype,
                    pages = documents
            )
        }
    }

    fun saveDocuments(docs: UserDocuments) {
        val documents = Documents()
        documents.doctype = docs.doctype
        docs.pages?.let {
            documents.frontPage = it[0]
            documents.backPage = it[1]
        }

        return dbDocs.saveDocs(documents)
    }

    fun replaceDocuments(userDocuments: UserDocuments) {
        dbDocs.deleteDocuments()
        return saveDocuments(userDocuments)
    }
}