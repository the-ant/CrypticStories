package sdt.com.crypticstories.library.view;

import java.util.List;

import sdt.com.crypticstories.base.BaseView;
import sdt.com.crypticstories.pojo.Story;

public interface LibraryView  extends BaseView{

    void showLibrary(List<Story> stories);

    void showLoading();

    void hideLoading();

    void onStoryClicked(Story story);

    void showRefresh();

    void hideRefresh();

    void refreshData(List<Story> stories);
}
