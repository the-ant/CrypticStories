package sdt.com.crypticstories.library.model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import sdt.com.crypticstories.api.StoryAPI;
import sdt.com.crypticstories.pojo.Story;
import sdt.com.crypticstories.utils.RealmUtil;

public class LibInteractionImpl implements LibInteraction {
    private static final String TAG = "lib_interaction";
    private StoryAPI storyAPI;
    private Realm realm = RealmUtil.getLibInstance();

    public LibInteractionImpl(StoryAPI storyAPI) {
        this.storyAPI = storyAPI;
    }

    @Override
    public List<Story> loadLibrary() {
        return RealmUtil.getStories(realm);
    }

    @Override
    public void addToLib(Story story, final onAddedListener addedListener) {
        if (!RealmUtil.isExistStory(realm, story.getId())) {
            RealmUtil.createRealmObject(realm, story);
            addedListener.onFinished("ok");
        } else {
            addedListener.onFailed("existed");
        }
    }
}
