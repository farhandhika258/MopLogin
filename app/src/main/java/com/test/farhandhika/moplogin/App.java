package com.test.farhandhika.moplogin;

import android.app.Application;

/**
 * Created by farhandhika on 21/12/17.
 */

public class App extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mDaoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(this, "greendao_token2.db").getWritableDb()).newSession();

        //if(mDaoSession.getTokenDao().loadAll().size() == 0) {
        //    mDaoSession.getTokenDao().insert(new Token(2L, "acces_token", "expires_in", "token_type", "scope","refresh_token"));
        //}

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
