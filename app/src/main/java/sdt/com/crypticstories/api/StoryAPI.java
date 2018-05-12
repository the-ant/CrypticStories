package sdt.com.crypticstories.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import sdt.com.crypticstories.pojo.StoryResponse;

public interface StoryAPI {
    @GET("stories")
    Call<StoryResponse> getStories(@Query("page_number") int page);

}
