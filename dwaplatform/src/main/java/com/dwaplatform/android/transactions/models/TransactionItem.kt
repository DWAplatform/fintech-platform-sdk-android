package com.dwaplatform.android.transactions.models

import android.os.Parcel
import android.os.Parcelable

data class TransactionItem(val id: String,
                           val what: String,
                           val who: String,
                           val message: String?,
                           val amount: String,
                           val twhen: String,
                           val order: Long,
                           val status: String,
                           val resultcode: String,
                           val newitem: Boolean = false) : Parcelable {

    constructor(source: Parcel): this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readInt() != 0)

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(what)
        dest?.writeString(who)
        dest?.writeString(message)
        dest?.writeString(amount)
        dest?.writeString(twhen)
        dest?.writeLong(order)
        dest?.writeString(status)
        dest?.writeString(resultcode)
        dest?.writeInt(if (newitem) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<TransactionItem> = object : Parcelable.Creator<TransactionItem> {
            override fun createFromParcel(source: Parcel): TransactionItem{
                return TransactionItem(source)
            }

            override fun newArray(size: Int): Array<TransactionItem?> {
                return arrayOfNulls(size)
            }
        }
    }

}