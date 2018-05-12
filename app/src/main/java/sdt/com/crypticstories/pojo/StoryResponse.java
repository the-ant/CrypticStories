package sdt.com.crypticstories.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoryResponse {
    @SerializedName("stories")
    @Expose
    private List<Story> stories = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
