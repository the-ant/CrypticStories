package sdt.com.crypticstories.search.presenter;

import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.search.view.SearchView;

public interface SearchPresenter {
    void setView(SearchView searchView);

    void loadRecentSearch();

    void loadLibrary();

    void handleRecommendSearch(String s);

    void searchStory(Integer id);

    void createRecentSearch(ResultSearch story);
}
