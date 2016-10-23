package com.android.nytimessearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vsuryawanshi on 10/20/16.
 */
public class Article implements Serializable{
    String articleUrl;
    String articleDescription;
    String articleImage;

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public String getArticleImage() {
        return "http://www.nytimes.com/" + articleImage;
    }
    public Article(JSONObject article) {
        try {
            this.articleUrl = article.getString("web_url");

            JSONObject headline = article.getJSONObject("headline");
            this.articleDescription = headline.getString("main");

            JSONArray multimedia = article.getJSONArray("multimedia");
            JSONObject imageObject = multimedia.getJSONObject(0);
            this.articleImage = imageObject.getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Article> getArticlesList(JSONArray movies) {
        ArrayList<Article> articles = new ArrayList<Article>();
        try {
            for (int i=0;i<movies.length();i++) {
                articles.add(new Article(movies.getJSONObject(i)));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
