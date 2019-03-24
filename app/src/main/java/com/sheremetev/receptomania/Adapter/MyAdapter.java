package com.sheremetev.receptomania.Adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.sheremetev.receptomania.Interface.ILoadMore;
import com.sheremetev.receptomania.Model.Item;
import com.sheremetev.receptomania.R;
import com.sheremetev.receptomania.Model.Img;

import static android.content.ContentValues.TAG;

class LoadingViewHolder extends RecyclerView.ViewHolder {




    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    ImageView imageView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.txtName);
        imageView = (ImageView) itemView.findViewById(R.id.img);
    }
}


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Item> items;
    List<Img> img;
    int visibleThreshold = 5;
    int lastVisibleItem;
    int totalItemCount;

    public MyAdapter(RecyclerView recyclerView, Activity activity, List<Item> items, List<Img> img) {
        this.activity = activity;
        this.items = items;
        this.img = img;

        final LinearLayoutManager linearLayoutManager
                = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                    if (loadMore != null) {
                        loadMore.onLoadMore();
                    }
                    isLoading = true;
                }


            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    private Listener listener;
    public interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            Item item = items.get(position);
            Img image = img.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            Glide.with(activity).load(image.getName()).into(viewHolder.imageView);
            //viewHolder.number.setText(image.getName());
            viewHolder.name.setText(item.getName());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            loadingHolder.progressBar.setIndeterminate(true);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public int getImgCount() {
        return img.size();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();
        Log.d(TAG, "onViewAttachedToWindow: getayoutPosition = " + layoutPosition);

        layoutPosition = holder.getAdapterPosition();
        Log.d(TAG, "onViewAttachedToWindow: getAdapterPosition = " + layoutPosition);

    }
}