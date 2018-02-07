package com.fintechplatform.android.transfer.contactslist.db;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class NetworkUsersDB {

    public void deleteP2P() {
        SQLite.delete().from(NetworkUsers.class).execute();
    }

    public void saveP2P(NetworkUsers p2p) {
        p2p.save();
    }

    public List<NetworkUsers> getP2PPeers(){
        return SQLite.select().from(NetworkUsers.class).queryList();
    }

    public NetworkUsers findP2P(String userid) {
        return SQLite.select().from(NetworkUsers.class).where(NetworkUsers_Table.userid.eq(userid)).querySingle();
    }
}
