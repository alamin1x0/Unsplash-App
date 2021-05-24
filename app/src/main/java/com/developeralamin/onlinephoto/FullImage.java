package com.developeralamin.onlinephoto;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class FullImage extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        ImageView imageView = findViewById(R.id.myZoomageView);

        Button download= findViewById(R.id.download_img);

        url = getIntent().getStringExtra("image");

        PRDownloader.initialize(getApplicationContext());

        Glide.with(this)
                .load(url)
                .into(imageView);

        download.setOnClickListener(v ->{
            checkPermission();
        });
    }

    private void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    downloadImage();
                }else {
                    Toast.makeText(FullImage.this, "Please allow all permission", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }

        }).check();
    }

    private void downloadImage() {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Downloading.....");
        pd.setCancelable(false);
        pd.show();

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

             PRDownloader.download(url, file.getPath(), URLUtil.guessFileName(url,null,null))
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                        long per = progress.currentBytes*100 / progress.totalBytes;

                        pd.setMessage("Downloading : "+per+ " %");

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(FullImage.this, "Downloading Completed", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(FullImage.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                });
    }
}