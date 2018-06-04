package sdt.com.crypticstories.library.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.detail.view.DetailStoryActivity;
import sdt.com.crypticstories.home.view.StoriesFragment;
import sdt.com.crypticstories.pojo.Story;

public class LibraryActivity extends AppCompatActivity implements LibraryFragment.Callback {
    private static final String LIB_FRAGMENT = "LIB";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        loadLibraryFragment();
    }

    private void loadLibraryFragment() {
        LibraryFragment libraryFragment = new LibraryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, libraryFragment, LIB_FRAGMENT)
                .commit();
    }

    @Override
    public void onStoryClicked(Story story) {
        Intent intent = new Intent(this, DetailStoryActivity.class);
        intent.putExtra(Constants.STORY, Parcels.wrap(story));
        startActivity(intent);
    }
}
