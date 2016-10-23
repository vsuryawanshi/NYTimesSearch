package com.android.nytimessearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vsuryawanshi on 10/20/16.
 */
public class GridAdapter extends ArrayAdapter<Article> {
    public GridAdapter(Context context,List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_article_item, parent, false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.articleImage);
        imageView.setImageResource(0);
        TextView articleTitle = (TextView)convertView.findViewById(R.id.articleText);
        articleTitle.setText(article.getArticleDescription());
        if(TextUtils.isEmpty(article.getArticleImage())) {
            Picasso.with(getContext()).load(article.getArticleImage()).into(imageView);
        }
        Picasso.with(getContext()).load(article.getArticleImage()).into(imageView);
        return convertView;
    }
}
