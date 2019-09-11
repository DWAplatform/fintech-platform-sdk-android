package com.fintechplatform.ui.profile.db.documents

import com.fintechplatform.api.profile.models.DocType
import com.fintechplatform.api.profile.models.UserDocuments
import javax.inject.Inject

class DocumentsPersistanceDB @Inject constructor(val dbDocs: DocumentsDB){
    fun getDocuments(): UserDocuments? {
        val docs = dbDocs.findDocs()
        return docs?.let {
            val documents = arrayListOf(it.frontPage, it.backPage).filterNotNull()
            val documentsIds = arrayListOf(docs.frontPageId, docs.backPageId).filterNotNull()
            val docType = docs.docType?.run { DocType.valueOf(this) }
            UserDocuments(docs.userId, docs.id, docType, documentsIds, documents)
        }
    }

    fun saveDocuments(docs: UserDocuments) {
        val documents = Documents()
        documents.id = docs.documentId
        documents.userId = docs.userId
        documents.docType = docs.docType.toString()
        docs.imagesBase64?.let {
            documents.frontPage = it[0]
            documents.backPage = it[1]
        }
        docs.bucketObjectIdPages?.let { 
            documents.frontPageId = it[0]
            documents.backPageId = it[1]
        }
        return dbDocs.saveDocs(documents)
    }

    fun replaceDocuments(userDocuments: UserDocuments) {
        dbDocs.deleteDocuments()
        return saveDocuments(userDocuments)
    }
}