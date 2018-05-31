package com.zrj.birdnews.ui.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gminibird.birdrecyclerview.adapter.RecyclerViewHolder;
import com.gminibird.birdrecyclerview.item.RecyclerItem;
import com.zrj.birdnews.R;

public class CategoryItem extends RecyclerItem {

    public String mkey;



    public String mValue;
    public boolean isSelect;

    public CategoryItem(String key,String value){
        mkey = key;
        mValue = value;
    }

    @Override
    public int getType() {
        return getClass().getName().hashCode();
    }

    @Override
    public View getView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.more_window_item,parent,false);
    }

    @Override
    public void convert(RecyclerViewHolder holder) {
        TextView textView = holder.getViewById(R.id.category_text);
        textView.setText(mkey);
        if (isSelect){
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorPrimary));
        }else {
            textView.setTextColor(0x8a000000);
        }
    }

    public String getkey() {
        return mkey;
    }

    public void setkey(String key) {
        this.mkey = key;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String Value) {
        this.mValue = Value;
    }
}
