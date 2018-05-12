package sdt.com.crypticstories.home.view;


import android.widget.ImageView;
import android.widget.TextView;

import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.pojo.StoryResponse;

public interface HomeView {
    void displayHome(StoryResponse response);

    void onLoadingFailed(String errorMessage);

    void onReloadSuccessful(StoryResponse response);

    void showLoadingProgress();

    void hideLoadingProgress();

    void showRefresh();

    void hideRefresh();

    void onStoryClicked(Story story, ImageView poster);

    void onLoadMoreSuccess(StoryResponse response);

//    void showSearchActivity();
}
