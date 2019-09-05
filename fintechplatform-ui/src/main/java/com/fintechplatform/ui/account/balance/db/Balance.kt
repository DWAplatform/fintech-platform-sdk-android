package com.fintechplatform.ui.account.balance.db

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = PlatformDB::class)
data class Balance(@PrimaryKey var id: String = "",
                   @Column var amount: Long? = null,
                   @Column var currency: String = "",
                   @Column var accountId: String = "",
                   @Column var availableBalance: Long? = null): BaseModel()