package com.perich.nytimessearch.adapters;

import android.widget.AbsListView;

/**
 * Created by perich on 3/21/16.
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
    // Number of items below current position before loading more
    private int visibleThreshold = 5;
    // the current offset index of data
    private int currentPage = 0;
    // Total number of items in the data set after last load
    private int previousTotalItemCount = 0;
    // True if we are waiting for the last set of data to load;
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // If totalitemcount is 0, but previous isnt
        // assume the list is invalidated and should be reset
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) { this.loading = true; }
        }

        // if its still loading we check to see if the data set count
        // has changed, if so we conclude it has finished loading
        // and update the current page number and total item count
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        // If it isn't currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do we need to relaod some more data, we execute onLoadMore to fetch data
        if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount) {
            loading = onLoadMore(currentPage + 1, totalItemCount);
        }

    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);

}
