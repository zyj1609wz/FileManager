package com.zyj.filemanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyj.filemanager.adapter.FileHolder;
import com.zyj.filemanager.adapter.MyAdapter;
import com.zyj.filemanager.adapter.RecyclerViewAdapter;
import com.zyj.filemanager.bean.FileBean;
import com.zyj.filemanager.bean.FilePath;
import com.zyj.filemanager.bean.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<FileBean> beanList = new ArrayList<>();
    private TextView filePathState_tv ;
    private List<FilePath> filePathStateList = new ArrayList<>() ;
    private StringBuilder stringBuilder = new StringBuilder("");
    private File rootFile ;
    private LinearLayout empty_rel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filePathState_tv = (TextView) findViewById( R.id.filePathState_tv );
        filePathState_tv.setHorizontallyScrolling( true );
        filePathState_tv.setMovementMethod(new ScrollingMovementMethod());

        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        myAdapter = new MyAdapter(this, beanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(myAdapter);

        empty_rel = (LinearLayout) findViewById( R.id.empty_rel );

        myAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                if ( viewHolder instanceof FileHolder ){
                    FileBean file = beanList.get(position);
                    FileType fileType = file.getFileType() ;
                    if ( fileType == FileType.directory) {
                        getFile(file.getPath());

                        FilePath filePath = new FilePath() ;
                        filePath.setNameState(  file.getName() + " > "  );
                        filePath.setPath( file.getPath() );
                        filePathStateList.add( filePath ) ;

                        filePathState_tv.setText( getFilePathState() );
                    }else if ( fileType == FileType.apk ){
                        //安装 apk
                        FileUtil.installApp( MainActivity.this , new File( file.getPath() ) );
                    }else if ( fileType == FileType.image ){
                        Intent image_intent =  new Intent( MainActivity.this , ImageBrowseActivity.class) ;
                        image_intent.putExtra( ImageBrowseActivity.FILE_PATH_KEY , file.getPath() ) ;
                        startActivity( image_intent );
                    }else if ( fileType == FileType.txt ){
                        FileUtil.openWordFileIntent( MainActivity.this , file.getPath() );
                    }else {
                        FileUtil.openWordFileIntent( MainActivity.this , file.getPath() );
                    }
                }
            }
        });

        myAdapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                if ( viewHolder instanceof  FileHolder ){

                }
                return false;
            }
        });

        FilePath filePath = new FilePath() ;
        filePath.setNameState(  "" );
        filePath.setPath( rootPath );
        filePathStateList.add( filePath ) ;

        getFile(rootPath);

    }

    public void getFile(String path ) {
        rootFile = new File(path + "/" )  ;
        new MyTask(rootFile).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }

    class MyTask extends AsyncTask {
        File file;

        MyTask(File file) {
            this.file = file;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            List<FileBean> fileBeenList = new ArrayList<>();
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isHidden()) continue;

                FileBean fileBean = new FileBean();
                fileBean.setName(f.getName());
                fileBean.setPath(f.getAbsolutePath());
                fileBean.setFileType( FileUtil.getFileType( f ));
                fileBean.setChildCount( FileUtil.getFileChildCount( f ));
                fileBean.setSize( f.length() );
                fileBean.setHolderType( 0 );

                fileBeenList.add(fileBean);

                FileBean lineBean = new FileBean();
                lineBean.setHolderType( 1 );
                fileBeenList.add( lineBean );

            }
            beanList = fileBeenList;
            return fileBeenList;
        }

        @Override
        protected void onPostExecute(Object o) {
            if ( beanList.size() > 0  ){
                empty_rel.setVisibility( View.GONE );
            }else {
                empty_rel.setVisibility( View.VISIBLE );
            }
            myAdapter.refresh(beanList);
        }
    }

    private String getFilePathState(){
        stringBuilder.delete( 0 , stringBuilder.length() ) ;
        for ( FilePath fp : filePathStateList ){
            stringBuilder.append( fp.getNameState() ) ;
        }
        return stringBuilder.toString() ;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if ( filePathStateList.size() == 1 ){
                finish();
            }else {
                filePathStateList.remove( filePathStateList.size() - 1 ) ;
                filePathState_tv.setText( getFilePathState() );
                getFile( filePathStateList.get( filePathStateList.size() - 1 ).getPath());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
