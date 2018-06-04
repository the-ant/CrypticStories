package sdt.com.crypticstories.detail.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.api.StoryService;
import sdt.com.crypticstories.detail.adapter.RecommendAdapter;
import sdt.com.crypticstories.detail.model.DetailInteraction;
import sdt.com.crypticstories.detail.model.DetailInteractionImpl;
import sdt.com.crypticstories.detail.presenter.DetailPresenter;
import sdt.com.crypticstories.detail.presenter.DetailPresenterImpl;
import sdt.com.crypticstories.pojo.Story;

public class DetailStoryFragment extends Fragment implements DetailView {
    private static final String TAG = "detail_fragment";

    @BindView(R.id.root)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.load_detail_progress)
    ProgressBar loadingProgressBar;
    @BindView(R.id.poster)
    ImageView posterImage;
    @BindView(R.id.story_name)
    TextView storyName;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.views)
    TextView views;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.explanation)
    TextView explanation;
    @BindView(R.id.title_explanation)
    TextView titleExplanation;
    @BindView(R.id.read_explanation_buton)
    Button btnReadExplanation;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.list_recommend)
    RecyclerView listRecommend;
    @BindView(R.id.add_lib_button)
    ImageView btnAddLib;

    private DetailPresenter detailPresenter;
    private boolean isShowed = false;
    private List<Story> stories = new ArrayList<>();
    private RecommendAdapter recommendAdapter;
    private Story story;
    private Callback callback;

    public static DetailStoryFragment getInstance(@NonNull Story story) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.STORY, Parcels.wrap(story));
        DetailStoryFragment detailStoryFragment = new DetailStoryFragment();
        detailStoryFragment.setArguments(args);
        return detailStoryFragment;
    }

    public interface Callback {
        void onStoryClicked(Story story);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (this.story != null) showDetail(this.story);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        detailPresenter.destroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (DetailStoryFragment.Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_story, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        initUI();
        initListRecommend();
        initDetail(args);
        Log.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initUI() {
        ((DetailStoryActivity) getActivity()).setSupportActionBar(toolbar);
        ((DetailStoryActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((DetailStoryActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((DetailStoryActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        collapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitle("");
        ((DetailStoryActivity) getActivity()).getSupportActionBar().setTitle("");

        btnAddLib.setOnClickListener(v -> showAlert());
    }

    private void initListRecommend() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        listRecommend.setLayoutManager(layoutManager);
        listRecommend.setHasFixedSize(true);
        listRecommend.setItemAnimator(new DefaultItemAnimator());

        recommendAdapter = new RecommendAdapter(stories, this);
        listRecommend.setAdapter(recommendAdapter);
    }

    private void initDetail(Bundle args) {
        DetailInteraction detailInteraction = new DetailInteractionImpl(StoryService.getAPI());
        detailPresenter = new DetailPresenterImpl(detailInteraction);
        detailPresenter.setView(this);

        if (args != null) {
            Story story = Parcels.unwrap(args.getParcelable(Constants.STORY));
            if (story != null) {
                showDetail(story);
                btnReadExplanation.setOnClickListener(v -> {
                    if (!isShowed)
                        detailPresenter.showExplanation();
                    else
                        detailPresenter.hideExplanation();
                });
            }
        }
    }

    @Override
    public void showDetail(Story story) {
        new Handler().postDelayed(() -> {
            this.story = story;
            setupData(story);
            detailPresenter.setRecommendedList();
            hideLoading();
        }, 500);
    }

    private void setupData(Story story) {
        detailPresenter.checkAddedLib(story.getId());
        storyName.setText(story.getNameStory());
        content.setText(story.getContent());
        views.setText("" + story.getViews().toString());

        String[] tmp = story.getReleaseDate().split("\\s+");
        releaseDate.setText(tmp[0] + "");
        explanation.setText(story.getExplaining());

        Picasso.get().load(Constants.HEADER_URL_IMAGE + story.getPoster()).into(posterImage);
    }

    @Override
    public void updateButtonAddLib(boolean isAdded) {
        if (isAdded) {
            btnAddLib.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_ok));
            btnAddLib.setEnabled(false);
        }
    }

    @Override
    public void notifyAddLib(boolean successful) {
        if (successful) {
            showAddLibAlert("Added to Library!");
            updateButtonAddLib(true);
        } else {
            showAddLibAlert("Can't add to Library!");
        }
    }

    @Override
    public void showExplanation() {
        titleExplanation.setVisibility(View.VISIBLE);
        explanation.setVisibility(View.VISIBLE);
        isShowed = true;
        btnReadExplanation.setText("Hide");
    }

    @Override
    public void hideExplanation() {
        titleExplanation.setVisibility(View.GONE);
        explanation.setVisibility(View.GONE);
        isShowed = false;
        btnReadExplanation.setText("Explain");
    }

    @Override
    public void onStoryClicked(Story story) {
        detailPresenter.setViewsStory(story);
        callback.onStoryClicked(story);
        detailPresenter.destroy();
    }

    @Override
    public void showRecommendedStories(List<Story> list) {
        recommendAdapter.clear();
        for (Story story : list) {
            if (story.getId() != this.story.getId()) {
                recommendAdapter.add(story);
            }
        }
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading: ");
        loadingProgressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Library");
        alertDialog.setMessage("Add \"" + this.story.getNameStory() + "\" to Library");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            detailPresenter.addToLib(this.story);
            dialog.dismiss();
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", ((dialog, which) -> dialog.dismiss()));
        alertDialog.show();
    }

    private void showAddLibAlert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Library");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.show();
    }
}
