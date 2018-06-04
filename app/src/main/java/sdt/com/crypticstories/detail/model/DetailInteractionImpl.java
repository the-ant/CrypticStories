package sdt.com.crypticstories.detail.model;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdt.com.crypticstories.api.StoryAPI;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.utils.RealmUtil;

public class DetailInteractionImpl implements DetailInteraction {
    private static final String TAG = "detail_interaction";
    private StoryAPI storyAPI;
    private Realm realm = RealmUtil.getLibInstance();

    public DetailInteractionImpl(StoryAPI storyAPI) {
        this.storyAPI = storyAPI;
    }

    @Override
    public void fetchRecommendedList(final OnLoadedStoriesListener listener) {
        Call<List<Story>> call = storyAPI.getRecommendedStories();
        call.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body());
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

    @Override
    public boolean isAddedLib(Integer id) {
        return RealmUtil.isExistStory(realm, id);
    }

    @Override
    public void addToLib(Story story, OnAddedListener addedListener) {
        boolean isExist = RealmUtil.isExistStory(realm, story.getId());
        Log.d(TAG, "addToLib: " + isExist);
        if (isExist) {
            addedListener.onFinished(false);
        } else {
            RealmUtil.createRealmObject(realm, story);
            addedListener.onFinished(true);
        }
    }

    @Override
    public void fetchStory(Integer id, OnFetchedStoryListener listener) {
        Call<Story> call = storyAPI.getStory(id);
        call.enqueue(new Callback<Story>() {
            @Override
            public void onResponse(Call<Story> call, Response<Story> response) {
                if (response.isSuccessful()) {
                    listener.onFinished(response.body());
                } else
                    listener.onFailure(response.message());
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<Story> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

}
