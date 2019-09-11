package com.fintechplatform.ui.profile.db.documents

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = PlatformDB::class)
class Documents(@PrimaryKey var id: String? = null,
                @Column var userId: String? = null,
                @Column var docType: String? = null, 
                @Column var frontPage: String? = null,
                @Column var backPage: String? = null,
                @Column var frontPageId: String? = null,
                @Column var backPageId: String? = null) : BaseModel()
