package com.zyj.filemanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.zyj.filemanager.bean.FileType;
import java.io.File;

/**
 * Created by ${zhaoyanjun} on 2017/1/11.
 */

public class FileUtil {

    /**
     * 获取文件类型
     * @param file
     * @return
     */
    public static FileType getFileType(File file ){
        if (file.isDirectory()) {
            return FileType.directory ;
        }
        String fileName = file.getName().toLowerCase() ;

        if ( fileName.contains(".mp3")) {
            return FileType.music ;
        }

        if ( fileName.contains(".mp4") || fileName.contains( ".avi")
                || fileName.contains( ".3gp") || fileName.contains( ".mov")
                || fileName.contains( ".rmvb") || fileName.contains( ".mkv")
                || fileName.contains( ".flv") || fileName.contains( ".rm")) {
            return FileType.video ;
        }

        if ( fileName.contains(".txt") || fileName.contains(".log") || fileName.contains(".xml")) {
            return FileType.txt ;
        }

        if ( fileName.contains(".zip") || fileName.contains( ".rar")) {
            return FileType.zip ;
        }

        if ( fileName.contains(".png") || fileName.contains( ".gif")
                || fileName.contains( ".jpeg") || fileName.contains( ".jpg")   ) {
            return FileType.image ;
        }

        if ( fileName.contains(".apk") ) {
            return FileType.apk ;
        }

        return FileType.other ;
    }

    /**
     * 获取文件的子文件个数
     * @param file
     * @return
     */
    public static int getFileChildCount(File file) {
        int count = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isHidden()) continue;
                count ++ ;
            }
        }
        return count;
    }

    /**
     * 文件大小转换
     * @param size
     * @return
     */
    public static String sizeToChange( long size ){
        java.text.DecimalFormat df   =new   java.text.DecimalFormat("#.00");  //字符格式化，为保留小数做准备

        double G = size * 1.0 / 1024 / 1024 /1024 ;
        if ( G >= 1 ){
            return df.format( G ) + " GB";
        }

        double M = size * 1.0 / 1024 / 1024  ;
        if ( M >= 1 ){
            return df.format( M ) + " MB";
        }

        double K = size  * 1.0 / 1024   ;
        if ( K >= 1 ){
            return df.format( K ) + " KB";
        }

        return size + " B" ;
    }

    /**
     * 安装apk
     * @param context
     * @param file
     */
    public static void openAppIntent(Context context , File file ){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile( file ), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 打开图片资源
     * @param context
     * @param file
     */
    public static void openImageIntent( Context context , File file ) {
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(path, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 打开文本资源
     * @param context
     * @param file
     */
    public static void openTextIntent( Context context , File file ) {
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(path, "text/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 打开音频资源
     * @param context
     * @param file
     */
    public static void openMusicIntent( Context context , File file ){
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(path, "audio/*");
        context.startActivity(intent);
    }

    /**
     * 打开视频资源
     * @param context
     * @param file
     */
    public static void openVideoIntent( Context context , File file ){
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(path, "video/*");
        context.startActivity(intent);
    }

    /**
     * 打开所有能打开应用资源
     * @param context
     * @param file
     */
    public static void openApplicationIntent( Context context , File file ){
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(path, "application/*");
        context.startActivity(intent);
    }

    /**
     * 发送文件给第三方app
     * @param context
     * @param file
     */
    public static void sendFile( Context context , File file ){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM,
                Uri.fromFile(file));
        share.setType("*/*");//此处可发送多种文件
        context.startActivity(Intent.createChooser(share, "Share"));
    }
}
