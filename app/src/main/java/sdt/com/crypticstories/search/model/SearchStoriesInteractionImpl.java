package sdt.com.crypticstories.search.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdt.com.crypticstories.api.StoryAPI;
import sdt.com.crypticstories.pojo.Story;

/**
 * Created by phamminhson on 6/2/18.
 */

public class SearchStoriesInteractionImpl implements SearchStoriesInteraction {
    private static final String TAG = "search_interaction";
    private StoryAPI storyAPI;

    public SearchStoriesInteractionImpl(StoryAPI storyAPI) {
        this.storyAPI = storyAPI;
    }
    @Override
    public void searchStories(String searchString, OnLoadedStoriesListener onLoadedStoriesListener) {
        Call<List<Story>> call = storyAPI.searchStories(searchString);
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: successful");
                    onLoadedStoriesListener.onFinished(response.body());
                } else {
                    onLoadedStoriesListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                Log.i(TAG, "onResponse: onFailure - " + t.getMessage());
                onLoadedStoriesListener.onFailure(t.getMessage());
            }
        });
    }
}
