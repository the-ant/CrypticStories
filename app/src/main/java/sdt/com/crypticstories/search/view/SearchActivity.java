package sdt.com.crypticstories.search.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.api.StoryService;
import sdt.com.crypticstories.detail.adapter.RecommendAdapter;
import sdt.com.crypticstories.detail.view.DetailStoryActivity;
import sdt.com.crypticstories.home.model.StoriesInteraction;
import sdt.com.crypticstories.home.model.StoriesInteractionImpl;
import sdt.com.crypticstories.pojo.RecentSearch;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.search.adapter.RecentSearchAdapter;
import sdt.com.crypticstories.search.adapter.RecommendSearchAdapter;
import sdt.com.crypticstories.search.model.SearchInteraction;
import sdt.com.crypticstories.search.model.SearchInteractionImpl;
import sdt.com.crypticstories.search.presenter.SearchPresenter;
import sdt.com.crypticstories.search.presenter.SearchPresenterImpl;

public class SearchActivity extends AppCompatActivity implements SearchView {
    private static final String TAG = "search_act";
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.tvCancel)
    TextView btnCancel;
    @BindView(R.id.list_recent)
    RecyclerView listRecent;
    @BindView(R.id.library)
    RecyclerView listLibrary;
    @BindView(R.id.search_recommend)
    RecyclerView listRecommend;

    private List<Story> stories = new ArrayList<>();
    private List<ResultSearch> recentSearches = new ArrayList<>();
    private List<ResultSearch> recommendSearch = new ArrayList<>();
    private RecommendAdapter libAdapter;
    private RecentSearchAdapter recentSearchAdapter;
    private RecommendSearchAdapter recommendSearchAdapter;
    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        initUI();
        loadData();
    }

    private void loadData() {
        SearchInteraction searchInteraction = new SearchInteractionImpl(StoryService.getAPI());
        searchPresenter = new SearchPresenterImpl(searchInteraction);
        searchPresenter.setView(this);
        searchPresenter.loadRecentSearch();
        searchPresenter.loadLibrary();
    }

    private void initUI() {
        edtSearch.setText("");
        edtSearch.addTextChangedListener(searchTextChange);
        edtSearch.setOnKeyListener((v, keyCode, event) -> pressEnterKey(v, keyCode, event));
        btnCancel.setOnClickListener(v -> this.finish());
        initListRecent();
        initListLibrary();
        initRecommendSearch();
    }

    private void initRecommendSearch() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        listRecommend.setLayoutManager(layoutManager);
        listRecommend.setHasFixedSize(true);
        listRecommend.setItemAnimator(new DefaultItemAnimator());

        recommendSearchAdapter = new RecommendSearchAdapter(recommendSearch, this);
        listRecommend.setAdapter(recommendSearchAdapter);
    }

    private boolean pressEnterKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    search();
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    private void search() {
        Log.d(TAG, "search: ");
    }

    private void initListLibrary() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        listLibrary.setLayoutManager(layoutManager);
        listLibrary.setHasFixedSize(true);
        listLibrary.setItemAnimator(new DefaultItemAnimator());

        libAdapter = new RecommendAdapter(stories, this);
        listLibrary.setAdapter(libAdapter);
    }

    private void initListRecent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        listRecent.setLayoutManager(layoutManager);
        listRecent.setHasFixedSize(true);
        listRecent.setItemAnimator(new DefaultItemAnimator());

        recentSearchAdapter = new RecentSearchAdapter(recentSearches, this);
        listRecent.setAdapter(recentSearchAdapter);
    }

    private TextWatcher searchTextChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(TAG, "beforeTextChanged: " + s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged: " + s.toString());
            if (s.length() != 0)
                searchPresenter.handleRecommendSearch(s.toString());
            else
                recommendSearchAdapter.clear();
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(TAG, "afterTextChanged: " + s.toString());
        }
    };

    @Override
    public void showRecent(List<ResultSearch> recentSearches) {
        recentSearchAdapter.clear();
        recentSearchAdapter.addAll(recentSearches);
    }

    @Override
    public void showRecommendSearch(List<ResultSearch> list) {
        recommendSearchAdapter = new RecommendSearchAdapter(list, this);
        listRecommend.setAdapter(recommendSearchAdapter);
    }

    @Override
    public void showStory(Story story) {
        StoriesInteraction storiesInteraction = new StoriesInteractionImpl(StoryService.getAPI());
        storiesInteraction.updateViewsStory(story, successful -> Log.d(TAG, "showStory: " + successful));
        Intent intent = new Intent(this, DetailStoryActivity.class);
        intent.putExtra(Constants.STORY, Parcels.wrap(story));
        startActivity(intent);
    }

    @Override
    public void showLibrary(List<Story> stories) {
        libAdapter.clear();
        libAdapter.addAll(stories);
    }

    @Override
    public void onStoryClicked(Story story) {
        showStory(story);
    }

    @Override
    public void onRecentClicked(ResultSearch resultSearch) {
        searchPresenter.searchStory(resultSearch.getId());
    }

    @Override
    public void onRecommendSearchClick(ResultSearch resultSearch) {
        searchPresenter.createRecentSearch(resultSearch);
        searchPresenter.searchStory(resultSearch.getId());
    }
}
