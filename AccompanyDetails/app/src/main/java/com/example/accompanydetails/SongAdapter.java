package com.example.accompanydetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.accompanydetails.RoundImageView.getRoundBitmapByShader;


class SongAdapter extends BaseAdapter {
    private String sname;
    private String sauthor;
    private int slistenernum;//收听人数
    private String photo;

    private TextView tvname;
    private TextView tvauthor;
    private TextView tvnum;
    private ImageView ivpho;

    private Context mContext;
    private List<Song> songlist = new ArrayList<>();
    private int itemLayoutRes;

    public SongAdapter(Context mContext, List<Song> songlist, int itemLayoutRes){
        this.mContext = mContext;
        this.songlist = songlist;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (null!=songlist){
            return songlist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null!= songlist){
            return songlist.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载item的布局文件
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.song_item,null);
        //获取item控件的引用
        ivpho = convertView.findViewById(R.id.song_item_pho);
        tvname = convertView.findViewById(R.id.song_item_name);
        tvauthor = convertView.findViewById(R.id.song_item_author);
        tvnum = convertView.findViewById(R.id.song_item_lisnum);
        ImageView start ;
        start = convertView.findViewById(R.id.song_item_start);

        //设置控件内容
        photo = songlist.get(position).getImgpath();
        sname = songlist.get(position).getName();
        sauthor = songlist.get(position).getAuthor();
        slistenernum = songlist.get(position).getListenernum();

        //圆角
        ivpho.setImageResource(R.mipmap.pit);
        //Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.pit);
        //Bitmap outBitmap =getRoundBitmapByShader(bitmap1, 500,500,100, 0);
        //ivpho.setImageBitmap(outBitmap);

        tvname.setText(songlist.get(position).getName());
        tvauthor.setText(songlist.get(position).getAuthor());
        tvnum.setText(songlist.get(position).getListenernum()+"");

        MyListener myListener =new MyListener();
        start.setOnClickListener(myListener);
        tvname.setOnClickListener(myListener);
        tvauthor.setOnClickListener(myListener);
        return convertView;
    }
    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.song_item_start:
                    break;
                case R.id.song_item_name:
                    break;
                case R.id.song_item_author:
                    break;
            }
        }
    }
}
