package com.example.demoviewpage1122;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.demoviewpage1122.ViewPage.MyGallyPageTransformer;
import com.example.demoviewpage1122.ViewPage.MyViewPageAdapter;
import com.lzy.widget.HeaderViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private List<Integer> images=new ArrayList<>();
    private HeaderViewPager scrollableLayout;
    private ViewPager pagerHeader;
    private View titleBar_Bg;
    private TextView titleBar_title;
    private View status_bar_fix;
    private View titleBar;
    private List<Accompany> acclist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏title
        setContentView(R.layout.activity_main);
        AccAdapter customAdapter = new AccAdapter(MainActivity.this,acclist,R.layout.acc_item);
        ListView ListView = findViewById(R.id.acc_list);
        ListView.setAdapter(customAdapter);

        //viewpage相关
        viewpage();

        InitData();


    }

    private void viewpage() {
        vp = findViewById(R.id.vp);
        images.add(R.mipmap.img3);
        images.add(R.mipmap.img4);
        images.add(R.mipmap.img5);
        vp.setOffscreenPageLimit(3);
        int pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 4.0f / 5.0f);
        ViewGroup.LayoutParams lp = vp.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        vp.setLayoutParams(lp);
//setPageMargin表示设置图片之间的间距
        vp.setPageMargin(1);
//setPageMargin表示设置图片之间的间距
        vp.setPageTransformer(true, new MyGallyPageTransformer());
        vp.setAdapter(new MyViewPageAdapter(images, this));
        vp.setCurrentItem(2000);
//        pagerHeader = (ViewPager) findViewById(R.id.pagerHeader);
//        pagerHeader.setAdapter(new HeaderAdapter());
//        CircleIndicator ci = (CircleIndicator) findViewById(R.id.ci);
//        ci.setViewPager(pagerHeader);

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        //当前窗口获取焦点时，才能正真拿到titlebar的高度，此时将需要固定的偏移量设置给scrollableLayout即可
//        scrollableLayout.setTopOffset(titleBar.getHeight());
//    }


    }
    private void InitData() {
        Accompany s1 = new Accompany(1, "MONST3ER-Demo","s" , 1, "1", "ATEEZ-", 1216);

        acclist.add(s1);acclist.add(s1);
        acclist.add(s1);acclist.add(s1);acclist.add(s1);
    }


}
