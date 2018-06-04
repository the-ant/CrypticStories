package sdt.com.crypticstories.search.presenter;

import android.util.Log;

import java.util.List;

import sdt.com.crypticstories.api.StoryService;
import sdt.com.crypticstories.detail.model.DetailInteraction;
import sdt.com.crypticstories.detail.model.DetailInteractionImpl;
import sdt.com.crypticstories.pojo.RecentSearch;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.search.model.SearchInteraction;
import sdt.com.crypticstories.search.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter {
    private static final String TAG = "search_pre";
    private SearchInteraction searchInteraction;
    private SearchView searchView;

    public SearchPresenterImpl(SearchInteraction searchInteraction) {
        this.searchInteraction = searchInteraction;
    }

    @Override
    public void setView(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public void loadRecentSearch() {
        List<ResultSearch> recentSearch = searchInteraction.getRecentSearch();
        if (recentSearch != null) {
            searchView.showRecent(recentSearch);
        }
    }

    @Override
    public void loadLibrary() {
        List<Story> stories = searchInteraction.getStoriesInLibrary();
        if (stories != null) {
            searchView.showLibrary(stories);
        }
    }

    @Override
    public void handleRecommendSearch(String name) {
        searchInteraction.search(name, new SearchInteraction.OnSearchListener() {
            @Override
            public void onFinished(List<ResultSearch> recommendSearch) {
                if (recommendSearch != null) {
                    Log.d(TAG, "onFinished: " + recommendSearch.size());
                    searchView.showRecommendSearch(recommendSearch);
                }
            }

            @Override
            public void onFailed(String msg) {
                Log.d(TAG, "onFailed: "  + msg);
            }
        });
    }

    @Override
    public void searchStory(Integer id) {
        DetailInteractionImpl detailInteraction = new DetailInteractionImpl(StoryService.getAPI());
        detailInteraction.fetchStory(id, new DetailInteraction.OnFetchedStoryListener() {
            @Override
            public void onFinished(Story story) {
                searchView.showStory(story);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void createRecentSearch(ResultSearch story) {
        searchInteraction.createRecentSearch(story);
    }
}
