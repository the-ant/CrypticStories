package sdt.com.crypticstories.detail.model;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdt.com.crypticstories.api.StoryAPI;
import sdt.com.crypticstories.pojo.Story;

public class DetailInteractionImpl implements DetailInteraction {
    private static final String TAG = "detail_interaction";
    private StoryAPI storyAPI;

    public DetailInteractionImpl(StoryAPI storyAPI){
        this.storyAPI = storyAPI;
    }


    @Override
    public void fetchRecommendedList(final OnLoadedStoriesListener listener) {
        Call<List<Story>> call = storyAPI.getRecommendedStories();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful() && response.body() != null){
                    listener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void updateViewsStory(Story story) {
        Call<Integer> call = storyAPI.updateViewsStory(story.getId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: updated");
                } else {
                    Log.i(TAG, "onResponse: update failed");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i(TAG, "onResponse: update failed");
            }
        });
    }
}
