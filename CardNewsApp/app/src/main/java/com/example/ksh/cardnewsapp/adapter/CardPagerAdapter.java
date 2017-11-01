package com.example.ksh.cardnewsapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.ksh.cardnewsapp.data.Card;

import java.util.ArrayList;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class CardPagerAdapter extends PagerAdapter {

    private ArrayList<Card> cards;

    public CardPagerAdapter(ArrayList<Card> cards){
        this.cards = cards;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        //TODO
        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
