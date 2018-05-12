package sdt.com.crypticstories.home.presenter;


import sdt.com.crypticstories.home.view.HomeView;

public interface HomePresenter {
    void displayStories(int currentPage);

    void setView(HomeView homeView);

    void destroy();

    void reloadData(int currentPage);

    void loadMoreData(int nextPage);
}
