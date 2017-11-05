package com.example.ksh.cardnewsapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ksh.cardnewsapp.R;
import com.example.ksh.cardnewsapp.data.Card;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by leepyoungwon on 17. 11. 1.
 */

public class CardPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

    private int currentPosition = 0;

    private ArrayList<Card> cards;
    private ArrayList<View> views;

    private Context ctx;
    private LayoutInflater mLayoutInflater;

    public CardPagerAdapter(Context c, ArrayList<Card> cards){
        super();
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
        RelativeLayout view = (RelativeLayout) mLayoutInflater.inflate(R.layout.view_card, null);

        Card c = cards.get(position);
            ImageView image = view.findViewById(R.id.vc_iv_image);
            TextView title = view.findViewById(R.id.vc_tv_title);
            TextView text = view.findViewById(R.id.vc_tv_text);

            File file = new File(c.getFileDir());
            if(file.exists())
                Glide.with(view).load(new File(c.getFileDir())).into(image);
            else
                Glide.with(view).load(R.drawable.white_square).into(image);
            title.setText(c.getTitle());
            text.setText(c.getText());

        views.add(view);
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object o){
        return POSITION_NONE;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public ArrayList<View> getViews(){
        return views;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }
    public Card getCurrentCard(){
        return cards.get(currentPosition);
    }
    public void addCard(){
        cards.add(currentPosition, new Card(0, "", "", ""));
    }
    public void removeCard(){
        cards.remove(currentPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
