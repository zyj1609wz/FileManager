package com.zyj.filemanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyj.filemanager.R;

/**
 * Created by ${zhaoyanjun} on 2017/1/12.
 */

public class FileHolder extends RecyclerView.ViewHolder {

    ImageView fileIcon ;
    TextView fileName ;
    TextView fileChildCount ;
    TextView fileSize ;
    ImageView dir_enter_image ;

    public FileHolder(View view) {
        super(view);
        fileIcon = (ImageView) view.findViewById(R.id.fileIcon );
        fileName = (TextView) view.findViewById(R.id.fileName );
        fileChildCount = (TextView) view.findViewById(R.id.fileChildCount );
        fileSize = (TextView) view.findViewById(R.id.fileSize );
        dir_enter_image = (ImageView) view.findViewById(R.id.dir_enter_image );
    }


}
