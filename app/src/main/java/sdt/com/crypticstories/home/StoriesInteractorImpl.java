package sdt.com.crypticstories.home;

import java.util.ArrayList;
import java.util.List;

import sdt.com.crypticstories.pojos.Story;

public class StoriesInteractorImpl implements StoriesInteractor {

    public StoriesInteractorImpl() {
    }

    @Override
    public List<Story> fetchStories() {
        return createStories();
    }

    public static List<Story> createStories() {
        List<Story> stories = new ArrayList<>();
        Story story;
        for (int i = 0; i < 50; i++) {
            story = new Story();
            story.setId(i);
            story.setName("Name" + (++i));
            story.setViews(i + 1);
            story.setReleaseDate("15-04-2018");
            stories.add(story);
        }
        return stories;
    }
}
