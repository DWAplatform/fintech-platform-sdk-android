package com.fintechplatform.android.enterprise.db.documents

import com.fintechplatform.android.enterprise.models.EnterpriseDocs
import javax.inject.Inject

class EnterpriseDocumentsPersistanceDB @Inject constructor(val documentsDB: EnterpriseDocumentsDB) {

    fun getDocuments(accountId: String): EnterpriseDocs? {
        val docs = documentsDB.findDocuments(accountId)
        return docs?.let {
            val docPages = arrayListOf<String?>()
            if(it.pages.size >0 ) {
                for (i in 0 until it.pages.size) {
                    docPages.add(it.pages[i].page)
                }
            }
            EnterpriseDocs(it.id, it.doctype, docPages)
        }
    }

    fun saveDocuments(documents: EnterpriseDocs) {
        val docs = EnterpriseDocuments()

        docs.doctype = documents.doctype
        docs.id = documents.id
        val listPages = arrayListOf<DocumentPages>()
        documents.pages?.let {

            for (i in 0 until it.size){

                val singlePage = DocumentPages()
                singlePage.page = it[i]?:""
                // dbflow con la ForeignKey lega automaticamente le due tabelle

                // singlePage.enterpriseDocuments.id = it[i]?.documentId
                singlePage.save()
                listPages.add(singlePage)
            }
        }
        docs.pages = listPages

        return documentsDB.saveDocuments(docs)
    }

    fun replaceDocuments(documents: EnterpriseDocs) {
        documentsDB.deleteDocuments()
        return saveDocuments(documents)
    }

}