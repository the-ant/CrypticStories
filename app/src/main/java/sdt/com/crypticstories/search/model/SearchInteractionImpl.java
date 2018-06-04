package sdt.com.crypticstories.search.model;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdt.com.crypticstories.api.StoryAPI;
import sdt.com.crypticstories.library.model.LibInteraction;
import sdt.com.crypticstories.library.model.LibInteractionImpl;
import sdt.com.crypticstories.pojo.RecentSearch;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.utils.RealmUtil;

public class SearchInteractionImpl implements SearchInteraction {
    private static final String TAG = "search_int";
    private StoryAPI storyAPI;
    private LibInteraction libInteraction;
    private Realm recentRealm = RealmUtil.getRecentSearchInstance();

    public SearchInteractionImpl(StoryAPI storyAPI) {
        this.storyAPI = storyAPI;
        this.libInteraction = new LibInteractionImpl(storyAPI);
    }

    @Override
    public List<Story> getStoriesInLibrary() {
        return libInteraction.loadLibrary();
    }

    @Override
    public List<ResultSearch> getRecentSearch() {
        return RealmUtil.getRecentSearch(recentRealm);
    }

    @Override
    public void search(String name, OnSearchListener onSearchListener) {
        Call<List<ResultSearch>> call = storyAPI.getResultSearch(name);
        call.enqueue(new Callback<List<ResultSearch>>() {
            @Override
            public void onResponse(Call<List<ResultSearch>> call, Response<List<ResultSearch>> response) {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful()){
                    onSearchListener.onFinished(response.body());
                } else {
                    onSearchListener.onFailed("Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ResultSearch>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                onSearchListener.onFailed(t.getMessage());
            }
        });
    }

    @Override
    public void searchStories(String searchString, OnLoadedStoriesListener onLoadedStoriesListener) {
//        Call<List<Story>> call = storyAPI.getStory(searchString);
//        call.enqueue(new Callback<List<Story>>() {
//            @Override
//            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
//                if (response.isSuccessful()) {
//                    Log.i(TAG, "onResponse: successful");
//                    onLoadedStoriesListener.onFinished(response.body());
//                } else {
//                    onLoadedStoriesListener.onFailure(response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Story>> call, Throwable t) {
//                Log.i(TAG, "onResponse: onFailure - " + t.getMessage());
//                onLoadedStoriesListener.onFailure(t.getMessage());
//            }
//        });
    }

    @Override
    public void getStory(Integer id) {

    }

    @Override
    public void createRecentSearch(ResultSearch resultSearch) {
        RealmUtil.createRecentStory(recentRealm, resultSearch);
    }

}
