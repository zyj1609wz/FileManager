package com.zyj.filemanager.adapter.base;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zyj.filemanager.adapter.FileAdapter;

/**
 * Created by ${zhaoyanjun} on 2017/1/12.
 */

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder viewHolder , int position);
    }

    public interface OnItemLongClickListener{
        boolean onItemLongClick(View view , RecyclerView.ViewHolder viewHolder , int position);
    }

    public FileAdapter.OnItemClickListener onItemClickListener;
    public FileAdapter.OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(FileAdapter.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener( FileAdapter.OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition() ;
                    onItemClickListener.onItemClick( v , holder , pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if ( onItemLongClickListener != null ){
                    int pos = holder.getLayoutPosition() ;
                    return onItemLongClickListener.onItemLongClick( v , holder , pos  );
                }
                return false;
            }
        });

        onBindViewHolders( holder , position );
    }

    public abstract void onBindViewHolders( RecyclerView.ViewHolder holder, int position ) ;

    public abstract Object getAdapterData() ;

    public abstract Object getItem( int positon );

}
