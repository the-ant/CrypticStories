package sdt.com.crypticstories.pojos;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Parcel
public class Story extends RealmObject{

    @PrimaryKey
    private int id;
    private String name;
    @SerializedName("poster_url")
    private String posterUrl;
    @SerializedName("content_url")
    private String contentUrl;
    @SerializedName("explaining_url")
    private String explainingUrl;
    @SerializedName("views")
    private int views;
    @SerializedName("release_date")
    private String releaseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getExplainingUrl() {
        return explainingUrl;
    }

    public void setExplainingUrl(String explainingUrl) {
        this.explainingUrl = explainingUrl;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
