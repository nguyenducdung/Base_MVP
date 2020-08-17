package com.dungnd.basemvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class EndlessLoadingRecyclerViewAdapter extends RecyclerViewAdapter {
    public static final int VIEW_TYPE_LOADING = -1;
    private int visibleThreshold = 5;
    protected boolean isLoading = false;
    private OnLoadingMoreListener loadingMoreListener;
    private boolean disableLoadMore = false;

    public EndlessLoadingRecyclerViewAdapter(Context context, boolean enableSelectedMode) {
        super(context, enableSelectedMode);
    }

    public void setLoadingMoreListener(OnLoadingMoreListener loadingMoreListener) {
        this.loadingMoreListener = loadingMoreListener;
        enableLoadingMore(loadingMoreListener != null);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE: {
                        Log.d(TAG, "onScrollStateChanged: " + disableLoadMore + "-------" + isLoading);
                        if (disableLoadMore || isLoading) {
                            return;
                        }
                        int lastVisibleItemPosition = 0;
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        int totalItemCount = getItemCount();

                        if (layoutManager instanceof StaggeredGridLayoutManager) {
                            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
                            // get maximum element within the list
                            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
                        } else if (layoutManager instanceof GridLayoutManager) {
                            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof LinearLayoutManager) {
                            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        }
                        if ((lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
                            Log.d(TAG, "onScrollStateChanged - a: " + lastVisibleItemPosition + "-------" + totalItemCount);
                            isLoading = true;
                            if (loadingMoreListener != null) {
                                loadingMoreListener.onLoadMore();
                            }
                        }
                    }
                    break;

                    default: {
                        break;
                    }
                }
            }
        });
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    public void enableLoadingMore(boolean enable) {
        this.disableLoadMore = !enable;
    }

    public void showLoadingItem(boolean isScroll) {
        addModel(null, VIEW_TYPE_LOADING, isScroll);
    }

    public void hideLoadingItem() {
        if (isLoading) {
            removeModel(getItemCount() - 1);
            isLoading = false;
        }
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    protected RecyclerView.ViewHolder solvedOnCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder result;
        switch (viewType) {
            case VIEW_TYPE_LOADING: {
                result = initLoadingViewHolder(parent);
            }
            break;

            default: {
                result = initNormalViewHolder(parent);
            }
            break;
        }
        return result;
    }

    @Override
    protected void solvedOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        switch (viewType) {
            case VIEW_TYPE_LOADING: {
                bindLoadingViewHolder((LoadingViewHolder) viewHolder, position);
            }
            break;

            default: {
                bindNormalViewHolder((NormalViewHolder) viewHolder, position);
            }
            break;
        }
    }

    protected abstract RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent);

    protected abstract void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position);

    public interface OnLoadingMoreListener {
        void onLoadMore();
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.ivLoading)
//        ImageView ivLoading;

        public LoadingViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
//            Glide.with(itemView.getContext())
//                    .asGif()
//                    .load(R.drawable.ic_load_more)
//                    .apply(new RequestOptions().circleCrop())
//                    .into(ivLoading);
        }
    }
}
