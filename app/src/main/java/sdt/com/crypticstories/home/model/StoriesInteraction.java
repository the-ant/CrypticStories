package sdt.com.crypticstories.home.model;


import sdt.com.crypticstories.pojo.StoryResponse;

public interface StoriesInteraction {
    void fetchStories(int currentPage, OnLoadedStoriesListener onLoadedStoriesListener);

    interface OnLoadedStoriesListener {
        void onFinished(StoryResponse response);
        void onFailure(String msg);
    }
}
