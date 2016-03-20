package com.perich.nytimessearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.perich.nytimessearch.R;
import com.perich.nytimessearch.models.Article;

public class ArticleActivity extends AppCompatActivity {

    WebView wvArticle;

    Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        article = (Article) getIntent().getSerializableExtra("article");
        setWebView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("logger", "It all coming back to me now");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setWebView() {
        wvArticle = (WebView) findViewById(R.id.wvArticle);
        wvArticle.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wvArticle.loadUrl(article.url);
    }
}
