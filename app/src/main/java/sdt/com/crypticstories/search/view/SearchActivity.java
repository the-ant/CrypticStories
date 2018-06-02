package sdt.com.crypticstories.search.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import sdt.com.crypticstories.R;
import sdt.com.crypticstories.api.StoryService;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.search.model.SearchStoriesInteraction;
import sdt.com.crypticstories.search.model.SearchStoriesInteractionImpl;
import sdt.com.crypticstories.search.presenter.SearchStoriesPresenter;
import sdt.com.crypticstories.search.presenter.SearchStoriesPresenterImpl;

public class SearchActivity extends AppCompatActivity {

    private SearchStoriesPresenter searchStoriesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();

        // for test
        String searchString = "Q";
        searchStories(searchString);
    }

    private void init() {
        initData();
    }

    private void initData() {
        SearchStoriesInteraction searchStoriesInteraction = new SearchStoriesInteractionImpl(StoryService.getAPI());
        searchStoriesPresenter = new SearchStoriesPresenterImpl(searchStoriesInteraction);
    }
    private void searchStories(String searchString) {
        searchStoriesPresenter.displayStories(searchString);
    }
}
