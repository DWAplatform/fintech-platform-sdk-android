package com.dwaplatform.android.sample.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by ingrid on 18/12/17.
 */

@Database(name = SampleDB.NAME, version = SampleDB.VERSION)
public class SampleDB {

    public static final String NAME = "sampledb";

    public static final int VERSION = 1;
}
