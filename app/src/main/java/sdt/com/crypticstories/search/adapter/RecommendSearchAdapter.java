package sdt.com.crypticstories.search.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sdt.com.crypticstories.R;
import sdt.com.crypticstories.base.BaseView;
import sdt.com.crypticstories.pojo.ResultSearch;
import sdt.com.crypticstories.search.view.SearchView;

public class RecommendSearchAdapter extends RecyclerView.Adapter<RecommendSearchAdapter.RecommendSearchVh> {
    private static final String TAG = "recommend_search_adapter";
    private List<ResultSearch> resultSearches;
    private Context context;
    private SearchView baseView;

    public RecommendSearchAdapter(List<ResultSearch> resultSearches, SearchView baseView) {
        this.resultSearches = resultSearches;
        this.baseView = baseView;
    }

    @NonNull
    @Override
    public RecommendSearchVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.recommend_search_item, parent, false);
        return new RecommendSearchVh(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull RecommendSearchVh holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        ResultSearch resultSearch = resultSearches.get(position);
        holder.bind(resultSearch, baseView);
    }

    @Override
    public int getItemCount() {
        return resultSearches.size();
    }

    @SuppressLint("LongLogTag")
    public void add(ResultSearch story) {
        Log.d(TAG, "add: " + story.getNameStory());
        resultSearches.add(story);
        notifyItemInserted(resultSearches.size() - 1);
    }

    public void addAll(List<ResultSearch> list) {
        for (ResultSearch story : list) {
            add(story);
        }
    }

    public void remove(ResultSearch story) {
        int position = resultSearches.indexOf(story);
        if (position > -1) {
            resultSearches.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private ResultSearch getItem(int position) {
        return resultSearches.get(position);
    }

    public class RecommendSearchVh extends RecyclerView.ViewHolder {
        @BindView(R.id.story_name)
        TextView storyName;

        public RecommendSearchVh(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("LongLogTag")
        public void bind(ResultSearch resultSearch, SearchView baseView) {
            Log.d(TAG, "bind: ");
            storyName.setText(resultSearch.getNameStory());
            itemView.setOnClickListener(v -> baseView.onRecommendSearchClick(resultSearch));
        }
    }
}
