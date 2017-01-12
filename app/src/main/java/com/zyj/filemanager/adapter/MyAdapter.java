package com.zyj.filemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyj.filemanager.FileUtil;
import com.zyj.filemanager.R;
import com.zyj.filemanager.bean.FileBean;
import com.zyj.filemanager.bean.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoyanjun} on 2017/1/11.
 */

public class MyAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FileBean> list;
    private LayoutInflater mLayoutInflater ;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public MyAdapter(Context context, List<FileBean> list) {
        this.context = context;
        this.list = list;
        mLayoutInflater = LayoutInflater.from( context ) ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item , parent, false) ;
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder,
                                 final int position) {

        FileBean fileBean = list.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.fileName.setText( fileBean.getName() );

        FileType fileType = fileBean.getFileType() ;
        if ( fileType == FileType.directory ){
            myViewHolder.fileChildCount.setVisibility( View.VISIBLE );
            myViewHolder.fileChildCount.setText( fileBean.getChildCount() + "项");

            myViewHolder.fileSize.setVisibility( View.GONE );
            myViewHolder.dir_enter_image.setVisibility( View.VISIBLE );

        }else {
            myViewHolder.fileChildCount.setVisibility( View.GONE);

            myViewHolder.fileSize.setVisibility( View.VISIBLE );
            myViewHolder.fileSize.setText( FileUtil.sizeToChange( fileBean.getSize() ));

            myViewHolder.dir_enter_image.setVisibility( View.GONE );
        }

        //设置图标
        if ( fileType == FileType.directory ){
            myViewHolder.fileIcon.setImageResource( R.mipmap.file_icon_dir);
        }else if ( fileType == FileType.music ){
            myViewHolder.fileIcon.setImageResource( R.mipmap.file_icon_music);
        }else if ( fileType == FileType.video ){
            myViewHolder.fileIcon.setImageResource( R.mipmap.file_icon_video);
        }else if ( fileType == FileType.txt ){
            myViewHolder.fileIcon.setImageResource( R.mipmap.file_icon_txt );
        }else if ( fileType == FileType.zip ){
            myViewHolder.fileIcon.setImageResource( R.mipmap.file_icon_zip);
        }else if ( fileType == FileType.image ){
            Glide.with( context).load( new File( fileBean.getPath() )).into(  myViewHolder.fileIcon ) ;
        }else if ( fileType == FileType.apk ){
            myViewHolder.fileIcon.setImageResource( R.mipmap.file_icon_apk );
        }else {
            myViewHolder.fileIcon.setImageResource( R.mipmap.file_icon_other);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition() ;
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView fileIcon ;
        TextView fileName ;
        TextView fileChildCount ;
        TextView fileSize ;
        ImageView dir_enter_image ;

        public MyViewHolder(View view) {
            super(view);
            fileIcon = (ImageView) view.findViewById(R.id.fileIcon );
            fileName = (TextView) view.findViewById(R.id.fileName );
            fileChildCount = (TextView) view.findViewById(R.id.fileChildCount );
            fileSize = (TextView) view.findViewById(R.id.fileSize );
            dir_enter_image = (ImageView) view.findViewById(R.id.dir_enter_image );
        }
    }

    /**
     * 添加数据
     * @param content
     * @param position
     */
    public void addItem( FileBean content, int position) {
        list.add(position, content);
        notifyItemInserted(position);
    }

    /**
     * 增加数据
     * @param content
     */
    public void addItem( FileBean content ){
        if ( list == null ) {
            list = new ArrayList<>() ;
        }
        list.add( list.size() , content );
        notifyItemInserted( list.size() );
    }

    public void refresh( List<FileBean> list ){
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     * @param model
     */
    public void removeItem(String model) {
        int position = list.indexOf(model);
        list.remove(position);
        notifyItemRemoved(position);//Attention!
    }

    /**
     * 删除数据
     * @param position
     */
    public void removeItem( int position ){
        list.remove( position ) ;
        notifyItemRemoved( position );
    }
}
