package sdt.com.crypticstories.detail.view;

import java.util.List;

import sdt.com.crypticstories.pojo.Story;

public interface DetailView {
    void showDetail(Story story);

    void showExplanation();

    void hideExplanation();

    void onStoryClicked(Story story);

    void showRecommendedStories(List<Story> list);

    void showLoading();

    void hideLoading();

}
