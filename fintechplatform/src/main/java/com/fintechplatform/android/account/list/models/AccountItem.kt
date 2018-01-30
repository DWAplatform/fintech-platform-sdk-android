package com.fintechplatform.android.account.list.models

import android.os.Parcel
import android.os.Parcelable

data class AccountItem(val accounId: String,
                       val level: String,
                       val aspName: String,
                       val accountType: String) : Parcelable {

    constructor(source: Parcel): this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(accounId)
        dest?.writeString(level)
        dest?.writeString(aspName)
        dest?.writeString(accountType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<AccountItem> = object : Parcelable.Creator<AccountItem> {
            override fun createFromParcel(source: Parcel): AccountItem{
                return AccountItem(source)
            }

            override fun newArray(size: Int): Array<AccountItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}