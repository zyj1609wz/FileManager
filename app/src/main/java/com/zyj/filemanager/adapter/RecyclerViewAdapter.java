package com.zyj.filemanager.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    public MyAdapter.OnItemClickListener onItemClickListener;
    public MyAdapter.OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener( MyAdapter.OnItemLongClickListener listener) {
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

    abstract void onBindViewHolders( RecyclerView.ViewHolder holder, int position ) ;

    abstract public Object getAdapterData() ;

    abstract public Object getItem( int positon );

}
