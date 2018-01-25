package com.fintechplatform.android.enterprise.db.enterprise

import com.raizlabs.android.dbflow.sql.language.SQLite

class EnterpriseDB {
    fun findEnterprise(): Enterprise? {
        return SQLite.select().from(Enterprise::class.java).querySingle()
    }

    fun deleteEnterprise() {
        SQLite.delete().from(Enterprise::class.java).execute()
    }

    fun saveEnterprise(enterprise: Enterprise) {
        enterprise.save()
    }
}