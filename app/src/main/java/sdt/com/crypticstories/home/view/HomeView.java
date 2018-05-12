package sdt.com.crypticstories.home.view;


import java.util.List;

import sdt.com.crypticstories.model.Story;
import sdt.com.crypticstories.model.StoryResponse;

public interface HomeView {
    void displayHome(StoryResponse response);

    void onLoadingFailed(String errorMessage);

    void onReloadSuccessful(StoryResponse response);

    void showLoadingProgress();

    void hideLoadingProgress();

    void showRefresh();

    void hideRefresh();

    void onStoryClicked(Story story);

    void onLoadMoreSuccess(StoryResponse response);

//    void showSearchActivity();
}
