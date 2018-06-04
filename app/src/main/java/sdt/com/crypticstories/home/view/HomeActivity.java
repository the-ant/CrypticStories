package sdt.com.crypticstories.home.view;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.parceler.Parcels;

import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.detail.view.DetailStoryActivity;
import sdt.com.crypticstories.library.view.LibraryActivity;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.search.view.SearchActivity;

public class HomeActivity extends AppCompatActivity implements StoriesFragment.Callback {
    public static final String HOME_FRAGMENT = "HomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbar();
        loadStoriesFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startSearchActivity();
                break;
            case R.id.library:
                startLibraryActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startLibraryActivity() {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }

    private void startSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
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
    public void onStoryClicked(Story story) {
        loadStoryDetailFragment(story);
    }

    private void loadStoriesFragment() {
        StoriesFragment storiesFragment = new StoriesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, storiesFragment, HOME_FRAGMENT)
                .commit();
    }

    private void loadStoryDetailFragment(Story story) {
        Intent intent = new Intent(HomeActivity.this, DetailStoryActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        intent.putExtra(Constants.STORY, Parcels.wrap(story));
        startActivity(intent, optionsCompat.toBundle());
    }

}
