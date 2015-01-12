package com.github.iscanner.iscanner_android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwipeableCellAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<?> data;    
    private int mRightWidth = 0;
    private onRightItemClickListener mListener = null;

    public SwipeableCellAdapter(Context ctx,List<?> data, int rightWidth) {
        mContext = ctx;
        this.data = data;
        mRightWidth = rightWidth;
    }

    @Override
    public int getCount() {
    	return data.size();  
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, parent, false);
            holder = new ViewHolder();
            holder.cell_content = (RelativeLayout)convertView.findViewById(R.id.cell_content);
            holder.item_right = (RelativeLayout)convertView.findViewById(R.id.item_right);
            holder.content = (TextView)convertView.findViewById(R.id.content);
            holder.create_button = (Button)convertView.findViewById(R.id.create_button);
            holder.copy_button = (TextView)convertView.findViewById(R.id.copy_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        
        LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        holder.cell_content.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        holder.item_right.setLayoutParams(lp2);
        holder.content.setText((CharSequence) data.get(position));
        holder.create_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickCreateButton(v, position);
                }
            }
        });
        holder.copy_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickCopyButton(v, position);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
    	RelativeLayout cell_content;
    	RelativeLayout item_right;
        TextView content;
        Button create_button;
        TextView copy_button;
    }
    
    public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    }

    public interface onRightItemClickListener {
        void clickCreateButton(View v, int position);
        void clickCopyButton(View v, int position);
    }
}
