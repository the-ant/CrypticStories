package sdt.com.crypticstories.home.model;


import sdt.com.crypticstories.model.StoryResponse;

public interface StoriesInteractor {
    void fetchStories(int currentPage, OnLoadedStoriesListener onLoadedStoriesListener);

    interface OnLoadedStoriesListener {
        void onFinished(StoryResponse response);
        void onFailure(String msg);
    }
}
