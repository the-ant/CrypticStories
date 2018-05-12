package sdt.com.crypticstories.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Story {
    @SerializedName("_id")
    @Expose
    private Integer id;
    @SerializedName("name_story")
    @Expose
    private String nameStory;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("explaining")
    @Expose
    private String explaining;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("page_number")
    @Expose
    private Integer pageNumber;

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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExplaining() {
        return explaining;
    }

    public void setExplaining(String explaining) {
        this.explaining = explaining;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

}
