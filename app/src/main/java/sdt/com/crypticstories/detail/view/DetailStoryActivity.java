package sdt.com.crypticstories.detail.view;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.parceler.Parcels;

import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.pojo.Story;

public class DetailStoryActivity extends AppCompatActivity implements DetailStoryFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);
        initTransition();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Story story = Parcels.unwrap(intent.getParcelableExtra(Constants.STORY));
            if (story != null) {
                loadDetailStoryFragment(story);
            }
        }
    }

    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fade_in);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onStoryClicked(Story story) {
        loadDetailStoryFragment(story);
    }

    private void loadDetailStoryFragment(Story story) {
        DetailStoryFragment detailStoryFragment = DetailStoryFragment.getInstance(story);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailStoryFragment)
                .commit();
    }
}
