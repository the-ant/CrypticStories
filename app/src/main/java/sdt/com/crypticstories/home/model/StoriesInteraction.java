package sdt.com.crypticstories.home.model;


import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.pojo.StoryResponse;

public interface StoriesInteraction {
    void fetchStories(int currentPage, OnLoadedStoriesListener onLoadedStoriesListener);

    void updateViewsStory(Story story, OnUpdatedStoriesListener listener);

    interface OnUpdatedStoriesListener {
        void onUpdated(boolean successful);
    }

    interface OnLoadedStoriesListener {
        void onFinished(StoryResponse response);
        void onFailure(String msg);
    }
}
