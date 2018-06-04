package sdt.com.crypticstories.library.model;

import java.util.List;

import sdt.com.crypticstories.pojo.Story;

public interface LibInteraction {
    List<Story> loadLibrary();

    void addToLib(Story story, onAddedListener listener);

    interface onAddedListener{
        void onFinished(String msg);
        void onFailed(String msg);
    }
}
