package sdt.com.crypticstories.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.parceler.Parcels;

import sdt.com.crypticstories.R;
import sdt.com.crypticstories.databinding.ActivityHomeBinding;
import sdt.com.crypticstories.detail.DetailStoryActivity;
import sdt.com.crypticstories.pojos.Story;

public class HomeActivity extends AppCompatActivity implements StoriesFragment.Callback {
    public static final String HOME_FRAGMENT = "HomeFragment";
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setToolbar();
        loadStoriesFragment();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.home_title);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public void onStoryClicked(Story story) {
        loadStoryDetailFragment(story);
    }

    private void loadStoriesFragment(){
        StoriesFragment storiesFragment = new StoriesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, storiesFragment, HOME_FRAGMENT)
                .commit();
    }

    private void loadStoryDetailFragment(Story story) {
        Intent intent = new Intent(HomeActivity.this, DetailStoryActivity.class);
        intent.putExtra("story", Parcels.wrap(story));
        startActivity(intent);
    }
}
