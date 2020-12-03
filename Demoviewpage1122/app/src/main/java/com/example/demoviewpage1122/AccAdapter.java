package com.example.demoviewpage1122;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class AccAdapter extends BaseAdapter {
    private String aname;
    private String aauthor;
    private int alistenernum;//收听人数
    private String aphoto;

    private TextView tvname;
    private TextView tvauthor;
    private TextView tvnum;
    private ImageView ivpho;

    private Context mContext;
    private List<Accompany> acclist = new ArrayList<>();
    private int itemLayoutRes;

    public AccAdapter(Context mContext, List<Accompany> acclist, int itemLayoutRes) {
        this.mContext = mContext;
        this.acclist = acclist;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (null != acclist) {
            return acclist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != acclist) {
            return acclist.get(position);
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
        convertView = inflater.inflate(R.layout.acc_item, null);
        //获取item控件的引用
        ivpho = convertView.findViewById(R.id.acc_item_pho);
        tvname = convertView.findViewById(R.id.acc_item_name);
        tvauthor = convertView.findViewById(R.id.acc_item_author);
        tvnum = convertView.findViewById(R.id.acc_item_lisnum);
        ImageView start;
        start = convertView.findViewById(R.id.acc_item_start);

        //设置控件内容
        aphoto = acclist.get(position).getCoverpath();
        aname = acclist.get(position).getName();
        aauthor = acclist.get(position).getAuthor();
        alistenernum = acclist.get(position).getUsednum();

        //圆角
        ivpho.setImageResource(R.drawable.pit);
        //Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.pit);
        //Bitmap outBitmap =getRoundBitmapByShader(bitmap1, 500,500,100, 0);
        //ivpho.setImageBitmap(outBitmap);

        tvname.setText(acclist.get(position).getName());
        tvauthor.setText(acclist.get(position).getAuthor());
        tvnum.setText(acclist.get(position).getUsednum() + "");

        MyListener myListener = new MyListener();
        start.setOnClickListener(myListener);
        tvname.setOnClickListener(myListener);
        tvauthor.setOnClickListener(myListener);
        return convertView;
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.acc_item_start:
                    break;
                case R.id.acc_item_name:
                    break;
                case R.id.acc_item_author:
                    break;
            }
        }
    }
}
