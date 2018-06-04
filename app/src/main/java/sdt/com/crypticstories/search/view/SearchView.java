package sdt.com.crypticstories.search.view;

import java.util.List;

import sdt.com.crypticstories.base.BaseView;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.pojo.Story;

public interface SearchView extends BaseView{

    void showRecent(List<ResultSearch> recentSearches);

    void showLibrary(List<Story> stories);

    void onStoryClicked(Story story);

    void onRecentClicked(ResultSearch resultSearch);

    void onRecommendSearchClick(ResultSearch resultSearch);

    void showRecommendSearch(List<ResultSearch> recommendSearch);

    void showStory(Story story);
}
