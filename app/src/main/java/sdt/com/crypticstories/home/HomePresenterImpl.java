package sdt.com.crypticstories.home;

import java.util.List;

import sdt.com.crypticstories.pojos.Story;

public class HomePresenterImpl implements HomePresenter {
    private HomeView homeView;
    private StoriesInteractor storiesInteractor;

    public HomePresenterImpl(StoriesInteractor storiesInteractor) {
        this.storiesInteractor = storiesInteractor;
    }

    @Override
    public void setView(HomeView homeView) {
        this.homeView = homeView;
        displayStories();
    }

    @Override
    public void displayStories() {
        if (isViewAttached()) {
            homeView.showLoadingProgress();
            List<Story> stories = storiesInteractor.fetchStories();
            if (stories != null && stories.size() > 0) {
                homeView.displayHome(stories);
            } else {
                homeView.loadingFailed("load error!");
            }
//            homeView.hideLoadingProgress();
        }
    }

    @Override
    public void reloadData() {
        if (isViewAttached()) {
            homeView.showRefresh();
//            List<Story> stories = storiesInteractor.fetchStories();
//            if (stories != null && stories.size() > 0) {
//                homeView.displayHome(stories);
//            } else {
//                homeView.loadingFailed("load error!");
//            }
//            homeView.hideRefresh();
        }
    }

    @Override
    public void loadMoreData() {
        if (isViewAttached()) {
            List<Story> stories = storiesInteractor.fetchStories();
            homeView.onLoadMoreSuccess(stories);
        }
    }

    @Override
    public void destroy() {
        homeView = null;
    }

    private boolean isViewAttached() {
        return homeView != null;
    }
}
