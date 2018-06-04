package sdt.com.crypticstories.library.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sdt.com.crypticstories.R;
import sdt.com.crypticstories.base.BaseView;
import sdt.com.crypticstories.library.view.LibraryView;
import sdt.com.crypticstories.pojo.Story;

public class LibAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Story> stories;
    private Context context;
    private BaseView baseView;

    public LibAdapter(List<Story> stories, BaseView baseView) {
        this.stories = stories;
        this.baseView = baseView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.lib_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new LibVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Story story = stories.get(position);
        ((LibVH) holder).bind(story, baseView);
    }

    @Override
    public int getItemCount() {
        return stories == null ? 0 : stories.size();
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
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private Story getItem(int position) {
        return stories.get(position);
    }
}
