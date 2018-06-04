package sdt.com.crypticstories.detail.model;

import java.util.List;

import sdt.com.crypticstories.library.model.LibInteraction;
import sdt.com.crypticstories.pojo.Story;

public interface DetailInteraction {
    void fetchRecommendedList(OnLoadedStoriesListener listener);

    void updateViewsStory(Story story);

    boolean isAddedLib(Integer id);

    void addToLib(Story story, OnAddedListener addedListener);

    void fetchStory(Integer id, OnFetchedStoryListener listener);

    interface OnFetchedStoryListener {
        void onFinished(Story story);
        void onFailure(String msg);
    }

    interface OnAddedListener {
        void onFinished(boolean successfull);
    }

    interface OnLoadedStoriesListener {
        void onFinished(List<Story> list);
        void onFailure(String msg);
    }
}
