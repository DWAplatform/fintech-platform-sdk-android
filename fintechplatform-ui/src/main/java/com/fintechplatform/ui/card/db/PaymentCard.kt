package com.fintechplatform.ui.card.db

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = PlatformDB::class)
data class PaymentCard(
        @PrimaryKey var id: String? = null,
        @Column var numberAlias: String? = null,
        @Column var state: String? = null,
        @Column var expiration: String? = null,
        @Column var accountId: String? = null,
        @Column var isDefault: Boolean = false
) : BaseModel()