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
        View view ;
        if ( viewType == 0 ){
            view = mLayoutInflater.inflate(R.layout.list_item_file, parent, false) ;
            return new FileHolder( view );
        }else {
            view = mLayoutInflater.inflate(R.layout.list_item_line , parent, false) ;
            return new LineHolder( view );
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder,
                                 final int position) {

        FileBean fileBean = list.get(position);

        if ( holder instanceof  FileHolder ){
            FileHolder fileHolder = (FileHolder) holder;
            fileHolder.fileName.setText( fileBean.getName() );

            FileType fileType = fileBean.getFileType() ;
            if ( fileType == FileType.directory ){
                fileHolder.fileChildCount.setVisibility( View.VISIBLE );
                fileHolder.fileChildCount.setText( fileBean.getChildCount() + "项");

                fileHolder.fileSize.setVisibility( View.GONE );
                fileHolder.dir_enter_image.setVisibility( View.VISIBLE );

            }else {
                fileHolder.fileChildCount.setVisibility( View.GONE);

                fileHolder.fileSize.setVisibility( View.VISIBLE );
                fileHolder.fileSize.setText( FileUtil.sizeToChange( fileBean.getSize() ));

                fileHolder.dir_enter_image.setVisibility( View.GONE );
            }

            //设置图标
            if ( fileType == FileType.directory ){
                fileHolder.fileIcon.setImageResource( R.mipmap.file_icon_dir);
            }else if ( fileType == FileType.music ){
                fileHolder.fileIcon.setImageResource( R.mipmap.file_icon_music);
            }else if ( fileType == FileType.video ){
                fileHolder.fileIcon.setImageResource( R.mipmap.file_icon_video);
            }else if ( fileType == FileType.txt ){
                fileHolder.fileIcon.setImageResource( R.mipmap.file_icon_txt );
            }else if ( fileType == FileType.zip ){
                fileHolder.fileIcon.setImageResource( R.mipmap.file_icon_zip);
            }else if ( fileType == FileType.image ){
                Glide.with( context).load( new File( fileBean.getPath() )).into(  fileHolder.fileIcon ) ;
            }else if ( fileType == FileType.apk ){
                fileHolder.fileIcon.setImageResource( R.mipmap.file_icon_apk );
            }else {
                fileHolder.fileIcon.setImageResource( R.mipmap.file_icon_other);
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

        }else if ( holder instanceof  LineHolder ){

        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get( position).getHolderType() ;
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
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
