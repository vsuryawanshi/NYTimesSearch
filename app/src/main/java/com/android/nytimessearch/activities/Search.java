package com.android.nytimessearch.activities;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.nytimessearch.GridAdapter;
import com.android.nytimessearch.R;
import com.android.nytimessearch.helpers.EndlessScrollListener;
import com.android.nytimessearch.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class Search extends ActionBarActivity {
    EditText searchText;
    GridView articleList;
    GridAdapter gridAdapter;
    ArrayList<Article> articles;
    String sortOption;
    String beginDate;
    String newsDesk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchText=(EditText)findViewById(R.id.searchText);
        articleList=(GridView)findViewById(R.id.articleView);
        articles = new ArrayList<Article>();
        gridAdapter=new GridAdapter(this,articles);
        articleList.setAdapter(gridAdapter);
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = articles.get(position);
                Intent i = new Intent(getApplicationContext(), ArticleViewActivity.class);
                i.putExtra("article", article);
                startActivity(i);
            }
        });
        articleList.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }
    public void customLoadMoreDataFromApi(int offset) {
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        String newsDeskFilter = null;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String encodedUrl=null;
        try {
            if(newsDesk!=null) {
                encodedUrl = URLEncoder.encode(newsDesk, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        newsDeskFilter = "news_desk:(\"" + encodedUrl +"\")";
        requestParams.put("api-key","bd0fa146cafc4d8f92e219d14075a92a");
        requestParams.put("q",searchText.getText().toString());
        requestParams.put("begin_date", beginDate);
        requestParams.put("sort", sortOption);
        requestParams.put("fw", newsDeskFilter);
        requestParams.put("page", offset);
        client.get(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray docs = response.getJSONObject("response").getJSONArray("docs");
                    articles = Article.getArticlesList(docs);
                    gridAdapter.addAll(articles);
                    gridAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void searchArticle(View view) {
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        String newsDeskFilter = null;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String encodedUrl=null;
        try {
            if(newsDesk!=null) {
                encodedUrl = URLEncoder.encode(newsDesk, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        newsDeskFilter = "news_desk:(\"" + encodedUrl +"\")";
        requestParams.put("api-key","bd0fa146cafc4d8f92e219d14075a92a");
        requestParams.put("q",searchText.getText().toString());
        requestParams.put("begin_date", beginDate);
        requestParams.put("sort", sortOption);
        requestParams.put("fw", newsDeskFilter);

        client.get(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray docs = response.getJSONObject("response").getJSONArray("docs");
                    articles = Article.getArticlesList(docs);
                    gridAdapter.addAll(articles);
                    gridAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void showFilterView(View view) {
        Intent i=new Intent(this,FilterActivity.class);
        startActivityForResult(i, 20);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle dataBundle = data.getExtras();
            sortOption = dataBundle.getString("sortOption");
            newsDesk = dataBundle.getString("newsDesk");
            beginDate = dataBundle.getString("date");
        }
    }
}
