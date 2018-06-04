package sdt.com.crypticstories.search.model;

import java.util.List;

import sdt.com.crypticstories.pojo.RecentSearch;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.pojo.Story;

public interface SearchInteraction {
    void searchStories(String searchString, OnLoadedStoriesListener onLoadedStoriesListener);

    void getStory(Integer id);

    void createRecentSearch(ResultSearch story);

    interface OnLoadedStoriesListener {
        void onFinished(List<Story> response);
        void onFailure(String msg);
    }
    List<Story> getStoriesInLibrary();

    List<ResultSearch> getRecentSearch();

    void search(String name, OnSearchListener onSearchListener);

    interface OnSearchListener {
        void onFinished(List<ResultSearch> list);
        void onFailed(String msg);
    }
}
