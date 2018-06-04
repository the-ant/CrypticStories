package sdt.com.crypticstories.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.pojo.StoryResponse;

public interface StoryAPI {
    @GET("stories")
    Call<StoryResponse> getStories(@Query("page_number") int page);

    @PUT("story/views")
    Call<Integer> updateViewsStory(@Query("story_id") int id);

    @GET("stories/top-view")
    Call<List<Story>> getRecommendedStories();
    
    @GET("story/{story_id}")
    Call<Story> getStory(@Path("story_id") int id);

    @GET("stories/search")
    Call<List<ResultSearch>> getResultSearch(@Query("string") String name);

}
