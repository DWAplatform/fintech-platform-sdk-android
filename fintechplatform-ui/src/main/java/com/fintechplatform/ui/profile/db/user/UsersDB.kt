package com.fintechplatform.ui.profile.db.user

import com.raizlabs.android.dbflow.sql.language.SQLite

class UsersDB {

    fun findUser(userId: String): Users? {
        return SQLite.select()
                .from(Users::class.java)
                .where(Users_Table.id.`is`(userId))
                .querySingle()

        // return (select from Users::class where (Users_Table.id `is` ownerId)).querySingle()
    }

    fun deleteUser() {
        SQLite.delete().from(Users::class.java).execute()
    }

    fun saveUser(user: Users) {
        user.save()
    }
}
