package com.fintechplatform.android.profile.db.user;

import com.raizlabs.android.dbflow.sql.language.SQLite;

public class UsersDB {

    public Users findUser(String userId){
        return SQLite.select()
                .from(Users.class)
                .where(Users_Table.id.is(userId))
                .querySingle();

        // return (select from Users::class where (Users_Table.id `is` userId)).querySingle()
    }

    public void deleteUser() {
        SQLite.delete().from(Users.class).execute();
    }

    public void saveUser(Users user) {
        user.save();
    }
}
