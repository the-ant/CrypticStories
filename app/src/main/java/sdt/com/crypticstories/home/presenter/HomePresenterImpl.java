package sdt.com.crypticstories.home.presenter;

import sdt.com.crypticstories.home.model.StoriesInteraction;
import sdt.com.crypticstories.home.view.HomeView;
import sdt.com.crypticstories.pojo.StoryResponse;

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "home_presenter";
    private HomeView homeView;
    private StoriesInteraction storiesInteraction;

    public HomePresenterImpl(StoriesInteraction storiesInteraction) {
        this.storiesInteraction = storiesInteraction;
    }

    @Override
    public void setView(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void displayStories(int currentPage) {
        if (isViewAttached()) {
            homeView.showLoadingProgress();
            storiesInteraction.fetchStories(currentPage, new StoriesInteraction.OnLoadedStoriesListener() {
                @Override
                public void onFinished(StoryResponse response) {
                    homeView.displayHome(response);
                }

                @Override
                public void onFailure(String msg) {
                    homeView.onLoadingFailed(msg);
                    homeView.hideLoadingProgress();
                }
            });
        }
    }

    @Override
    public void reloadData(int currentPage) {
        if (isViewAttached()) {
            homeView.showRefresh();
            storiesInteraction.fetchStories(currentPage, new StoriesInteraction.OnLoadedStoriesListener() {
                @Override
                public void onFinished(StoryResponse response) {
                    homeView.onReloadSuccessful(response);
                }

                @Override
                public void onFailure(String msg) {
                    homeView.onLoadingFailed(msg);
                    homeView.hideRefresh();
                }
            });
        }
    }

    @Override
    public void loadMoreData(int nextPage) {
        if (isViewAttached()) {
            storiesInteraction.fetchStories(nextPage, new StoriesInteraction.OnLoadedStoriesListener() {
                @Override
                public void onFinished(StoryResponse response) {
                    homeView.onLoadMoreSuccess(response);
                }

                @Override
                public void onFailure(String msg) {
                    homeView.onLoadingFailed(msg);
                }
            });
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
