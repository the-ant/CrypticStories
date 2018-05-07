package sdt.com.crypticstories.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

import sdt.com.crypticstories.R;
import sdt.com.crypticstories.databinding.FragmentStoriesBinding;
import sdt.com.crypticstories.pojos.Story;
import sdt.com.crypticstories.utils.PaginationScrollListener;

public class StoriesFragment extends Fragment implements HomeView {
    private static final String TAG = "home";

    private FragmentStoriesBinding binding;
    private HomePresenter homePresenter;
    private Callback callback;
    private List<Story> stories = new ArrayList<>();
    private StoriesAdapter storiesAdapter;
    private LinearLayoutManager layoutManager;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private static final int PAGE_START = 1;
    private int totalPage = 5;
    private int currentPage = PAGE_START;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stories, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        initLayoutReferences();
        initData();
    }

    private void initData() {
        StoriesInteractor storiesInteractor = new StoriesInteractorImpl();
        homePresenter = new HomePresenterImpl(storiesInteractor);
        homePresenter.setView(this);
    }

    private void initLayoutReferences() {
        binding.swipeRefresh.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        binding.swipeRefresh.setOnRefreshListener(() -> homePresenter.reloadData());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.storiesListing.getContext(),
//                layoutManager.getOrientation());
        binding.storiesListing.setLayoutManager(layoutManager);
//        binding.storiesListing.addItemDecoration(dividerItemDecoration);
        binding.storiesListing.setHasFixedSize(true);
        binding.storiesListing.setItemAnimator(new DefaultItemAnimator());

        storiesAdapter = new StoriesAdapter(stories, this);
        binding.storiesListing.setAdapter(storiesAdapter);

        binding.storiesListing.addOnScrollListener(new PaginationScrollListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                new Handler().postDelayed(() -> homePresenter.loadMoreData(), 1000);
            }

            @Override
            protected int getTotalPageCount() {
                return totalPage;
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
    public void displayHome(List<Story> stories) {
        new Handler().postDelayed(() -> {
            storiesAdapter.clear();
            storiesAdapter.addAll(stories);

            if (currentPage <= totalPage)
                storiesAdapter.addLoadingFooter();
            else
                isLastPage = true;
            hideLoadingProgress();
        }, 1000);
    }

    @Override
    public void loadingFailed(String errorMessage) {
        Log.d(TAG, "loadingFailed: " + errorMessage);
    }

    @Override
    public void showLoadingProgress() {
        binding.primaryLayout.setVisibility(View.INVISIBLE);
        binding.loadDataProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        binding.loadDataProgress.setVisibility(View.GONE);
        binding.primaryLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRefresh() {
        binding.swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onStoryClicked(Story story) {
        callback.onStoryClicked(story);
    }

    @Override
    public void onLoadMoreSuccess(List<Story> stories) {
        isLoading = false;
        storiesAdapter.removeLoadingFooter();
        storiesAdapter.addAll(stories);

        if (currentPage != totalPage)
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
        void onStoryClicked(Story story);
    }
}
