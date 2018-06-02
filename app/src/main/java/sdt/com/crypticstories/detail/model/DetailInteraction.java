package sdt.com.crypticstories.detail.model;

import java.util.List;

import sdt.com.crypticstories.pojo.Story;

public interface DetailInteraction {
    void fetchRecommendedList(OnLoadedStoriesListener listener);

    void updateViewsStory(Story story);

    interface OnLoadedStoriesListener {
        void onFinished(List<Story> list);

        void onFailure(String msg);
    }
}
