package com.dungnd.basemvp.data.local.realm;

import com.dungnd.basemvp.data.model.db.User;

import javax.inject.Inject;

import io.realm.RealmResults;

public class RealmHelper {

    private final RealmManager realmManager;

    @Inject
    public RealmHelper(RealmManager realmManager) {
        this.realmManager = realmManager;
    }

    public void saveUser(User user) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(user));
    }

    public User getUser() {
        realmManager.getRealm().refresh();
        return realmManager.getRealm().where(User.class).findFirst();
    }

    public void deleteUser() {
        realmManager.getRealm().executeTransaction(realm -> {
            RealmResults<User> resultUser = realm.where(User.class).findAll();
            resultUser.deleteAllFromRealm();
        });
    }
}
