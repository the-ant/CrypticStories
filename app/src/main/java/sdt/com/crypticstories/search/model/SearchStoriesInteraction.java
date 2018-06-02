package sdt.com.crypticstories.search.model;

import java.util.List;

import sdt.com.crypticstories.pojo.Story;

/**
 * Created by phamminhson on 6/2/18.
 */

public interface SearchStoriesInteraction {
    void searchStories(String searchString, SearchStoriesInteraction.OnLoadedStoriesListener onLoadedStoriesListener);

    interface OnLoadedStoriesListener {
        void onFinished(List<Story> response);
        void onFailure(String msg);
    }
}
