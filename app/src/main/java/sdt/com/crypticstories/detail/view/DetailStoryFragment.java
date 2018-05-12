package sdt.com.crypticstories.detail.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.detail.presenter.DetailPresenter;
import sdt.com.crypticstories.detail.presenter.DetailPresenterImpl;
import sdt.com.crypticstories.pojo.Story;

public class DetailStoryFragment extends Fragment implements DetailView {
    private static final String TAG = "detail";

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

    private Story story;
    private DetailPresenter detailPresenter;
    private String imageTransitionName;
    private boolean isShowed = false;

    public static DetailStoryFragment getInstance(@NonNull Story story, String imageTransitionName) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.STORY, Parcels.wrap(story));
        args.putString(Constants.IMAGE_TRANSITION_NAME, imageTransitionName);
        DetailStoryFragment detailStoryFragment = new DetailStoryFragment();
        detailStoryFragment.setArguments(args);
        return detailStoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        if (args != null) {
            Story story = Parcels.unwrap(args.getParcelable(Constants.STORY));
            String imageTransitionName = args.getString(Constants.IMAGE_TRANSITION_NAME);

            if (story != null && !imageTransitionName.isEmpty()) {
                this.story = story;
                this.imageTransitionName = imageTransitionName;
                detailPresenter = new DetailPresenterImpl();
                detailPresenter.setView(this);
                detailPresenter.showDetail(story);

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
        storyName.setText(story.getNameStory());
        content.setText(story.getContent());
        views.setText("" + story.getViews());

        String[] tmp = story.getReleaseDate().split("\\s+");
        releaseDate.setText(tmp[0] + "");

        explanation.setText(story.getExplaining());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            posterImage.setTransitionName(imageTransitionName);
        }
        Picasso.get()
                .load(Constants.HEADER_URL_IMAGE + story.getPoster())
                .into(posterImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        ((DetailStoryActivity) getContext()).supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        ((DetailStoryActivity) getContext()).supportStartPostponedEnterTransition();
                    }
                });
    }

    @Override
    public void showExplanation() {
        titleExplanation.setVisibility(View.VISIBLE);
        explanation.setVisibility(View.VISIBLE);
        isShowed = true;
        btnReadExplanation.setText("Hide");
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @Override
    public void hideExplanation() {
        titleExplanation.setVisibility(View.GONE);
        explanation.setVisibility(View.GONE);
        isShowed = false;
        btnReadExplanation.setText("Explanation");
    }
}
