package sdt.com.crypticstories.detail.view;

import java.util.List;

import sdt.com.crypticstories.base.BaseView;
import sdt.com.crypticstories.pojo.Story;

public interface DetailView extends BaseView {
    void showDetail(Story story);

    void showExplanation();

    void hideExplanation();

    void onStoryClicked(Story story);

    void showRecommendedStories(List<Story> list);

    void showLoading();

    void hideLoading();

    void updateButtonAddLib(boolean isAdded);

    void notifyAddLib(boolean successful);
}
