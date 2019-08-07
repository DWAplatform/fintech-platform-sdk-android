package com.fintechplatform.ui.models

import android.os.Parcel
import android.os.Parcelable

data class DataAccount(val ownerId: String,
                       val accountId: String,
                       val accountType: String,
                       val tenantId: String,
                       val accessToken: String,
                       val primaryAccountId: String?=null) : Parcelable {

    constructor(source: Parcel): this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(ownerId)
        dest?.writeString(accountId)
        dest?.writeString(accountType)
        dest?.writeString(tenantId)
        dest?.writeString(accessToken)
        dest?.writeString(primaryAccountId)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<DataAccount> = object : Parcelable.Creator<DataAccount> {
            override fun createFromParcel(source: Parcel): DataAccount{
                return DataAccount(source)
            }

            override fun newArray(size: Int): Array<DataAccount?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }
}