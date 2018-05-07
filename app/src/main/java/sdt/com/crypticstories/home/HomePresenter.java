package sdt.com.crypticstories.home;


public interface HomePresenter {
    void displayStories();
    void setView(HomeView homeView);
    void destroy();
    void reloadData();
    void loadMoreData();
}
