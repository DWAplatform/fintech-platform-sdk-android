package com.fintechplatform.ui.db;

import com.fintechplatform.ui.card.db.PaymentCard;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.config.fintechplatformGeneratedDatabaseHolder;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

@Database(name = PlatformDB.NAME, version = PlatformDB.VERSION)
public class PlatformDB {

    public static final String NAME = "PlatformDatabase"; // we will add the .db extension

    public static final int VERSION = 2;

    public static final String Module = "fintechplatform";

    public static void init() {
        FlowManager.initModule(fintechplatformGeneratedDatabaseHolder.class);
    }

    public static void deleteEverything() {
        FlowManager.getDatabase(PlatformDB.class).reset();
    }

    @Migration(version = 2, database = PlatformDB.class)
    public static class Migration2 extends AlterTableMigration<PaymentCard> {

        public Migration2(Class<PaymentCard> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "isDefault");
        }
    }
}
