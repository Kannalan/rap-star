package com.example.demoviewpage1122;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MyViewPageAdapter extends PagerAdapter {
    private List<Integer> images;
    private Context context;

    public MyViewPageAdapter(List<Integer> images, Context context) {
        this.context=context;
        this.images=images;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView vp_iv= new ImageView(context);
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(18);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);

        Glide.with(context).load(images.get(position%images.size())).apply(options).into(vp_iv);
        container.addView(vp_iv);
        return vp_iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return (float) 0.65;
    }
}
