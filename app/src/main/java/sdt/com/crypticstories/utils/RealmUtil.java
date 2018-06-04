package sdt.com.crypticstories.utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import sdt.com.crypticstories.pojo.RecentSearch;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.pojo.Story;

public class RealmUtil {

    public static Realm getLibInstance() {
        RealmConfiguration libConfig = new RealmConfiguration.Builder()
                .name("library2")
                .build();
        Realm realm = Realm.getInstance(libConfig);
        return realm;
    }

    public static Realm getRecentSearchInstance() {
        RealmConfiguration recentConfig = new RealmConfiguration.Builder()
                .name("result")
                .build();
        Realm realm = Realm.getInstance(recentConfig);
        return realm;
    }

    public static void createRecentStory(Realm realm, ResultSearch recentSearch){
        realm.beginTransaction();
        realm.insert(recentSearch);
        realm.commitTransaction();
    }

    public static List<ResultSearch> getRecentSearch(Realm realm) {
        final RealmResults<ResultSearch> recentSearches = realm.where(ResultSearch.class)
                .findAll();
        return realm.copyFromRealm(recentSearches);
    }

    public static List<Story> getStories(Realm realm) {
        final RealmResults<Story> stories = realm.where(Story.class)
                .findAll();
        return realm.copyFromRealm(stories);
    }

    public static boolean isExistStory(Realm realm, Integer id) {
        final Story result = realm.where(Story.class)
                .equalTo("id", id)
                .findFirst();
        return result != null;
    }

    public static void createRealmObject(Realm realm, Story story) {
        realm.beginTransaction();
        realm.insert(story);
        realm.commitTransaction();
    }
}
