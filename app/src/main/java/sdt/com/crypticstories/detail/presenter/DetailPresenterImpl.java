package sdt.com.crypticstories.detail.presenter;

import sdt.com.crypticstories.detail.view.DetailView;
import sdt.com.crypticstories.pojo.Story;

public class DetailPresenterImpl implements DetailPresenter {
    private DetailView detailView;

    @Override
    public void setView(DetailView view) {
        this.detailView = view;
    }

    @Override
    public void destroy() {
        this.detailView  = null;
    }
    @Override
    public void showDetail(Story story) {
        if (isViewAttached()){
            detailView.showDetail(story);
        }
    }

    private boolean isViewAttached() {
        return detailView != null;
    }

    @Override
    public void showExplanation() {
        if (isViewAttached()){
            detailView.showExplanation();
        }
    }

    @Override
    public void hideExplanation() {
        if (isViewAttached()){
            detailView.hideExplanation();
        }
    }
}
