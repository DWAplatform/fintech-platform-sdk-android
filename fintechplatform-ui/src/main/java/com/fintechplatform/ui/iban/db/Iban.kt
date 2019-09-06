package com.fintechplatform.ui.iban.db

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = PlatformDB::class)
data class Iban(@PrimaryKey var id: String? = "",
                @Column var number: String? = "",
                @Column var state: String? = "",
                @Column var accountId: String = "") : BaseModel()