package sdt.com.crypticstories.search.presenter;

import android.util.Log;

import java.util.List;

import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.search.model.SearchStoriesInteraction;

/**
 * Created by phamminhson on 6/2/18.
 */

public class SearchStoriesPresenterImpl implements SearchStoriesPresenter {
    private static final String TAG = "search_presenter";
    private SearchStoriesInteraction searchStoriesInteraction;

    public SearchStoriesPresenterImpl(SearchStoriesInteraction searchStoriesInteraction) {
        this.searchStoriesInteraction = searchStoriesInteraction;
    }

    @Override
    public void displayStories(String searchString) {
        if (searchString != null && searchString != "") {
            searchStoriesInteraction.searchStories(searchString, new SearchStoriesInteraction.OnLoadedStoriesListener() {
                @Override
                public void onFinished(List<Story> response) {
                    Log.i(TAG, "onResponse: successful - " + response);
                }
                @Override
                public void onFailure(String msg) {
                    Log.i(TAG, "onResponse: onFailure - " + msg);
                }
            });
        }
    }
}
