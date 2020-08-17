package com.dungnd.basemvp.data.local.realm;

import io.realm.Realm;

public class RealmManager {

    /**
     * Singleton of RealmManager object
     */
    private static RealmManager sInstance;
    private Realm realm;

    /**
     * Get singleton of ApiManager object
     */
    public static RealmManager getInstance() {
        if (sInstance == null) {
            sInstance = new RealmManager();
        }
        return sInstance;
    }

    public static void deleteAll() {
        getInstance().getRealm().executeTransaction(realm -> {

        });
    }

    /**
     * Get Realm instance
     *
     * @return
     */
    public Realm getRealm() {
        realm = Realm.getDefaultInstance();
        return realm;
    }

    /**
     * Close Realm
     */
    public void closeRealm() {
        if (realm != null) {
            realm.close();
        }
    }
}
