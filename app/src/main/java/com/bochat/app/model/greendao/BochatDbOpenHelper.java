package com.bochat.app.model.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.bochat.app.model.util.LogUtil;

import org.greenrobot.greendao.database.Database;


public class BochatDbOpenHelper extends DaoMaster.DevOpenHelper{


    public BochatDbOpenHelper(Context context, String name) {
        super(context, name);
    }

    public BochatDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        LogUtil.LogDebug("database", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables.");
        DaoMaster.dropAllTables(db, true);
        onCreate(db);
    }

}
