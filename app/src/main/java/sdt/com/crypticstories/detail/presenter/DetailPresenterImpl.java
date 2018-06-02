package sdt.com.crypticstories.detail.presenter;

import android.util.Log;

import java.util.List;

import sdt.com.crypticstories.detail.model.DetailInteraction;
import sdt.com.crypticstories.detail.view.DetailView;
import sdt.com.crypticstories.pojo.Story;

public class DetailPresenterImpl implements DetailPresenter {
    private static final String TAG = "detail_presenter";
    private DetailView detailView;
    private DetailInteraction detailInteraction;

    public DetailPresenterImpl(DetailInteraction detailInteraction) {
        this.detailInteraction = detailInteraction;
    }

    @Override
    public void setView(DetailView view) {
        this.detailView = view;
    }

    @Override
    public void destroy() {
        this.detailView = null;
    }

    @Override
    public void setRecommendedList() {
        detailInteraction.fetchRecommendedList(new DetailInteraction.OnLoadedStoriesListener() {
            @Override
            public void onFinished(List<Story> list) {
                if (list.size() > 0) {
                    detailView.showRecommendedStories(list);
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.d(TAG, "onFailure: " + msg);
            }
        });
    }

    @Override
    public void resetDetail(Story story) {
        detailView.showLoading();
        detailView.showDetail(story);
    }

    @Override
    public void setViewsStory(Story story) {
        detailInteraction.updateViewsStory(story);
    }

    @Override
    public void addToLib(Story story) {
//        detailView.showAlert(story.getNameStory());
    }

    @Override
    public void showDetail(Story story) {
        if (isViewAttached()) {
            detailView.showLoading();
            detailView.showDetail(story);
        }
    }

    private boolean isViewAttached() {
        return detailView != null;
    }

    @Override
    public void showExplanation() {
        if (isViewAttached()) {
            detailView.showExplanation();
        }
    }

    @Override
    public void hideExplanation() {
        if (isViewAttached()) {
            detailView.hideExplanation();
        }
    }
}
