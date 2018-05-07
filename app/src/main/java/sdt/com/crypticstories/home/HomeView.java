package sdt.com.crypticstories.home;


import java.util.List;

import sdt.com.crypticstories.pojos.Story;

public interface HomeView {
    void displayHome(List<Story> stories);

    void loadingFailed(String errorMessage);

    void showLoadingProgress();

    void hideLoadingProgress();

    void showRefresh();

    void hideRefresh();

    void onStoryClicked(Story story);

    void onLoadMoreSuccess(List<Story> stories);

//    void showSearchActivity();
}
