package sdt.com.crypticstories.home.view;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.detail.view.DetailStoryActivity;
import sdt.com.crypticstories.pojo.Story;

public class HomeActivity extends AppCompatActivity implements StoriesFragment.Callback {
    public static final String HOME_FRAGMENT = "HomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbar();
        loadStoriesFragment();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public void onStoryClicked(Story story, ImageView poster) {
        loadStoryDetailFragment(story, poster);
    }

    private void loadStoriesFragment(){
        StoriesFragment storiesFragment = new StoriesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, storiesFragment, HOME_FRAGMENT)
                .commit();
    }

    private void loadStoryDetailFragment(Story story, ImageView poster) {
        Intent intent = new Intent(HomeActivity.this, DetailStoryActivity.class);
        intent.putExtra(Constants.STORY, Parcels.wrap(story));
        intent.putExtra(Constants.IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(poster));

        Pair<View, String> posterPair = Pair.create(poster, ViewCompat.getTransitionName(poster));

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                posterPair
        );
        startActivity(intent, optionsCompat.toBundle());
    }
}
