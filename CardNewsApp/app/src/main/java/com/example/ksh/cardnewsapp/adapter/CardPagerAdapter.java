package com.example.ksh.cardnewsapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ksh.cardnewsapp.R;
import com.example.ksh.cardnewsapp.data.Card;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class CardPagerAdapter extends PagerAdapter {

    private ArrayList<Card> cards;
    private ArrayList<View> views;

    private Context ctx;
    private LayoutInflater mLayoutInflater;

    public CardPagerAdapter(Context c, ArrayList<Card> cards){
        this.cards = cards;

        this.ctx = c;
        this.mLayoutInflater = LayoutInflater.from(ctx);

        views = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = mLayoutInflater.inflate(R.layout.view_card, container);

        Card c = cards.get(position);

        if(view != null){
            ImageView image = (ImageView)view.findViewById(R.id.vc_iv_image);
            TextView title = (TextView)view.findViewById(R.id.vc_tv_title);
            TextView text = (TextView)view.findViewById(R.id.vc_tv_text);

            Glide.with(view).load(new File(c.getFileDir())).into(image);
            title.setText(c.getTitle());
            text.setText(c.getText());
        }

        views.add(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public ArrayList<View> getViews(){
        return views;
    }
}
