package sdt.com.crypticstories.home;


import java.util.List;

import sdt.com.crypticstories.pojos.Story;

public interface StoriesInteractor {
    List<Story> fetchStories();
}
