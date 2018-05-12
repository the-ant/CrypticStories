package sdt.com.crypticstories.detail.model;

import sdt.com.crypticstories.api.StoryAPI;

public class DetailInterationImpl implements DetailInteration {
    private StoryAPI storyAPI;

    public DetailInterationImpl(StoryAPI storyAPI){
        this.storyAPI = storyAPI;
    }

    @Override
    public void setViews(int views) {

    }
}
