package sdt.com.crypticstories.detail.view;

import sdt.com.crypticstories.pojo.Story;

public interface DetailView {
    void showDetail(Story story);

    void showExplanation();

    void hideExplanation();
}
