package com.fintechplatform.ui.transactions.db

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = PlatformDB::class)
data class Transaction(@PrimaryKey var id: String = "",
                       @Column var who: String = "",
                       @Column var what: String = "",
                       @Column var twhen: String = "",
                       @Column var amount: String = "",
                       @Column var order: Long = 0L,
                       @Column var status: String = "",
                       @Column var resultcode: String = "",
                       @Column var message: String? = null,
                       @Column var error: String? = null,
                       @Column var accountId: String = "") : BaseModel()