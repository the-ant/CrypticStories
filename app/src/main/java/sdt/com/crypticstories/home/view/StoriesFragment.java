package sdt.com.crypticstories.home.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.api.StoryService;
import sdt.com.crypticstories.home.adapter.StoriesAdapter;
import sdt.com.crypticstories.home.model.StoriesInteraction;
import sdt.com.crypticstories.home.model.StoriesInteractionImpl;
import sdt.com.crypticstories.home.presenter.HomePresenter;
import sdt.com.crypticstories.home.presenter.HomePresenterImpl;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.pojo.StoryResponse;
import sdt.com.crypticstories.utils.PaginationScrollListener;

public class StoriesFragment extends Fragment implements HomeView {
    private static final String TAG = "home";

    @BindView(R.id.primary_layout)
    RelativeLayout primaryLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.storiesListing)
    RecyclerView storiesListing;
    @BindView(R.id.load_data_progress)
    ProgressBar loadDataProgress;

    private HomePresenter homePresenter;
    private Callback callback;
    private List<Story> stories = new ArrayList<>();
    private StoriesAdapter storiesAdapter;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private StoryResponse storyResponse;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        initLayoutReferences();
        initData();
    }

    private void initData() {
        StoriesInteraction storiesInteraction = new StoriesInteractionImpl(StoryService.getAPI());
        homePresenter = new HomePresenterImpl(storiesInteraction);
        homePresenter.setView(this);
        homePresenter.displayStories(currentPage);
    }

    private void initLayoutReferences() {
        swipeRefresh.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefresh.setOnRefreshListener(() -> {
            isLastPage = false;
            currentPage = PAGE_START;
            homePresenter.reloadData(currentPage);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        storiesListing.setLayoutManager(layoutManager);
        storiesListing.setHasFixedSize(true);
        storiesListing.setItemAnimator(new DefaultItemAnimator());

        storiesAdapter = new StoriesAdapter(stories, this);
        storiesListing.setAdapter(storiesAdapter);

        storiesListing.addOnScrollListener(new PaginationScrollListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                new Handler().postDelayed(() -> homePresenter.loadMoreData(currentPage), 1000);
            }

            @Override
            protected int getTotalPageCount() {
                Log.d(TAG, "getTotalPageCount: " + storyResponse.getTotalPages());
                return storyResponse.getTotalPages();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void displayHome(StoryResponse response) {
        this.storyResponse = response;
        Log.i(TAG, "displayHome: " + response.getTotalPages());
        new Handler().postDelayed(() -> {
            setupData();
            hideLoadingProgress();
        }, 1000);
    }

    @Override
    public void onReloadSuccessful(StoryResponse response) {
        this.storyResponse = response;
        new Handler().postDelayed(() -> {
            setupData();
            hideRefresh();
        }, 1000);
    }

    @Override
    public void onLoadingFailed(String errorMessage) {
        Log.d(TAG, "onLoadingFailed: " + errorMessage);
    }

    @Override
    public void showLoadingProgress() {
        primaryLayout.setVisibility(View.INVISIBLE);
        loadDataProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        loadDataProgress.setVisibility(View.GONE);
        primaryLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRefresh() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onStoryClicked(Story story, ImageView poster) {
        callback.onStoryClicked(story, poster);
    }

    @Override
    public void onLoadMoreSuccess(StoryResponse response) {
        isLoading = false;
        storiesAdapter.removeLoadingFooter();
        storiesAdapter.addAll(response.getStories());

        if (currentPage != storyResponse.getTotalPages())
            storiesAdapter.addLoadingFooter();
        else
            isLastPage = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.destroy();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public interface Callback {
        void onStoryClicked(Story story, ImageView poster);
    }

    private void setupData() {
        storiesAdapter.clear();
        storiesAdapter.addAll(storyResponse.getStories());

        if (currentPage <= storyResponse.getTotalPages())
            storiesAdapter.addLoadingFooter();
        else
            isLastPage = true;
    }
}
