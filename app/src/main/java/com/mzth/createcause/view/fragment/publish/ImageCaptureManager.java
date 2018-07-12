package com.mzth.createcause.view.fragment.publish;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.mzth.createcause.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @创建者 wxcican(wxcican @ qq.com)
 * @创建时间 2018/7/12 10:39
 * @描述 图片获取管理器
 */
public class ImageCaptureManager {
    private final static String CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath";
    public static final  int    REQUEST_TAKE_PHOTO      = 1;

    private String  mCurrentPhotoPath;
    private Context mContext;

    public ImageCaptureManager(Context mContext) {
        this.mContext = mContext;
    }

    private File createImageFile() throws IOException {

        // Create an image file name
        //        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ////        String imageFileName = "JPEG_" + timeStamp + "_";
        ////        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        ////        if (!storageDir.exists()) {
        ////            if (!storageDir.mkdir()) {
        ////                throw new IOException();
        ////            }
        ////        }
        ////        File image = new File(storageDir, imageFileName + ".jpg");

        String filePath = Environment.getExternalStorageDirectory() + "/images/" + System.currentTimeMillis() + ".jpg";
        File image = new File(filePath);
        if (!image.getParentFile().exists()) {
            image.getParentFile().mkdir();
        }
        //        Uri contentUri = FileProvider.getUriForFile(mContext,
        //                BuildConfig.APPLICATION_ID + ".fileProvider", image);
        //        Log.e("aaa", "图片路径为：" + contentUri.toString());


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public Intent dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            //如果是7.0+的系统
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //            if(mContext.checkSelfPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
                //                return;
                //            }
                File photoFile = createImageFile();
                Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                }
            }
        }

        return takePictureIntent;
    }


    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }


    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && mCurrentPhotoPath != null) {
            savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY, mCurrentPhotoPath);
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
            mCurrentPhotoPath = savedInstanceState.getString(CAPTURED_PHOTO_PATH_KEY);
        }
    }
}
