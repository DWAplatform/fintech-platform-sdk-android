package com.dwaplatform.android.enterprise.models

import com.dwaplatform.android.enterprise.db.documents.DocumentPages

data class EnterpriseDocs(val id: String,
                          val doctype: String?= "IDENTITY_PROOF",
                          val pages: ArrayList<String?>?= null)