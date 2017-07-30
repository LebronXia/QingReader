package com.riane.qingreader.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.riane.qingreader.data.local.gen.DaoMaster;

import javax.inject.Inject;

/**
 * Created by Riane on 2017/7/30.
 */

public class DbOpenHelper extends DaoMaster.OpenHelper{

    @Inject
    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
