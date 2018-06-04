package sdt.com.crypticstories.library.presenter;

import sdt.com.crypticstories.library.view.LibraryView;

public interface LibPresenter {
    void setView(LibraryView view);

    void reloadData();

    void loadLibrary();

    void destroy();
}
