package com.fintechplatform.ui.db

import com.fintechplatform.ui.card.db.PaymentCard
import com.fintechplatform.ui.profile.db.documents.Documents
import com.raizlabs.android.dbflow.annotation.Database
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.config.fintechplatformGeneratedDatabaseHolder
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Database(name = PlatformDB.NAME, version = PlatformDB.VERSION)
class PlatformDB {
    companion object {

        const val NAME = "PlatformDatabase" // we will add the .db extension

        const val VERSION = 2

        val Module = "fintechplatform"

        fun init() {
            FlowManager.initModule(fintechplatformGeneratedDatabaseHolder::class.java)
        }

        fun deleteEverything() {
            FlowManager.getDatabase(PlatformDB::class.java).reset()
        }

        @Migration(version = 2, database = PlatformDB::class)
        class Migration2(table: Class<PaymentCard>) : AlterTableMigration<PaymentCard>(table) {

            override fun onPreMigrate() {
                addColumn(SQLiteType.INTEGER, "isDefault")
            }
        }


        @Migration(version = 3, database = PlatformDB::class)
        class Migration3(table: Class<Documents>) : AlterTableMigration<Documents>(table) {

            override fun onPreMigrate() {
                addColumn(SQLiteType.TEXT, "id")
                addColumn(SQLiteType.TEXT, "userId")
                addColumn(SQLiteType.TEXT, "docType")
                addColumn(SQLiteType.TEXT, "frontPage")
                addColumn(SQLiteType.TEXT, "backPage")
                addColumn(SQLiteType.TEXT, "frontPageId")
                addColumn(SQLiteType.TEXT, "backPageId")
            }
        }

    }
}