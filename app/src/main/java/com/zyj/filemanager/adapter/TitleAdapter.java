package com.zyj.filemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zyj.filemanager.R;
import com.zyj.filemanager.adapter.base.RecyclerViewAdapter;
import com.zyj.filemanager.bean.TitlePath;

import java.util.List;

/**
 * Created by ${zhaoyanjun} on 2017/1/13.
 */

public class TitleAdapter extends RecyclerViewAdapter {

    private List<TitlePath> list ;
    private LayoutInflater mLayoutInflater ;

    public TitleAdapter(Context context , List<TitlePath> list ){
        this.list = list ;
        mLayoutInflater = LayoutInflater.from( context ) ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.title_holder , parent, false) ;
        return new TitleHolder( view );
    }

    @Override
    public void onBindViewHolders(RecyclerView.ViewHolder holder, int position) {
        if ( holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.onBindViewHolder(titleHolder, this, position);
        }
    }

    @Override
    public Object getAdapterData() {
        return list ;
    }

    @Override
    public Object getItem(int positon) {
        return list.get( positon );
    }

    @Override
    public int getItemCount() {
        if ( list == null ) return  0 ;
        return list.size() ;
    }

    public void addItem( TitlePath titlePath ){
        list.add( titlePath ) ;
        notifyItemChanged( list.size() - 1 );
    }

    public void removeItem( int positon ){
        list.remove( positon ) ;
        notifyItemRemoved( positon );
    }

    public void removeLast(){
        if ( list == null ) return ;
        int lastPosition = getItemCount() - 1 ;
        list.remove( lastPosition ) ;
        notifyItemRemoved( lastPosition  );
    }
}
