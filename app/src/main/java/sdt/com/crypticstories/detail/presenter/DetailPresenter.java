package sdt.com.crypticstories.detail.presenter;

import sdt.com.crypticstories.detail.view.DetailView;
import sdt.com.crypticstories.pojo.Story;

public interface DetailPresenter {
    void setView(DetailView view);

    void  showDetail(Story story);

    void showExplanation();

    void hideExplanation();

    void destroy();

    void setRecommendedList();

    void resetDetail(Story story);

    void setViewsStory(Story story);

    void addToLib(Story story);
}
