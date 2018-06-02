package sdt.com.crypticstories.home.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.home.view.HomeView;
import sdt.com.crypticstories.pojo.Story;

public class StoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.loading_image_progress)
    ProgressBar loadingImageProgressBar;

    public StoryViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(final Story story, HomeView homeView) {
        loadingImageProgressBar.setVisibility(View.VISIBLE);
        poster.setImageDrawable(null);
        title.setText(story.getNameStory());
        description.setText(story.getContent());
        cardView.setPreventCornerOverlap(false);

        String posterUrl = Constants.HEADER_URL_IMAGE + story.getPoster();
        new Handler().postDelayed(() -> {
            Picasso.get()
                    .load(posterUrl)
                    .placeholder(R.drawable.ic_loading)
                    .into(target);
        }, 1000);
        itemView.setOnClickListener(v -> homeView.onStoryClicked(story));
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            loadingImageProgressBar.setVisibility(View.GONE);
            poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
            poster.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            loadingImageProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            loadingImageProgressBar.setVisibility(View.GONE);
        }
    };
}
