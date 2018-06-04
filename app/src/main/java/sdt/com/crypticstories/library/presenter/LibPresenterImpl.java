package sdt.com.crypticstories.library.presenter;

import java.util.List;

import sdt.com.crypticstories.library.model.LibInteraction;
import sdt.com.crypticstories.library.view.LibraryView;
import sdt.com.crypticstories.pojo.Story;

public class LibPresenterImpl implements LibPresenter {
    private LibraryView libraryView;
    private LibInteraction libInteraction;

    public LibPresenterImpl(LibInteraction libInteraction) {
        this.libInteraction = libInteraction;
    }

    @Override
    public void setView(LibraryView view) {
        this.libraryView = view;
    }

    @Override
    public void reloadData() {
        List<Story> stories = libInteraction.loadLibrary();
        if (stories != null) {
            libraryView.refreshData(stories);
        }
    }

    @Override
    public void loadLibrary() {
        libraryView.showLoading();
        List<Story> stories = libInteraction.loadLibrary();
        if (stories != null) {
            libraryView.showLibrary(stories);
        }
    }

    @Override
    public void destroy() {
        this.libraryView = null;
    }
}
