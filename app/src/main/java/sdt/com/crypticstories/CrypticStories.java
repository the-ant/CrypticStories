package sdt.com.crypticstories;


import android.app.Application;

import io.realm.Realm;

public class CrypticStories extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
