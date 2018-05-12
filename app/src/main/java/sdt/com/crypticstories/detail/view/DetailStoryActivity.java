package sdt.com.crypticstories.detail.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.parceler.Parcels;

import sdt.com.crypticstories.Constants;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.pojo.Story;

public class DetailStoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_story);
        supportPostponeEnterTransition();

        Intent intent = getIntent();
        if (intent != null){
            Story story = Parcels.unwrap(intent.getParcelableExtra(Constants.STORY));
            String imageTransitionName = intent.getStringExtra(Constants.IMAGE_TRANSITION_NAME);
            if (story != null){
                DetailStoryFragment detailStoryFragment = DetailStoryFragment.getInstance(story, imageTransitionName);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, detailStoryFragment)
                        .commit();
            }
        }
    }
}
