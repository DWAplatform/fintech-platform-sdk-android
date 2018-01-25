package com.dwaplatform.android.enterprise.models

data class EnterpriseDocs(val id: String,
                          val doctype: String?= "IDENTITY_PROOF",
                          val pages: ArrayList<String?>?= null)