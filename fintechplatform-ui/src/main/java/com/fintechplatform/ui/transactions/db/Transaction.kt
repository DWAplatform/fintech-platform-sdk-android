package com.fintechplatform.ui.transactions.db

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = PlatformDB::class)
data class Transaction(@PrimaryKey var id: String? = null,
                       @Column var who: String? = null,
                       @Column var what: String? = null,
                       @Column var twhen: String? = null,
                       @Column var amount: String? = null,
                       @Column var order: Long? = null,
                       @Column var status: String? = null,
                       @Column var resultcode: String? = null,
                       @Column var message: String? = null,
                       @Column var error: String? = null,
                       @Column var accountId: String? = null) : BaseModel()