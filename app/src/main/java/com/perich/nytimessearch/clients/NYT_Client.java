package com.perich.nytimessearch.clients;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.perich.nytimessearch.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NYT_Client {
    public static final String API_KEY = "b3ad725612bc137132f0540840b7109b:15:74744814";
    public static final String API_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

    public ArrayAdapter adapter;
    public AsyncHttpClient client;
    public RequestParams params;

    public NYT_Client(ArrayAdapter adapt) {
        adapter = adapt;
        client = new AsyncHttpClient();
        params = new RequestParams();
    }

    public void articlesForQuery(String query, int page) {
        params.put("api-key", API_KEY);
        params.put("page", page);
        params.put("q", query);

        client.get(API_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray articleJSONResults = response.getJSONObject("response").getJSONArray("docs");
                    ArrayList<Article> articles = Article.fromJSONArray(articleJSONResults);
                    adapter.addAll(articles);
                } catch (JSONException e) {}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("logger", "Failed because: " + errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
