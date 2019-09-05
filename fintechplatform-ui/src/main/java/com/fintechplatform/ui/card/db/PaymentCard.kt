package com.fintechplatform.ui.card.db

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = PlatformDB::class)
data class PaymentCard(
        @PrimaryKey var id: String? = "",
        @Column var numberAlias: String? = "",
        @Column var state: String? = "",
        @Column var expiration: String? = "",
        @Column var accountId: String? = "",
        @Column var isDefault: Boolean = false
) : BaseModel()