package sdt.com.crypticstories.home.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdt.com.crypticstories.api.StoryAPI;
import sdt.com.crypticstories.model.Story;
import sdt.com.crypticstories.model.StoryResponse;

public class StoriesInteractorImpl implements StoriesInteractor {
    private static final String TAG = "stories_interactor";
    private StoryAPI storyAPI;

    public StoriesInteractorImpl(StoryAPI storyAPI) {
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
}
