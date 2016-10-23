package com.android.nytimessearch.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.nytimessearch.R;
import com.android.nytimessearch.models.Article;

public class ArticleViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);
        WebView articleWebView = (WebView)findViewById(R.id.webviewArticle);
        Article article = (Article)getIntent().getSerializableExtra("article");
        articleWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        articleWebView.loadUrl(article.getArticleUrl());
    }

}
