package com.perich.nytimessearch.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.perich.nytimessearch.R;
import com.perich.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    // View elements
    ImageView ivThumb;
    TextView tvHeadline;
    TextView tvSnippet;
    TextView tvSource;
    TextView tvPubDate;

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_view, parent, false);
        }
        getViewElements(convertView);
        setViewElements(article);
        return convertView;
    }

    public void getViewElements(View view) {
        ivThumb         = (ImageView) view.findViewById(R.id.ivThumb);
        tvHeadline      = (TextView) view.findViewById(R.id.tvHeadline);
        tvSnippet       = (TextView) view.findViewById(R.id.tvSnippet);
        tvSource        = (TextView) view.findViewById(R.id.tvSource);
        tvPubDate       = (TextView) view.findViewById(R.id.tvPubDate);
    }

    public void setViewElements(Article article) {
        // Headline
        tvHeadline.setText(article.headline);
        // Snippet - add some spaces to indent the paragraph
        tvSnippet.setText("        " + article.snippet);
        // Source - if it exists, or else remove the TextView
        if(article.source != null && !article.source.isEmpty()) {
            tvSource.setText(article.source);
            tvSource.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            tvSource.setVisibility(View.GONE);
        }
        // Publish date
        tvPubDate.setText(article.pubDateString());
        // Image thumbnail - Clear in case of recylced view
        ivThumb.setImageResource(0);
        if(article.image_url != null && !article.image_url.isEmpty()) {
            Picasso.with(getContext()).load(article.image_url).into(ivThumb);
        }
    }
}
