package com.example.accompanydetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.accompanydetails.RoundImageView.getRoundBitmapByShader;

public class MainActivity extends AppCompatActivity {
    private ImageView imgview;
    private ImageView img;
    private List<Song> songlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏title
        setContentView(R.layout.activity_main);
      //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        SongAdapter customAdapter = new SongAdapter(MainActivity.this,songlist,R.layout.song_item);
        ListView ListView = findViewById(R.id.song_list);
       ListView.setAdapter(customAdapter);

        initData();


        //img.setImageResource(R.mipmap.pit);
        //该方法可从资源文件中读取图片信息。第一个参数一般传getResources(),第二个参数传drawable图片的资源id，如下
        //虚化
        imgview = findViewById(R.id.imgview);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.pit);
        imgview.setImageBitmap(BlurUtil.doBlur(bitmap,10,15));
        //圆角
        img = findViewById(R.id.img);
        Bitmap bitmap1 =BitmapFactory.decodeResource(getResources(), R.mipmap.pit);
        Bitmap outBitmap =getRoundBitmapByShader(bitmap1, 500,500,40, 0);
        img.setImageBitmap(outBitmap);
    }

    private void initData() {
        Song s1 = new Song(1,"-ATEEZ","MONST3ER-Demo",1,"1",1,"1",1,"1",1216);

        songlist.add(s1);
        songlist.add(s1);
        songlist.add(s1);
        songlist.add(s1);songlist.add(s1);songlist.add(s1);songlist.add(s1);
    }
}
