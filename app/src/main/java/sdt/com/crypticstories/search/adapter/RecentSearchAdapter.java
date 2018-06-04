package sdt.com.crypticstories.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.search.view.SearchView;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.RecentSearchVH> {
    private List<ResultSearch> recentSearches;
    private Context context;
    private SearchView baseView;

    public RecentSearchAdapter(List<ResultSearch> recentSearches, SearchView baseView) {
        this.recentSearches = recentSearches;
        this.baseView = baseView;
    }

    @NonNull
    @Override
    public RecentSearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.recent_item, parent, false);
        RecentSearchVH viewHolder = new RecentSearchVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchVH holder, int position) {
        ResultSearch recentSearch = recentSearches.get(position);
        holder.bind(recentSearch, baseView);
    }

    @Override
    public int getItemCount() {
        return recentSearches.size();
    }

    public void add(ResultSearch story) {
        recentSearches.add(story);
        notifyItemInserted(recentSearches.size() - 1);
    }

    public void addAll(List<ResultSearch> recentSearches) {
        for (ResultSearch story : recentSearches) {
            add(story);
        }
    }

    public void remove(ResultSearch story) {
        int position = recentSearches.indexOf(story);
        if (position > -1) {
            recentSearches.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private ResultSearch getItem(int position) {
        return recentSearches.get(position);
    }

    public class RecentSearchVH extends RecyclerView.ViewHolder {
        @BindView(R.id.recent_text)
        TextView recentText;

        public RecentSearchVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(ResultSearch recentSearch, SearchView baseView) {
            recentText.setText(recentSearch.getNameStory());
            itemView.setOnClickListener(v -> baseView.onRecentClicked(recentSearch));
        }
    }
}
