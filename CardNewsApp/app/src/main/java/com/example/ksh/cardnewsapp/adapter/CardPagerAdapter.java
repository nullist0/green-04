package com.example.ksh.cardnewsapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ksh.cardnewsapp.R;
import com.example.ksh.cardnewsapp.data.Card;

import java.io.File;
import java.util.ArrayList;

public class CardPagerAdapter extends PagerAdapter{
    private ArrayList<Card> cards;
    private ArrayList<View> views = new ArrayList<>();

    private Context ctx;
    private LayoutInflater mLayoutInflater;
    private RequestOptions ro = new RequestOptions();

    public CardPagerAdapter(Context c, ArrayList<Card> cards){

        this.cards = cards;

        this.ctx = c;
        this.mLayoutInflater = LayoutInflater.from(ctx);

        ro.diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        RelativeLayout view = getViewFromLayout(position);
        container.addView(view);
        views.add(view);

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

    public void addCard(int position){
        cards.add(position, new Card(0, "", "", ""));
        notifyDataSetChanged();
    }
    public void removeCard(int position){
        cards.remove(position);
        notifyDataSetChanged();
    }

    public Card getCard(int position){
        return cards.get(position);
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public View getView(int position){
        return views.get(position);
    }

    private RelativeLayout getViewFromLayout(int position){

        RelativeLayout view = (RelativeLayout) mLayoutInflater.inflate(R.layout.view_card, null);

        Card c = cards.get(position);
        ImageView image = view.findViewById(R.id.vc_iv_image);
        TextView title = view.findViewById(R.id.vc_tv_title);
        TextView text = view.findViewById(R.id.vc_tv_text);

        File file = new File(c.getFileDir());
        if(file.exists())
            Glide.with(view)
                    .applyDefaultRequestOptions(ro)
                    .load(new File(c.getFileDir()))
                    .into(image);
        else
            Glide.with(view)
                    .load(R.drawable.white_square)
                    .into(image);
        title.setText(c.getTitle());
        text.setText(c.getText());

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
    }
}
