package com.fintechplatform.android.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.config.fintechplatformGeneratedDatabaseHolder;

@Database(name = PlatformDB.NAME, version = PlatformDB.VERSION)
public class PlatformDB {

    public static final String NAME = "PlatformDatabase"; // we will add the .db extension

    public static final int VERSION = 1;

    public static final String Module = "fintechplatform";

    public static void init() {
        FlowManager.initModule(fintechplatformGeneratedDatabaseHolder.class);
    }
}
