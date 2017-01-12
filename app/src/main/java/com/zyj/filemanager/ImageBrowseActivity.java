package com.zyj.filemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import java.io.File;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageBrowseActivity extends AppCompatActivity {

    public static final String FILE_PATH_KEY = "FILE_PATH_KEY" ;
    private String filePath ;
    private PhotoView photoView ;
    PhotoViewAttacher mAttacher;
    private RelativeLayout back_rel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_browse);

        Intent intent = getIntent() ;
        Bundle bundle = intent.getExtras() ;
        if ( bundle.containsKey( FILE_PATH_KEY )){
            filePath = bundle.getString( FILE_PATH_KEY ) ;
        }

        photoView = (PhotoView) findViewById( R.id.photoView );
        Glide.with( this ).load( new File( filePath )).into( photoView ) ;

        mAttacher = new PhotoViewAttacher( photoView );

        back_rel = (RelativeLayout) findViewById( R.id.back_rel );
        back_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
