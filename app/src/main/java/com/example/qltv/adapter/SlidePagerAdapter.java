package com.example.qltv.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.qltv.R;
import com.example.qltv.model.BookModel;

public class SlidePagerAdapter extends PagerAdapter {

    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.slide_title)
    TextView slideTitle;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    private Context mContext;
    private List<BookModel> mList;

    public SlidePagerAdapter(Context mContext, List<BookModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideshow = inflater.inflate(R.layout.slide_item, null);
        ButterKnife.bind(this, slideshow);
        imageBack.setImageResource(mList.get(position).getThumballBook());
        slideTitle.setText(mList.get(position).getName());
        container.addView(slideshow);
        return slideshow;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }
}
