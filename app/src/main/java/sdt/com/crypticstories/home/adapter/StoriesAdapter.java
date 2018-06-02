package sdt.com.crypticstories.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sdt.com.crypticstories.R;
import sdt.com.crypticstories.home.view.HomeView;
import sdt.com.crypticstories.pojo.Story;

public class StoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String TAG = "adapter";

    private List<Story> stories;
    private Context context;
    private HomeView homeView;

    private boolean isLoadingAdded = false;

    public StoriesAdapter(List<Story> stories, HomeView homeView) {
        this.stories = stories;
        this.homeView = homeView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;
        View view;

        switch (viewType) {
            case ITEM:
                view = inflater.inflate(R.layout.story_item, parent, false);
                viewHolder = new StoryViewHolder(view);
                break;
            case LOADING:
                view = inflater.inflate(R.layout.item_load_more_progress, parent, false);
                viewHolder = new LoadingMoreViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Story story = stories.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                ((StoryViewHolder) holder).bind(story, homeView);
                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return stories == null ? 0 : stories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == stories.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Story story) {
        stories.add(story);
        notifyItemInserted(stories.size() - 1);
    }

    public void addAll(List<Story> stories) {
        for (Story story : stories) {
            add(story);
        }
    }

    public void remove(Story story) {
        int position = stories.indexOf(story);
        if (position > -1) {
            stories.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        Story story = new Story();
        add(story);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = stories.size() - 1;
        Story removeStory = getItem(position);
        if (removeStory != null) {
            stories.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Story getItem(int position) {
        return stories.get(position);
    }
}
