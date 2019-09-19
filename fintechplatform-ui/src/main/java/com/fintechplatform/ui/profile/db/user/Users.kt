package com.fintechplatform.ui.profile.db.user

import com.fintechplatform.ui.db.PlatformDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = PlatformDB::class)
class Users : BaseModel() {
    @PrimaryKey
    @Column
    var id: String? = null
//    @Column todo tale care, this column was deleted => need a migration dbflow?
//    var access: Boolean? = null
    @Column
    var myname: String? = null
    @Column
    var surname: String? = null
    @Column
    var nationality: String? = null
    @Column
    var dateOfBirth: String? = null
    @Column
    var cf: String? = null
    @Column
    var address: String? = null
    @Column
    var addressNumber: String? = null
    @Column
    var ziPcode: String? = null
    @Column
    var city: String? = null
    @Column
    var province: String? = null
    @Column
    var region: String? = null
    @Column
    var telephone: String? = null
    @Column
    var telephoneCode: String? = null
    @Column
    var email: String? = null
    @Column
    var emailCode: String? = null
    @Column
    var photo: String? = null
    @Column
    var countryofresidence: String? = null
    @Column
    var jobinfo: String? = null
    @Column
    var income: String? = null
    @Column
    var accountLimit: String? = null

    val fullname: String
        get() = (if (myname == null) surname else "$myname $surname") as String
}