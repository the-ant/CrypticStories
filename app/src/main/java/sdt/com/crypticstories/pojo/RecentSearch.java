package sdt.com.crypticstories.pojo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RecentSearch extends RealmObject{
    @PrimaryKey
    private String id;
    private String name;

    public String getName() {
        return name;
    }
}
