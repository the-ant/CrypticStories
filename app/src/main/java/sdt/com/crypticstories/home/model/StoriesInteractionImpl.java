package sdt.com.crypticstories.home.model;

import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdt.com.crypticstories.api.StoryAPI;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.pojo.StoryResponse;

public class StoriesInteractionImpl implements StoriesInteraction {
    private static final String TAG = "stories_interactor";
    private StoryAPI storyAPI;

    public StoriesInteractionImpl(StoryAPI storyAPI) {
        this.storyAPI = storyAPI;
    }

    @Override
    public void fetchStories(int currentPage, final OnLoadedStoriesListener onLoadedStoriesListener) {
        Call<StoryResponse> call = storyAPI.getStories(currentPage);
        call.enqueue(new Callback<StoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<StoryResponse> call, @NonNull Response<StoryResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: successful");
                    onLoadedStoriesListener.onFinished(response.body());
                } else {
                    onLoadedStoriesListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StoryResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "onResponse: onFailure - " + t.getMessage());
                onLoadedStoriesListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void updateViewsStory(Story story, OnUpdatedStoriesListener listener) {
        Call<Integer> call = storyAPI.updateViewsStory(story.getId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: updated");
                } else {
                    Log.i(TAG, "onResponse: update failed");
                }
                listener.onUpdated(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i(TAG, "onResponse: update failed");
                listener.onUpdated(false);
            }
        });
    }
}
