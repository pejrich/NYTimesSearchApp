package com.perich.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.perich.nytimessearch.clients.NYT_Client;
import com.perich.nytimessearch.R;
import com.perich.nytimessearch.adapters.ArticleArrayAdapter;
import com.perich.nytimessearch.models.Article;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    NYT_Client nytClient;

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setupVars();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupVars() {
        // setup views
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        // Initialize variables
        articles = new ArrayList<>();
        // Initialize Adapter
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);
        nytClient = new NYT_Client(adapter);
        // setup view onclick listener
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get selected article
                Article article = articles.get(position);
                // setup intent
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                // add data to intent
                i.putExtra("article", article);
                // Start intent
                startActivity(i);
            }
        });
    }

    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_LONG).show();
        articles.clear();
        int page_number = 1;
        nytClient.articlesForQuery(query, page_number);
        closeKeyboard();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
