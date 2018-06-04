package sdt.com.crypticstories.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ResultSearch extends RealmObject {
    @SerializedName("_id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("name_story")
    @Expose
    private String nameStory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameStory() {
        return nameStory;
    }

    public void setNameStory(String nameStory) {
        this.nameStory = nameStory;
    }
}
