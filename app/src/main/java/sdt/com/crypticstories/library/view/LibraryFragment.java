package sdt.com.crypticstories.library.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.api.StoryService;
import sdt.com.crypticstories.detail.view.DetailStoryFragment;
import sdt.com.crypticstories.library.adapter.LibAdapter;
import sdt.com.crypticstories.library.model.LibInteraction;
import sdt.com.crypticstories.library.model.LibInteractionImpl;
import sdt.com.crypticstories.library.presenter.LibPresenter;
import sdt.com.crypticstories.library.presenter.LibPresenterImpl;
import sdt.com.crypticstories.pojo.Story;

public class LibraryFragment extends Fragment implements LibraryView {

    @BindView(R.id.primary_layout)
    RelativeLayout primaryLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.storiesListing)
    RecyclerView storiesListing;
    @BindView(R.id.load_data_progress)
    ProgressBar loadDataProgress;

    private List<Story> stories = new ArrayList<>();
    private LibAdapter adapter;
    private LibPresenter libPresenter;
    private Callback callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (LibraryFragment.Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);
        ButterKnife.bind(this, view);
        initLayoutReferences();
        loadData();
        return view;
    }

    private void loadData() {
        LibInteraction libInteraction = new LibInteractionImpl(StoryService.getAPI());
        libPresenter = new LibPresenterImpl(libInteraction);
        libPresenter.setView(this);
        libPresenter.loadLibrary();
    }

    private void initLayoutReferences() {
        swipeRefresh.setColorSchemeResources(android.R.color.black);
        swipeRefresh.setOnRefreshListener(() -> libPresenter.reloadData());

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false);
        storiesListing.setLayoutManager(layoutManager);
        storiesListing.setHasFixedSize(true);
        storiesListing.setItemAnimator(new DefaultItemAnimator());

        adapter = new LibAdapter(stories, this);
        storiesListing.setAdapter(adapter);
    }

    @Override
    public void showLibrary(List<Story> stories) {
        new Handler().postDelayed(() -> {
            setupData(stories);
            hideLoading();
        }, 1000);
    }

    @Override
    public void refreshData(List<Story> stories) {
        new Handler().postDelayed(() -> {
            setupData(stories);
            hideRefresh();
        }, 1000);
    }

    private void setupData(List<Story> stories) {
        adapter.clear();
        adapter.addAll(stories);
    }

    @Override
    public void showLoading() {
        primaryLayout.setVisibility(View.INVISIBLE);
        loadDataProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
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
    public void onStoryClicked(Story story) {
        callback.onStoryClicked(story);
        libPresenter.destroy();
    }

    public interface Callback {
        void onStoryClicked(Story story);
    }

}
