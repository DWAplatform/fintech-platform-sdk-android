package com.dwaplatform.android.profile.db.user

import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.sql.language.SQLite

open class UsersDB {

    fun findUser(userId: String): Users? {
        return SQLite.select()
                .from(Users::class.java)
                .where(Users_Table.id.`is`(userId))
                .querySingle()
    }

    fun deleteUser() {
        SQLite.delete().from(Users::class.java).execute()
    }

    fun saveUser(user: Users) {
        user.save()
    }
}