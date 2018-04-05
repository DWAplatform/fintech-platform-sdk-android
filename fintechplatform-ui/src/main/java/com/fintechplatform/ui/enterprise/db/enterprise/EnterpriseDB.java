package com.fintechplatform.ui.enterprise.db.enterprise;

import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by ingrid on 26/02/18.
 */

public class EnterpriseDB {
    public Enterprise findEnterprise(String ownerId){
        return SQLite.select()
                .from(Enterprise.class)
                .where(Enterprise_Table.id.is(ownerId))
                .querySingle();
    }

    public void deleteEnterprises() {
        SQLite.delete().from(Enterprise.class).execute();
    }

    public void saveEnterprise(Enterprise enterprise) {
        enterprise.save();
    }
}
