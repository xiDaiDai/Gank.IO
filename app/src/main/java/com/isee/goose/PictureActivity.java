package com.isee.goose;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import in.srain.cube.util.CLog;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wuyun on 2015/10/22.
 */
public class PictureActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    private View loadingMask;
    private ImageView mImageView;
    private PhotoViewAttacher mPhotoViewAttacher;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        initView();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
//设置toolbar后调用setDisplayHomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setTitleTextColor(0xffffffff);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});
        toolbar.setTitle("Picture");
        mImageView = (ImageView) findViewById(R.id.picture);
        mPhotoViewAttacher = new PhotoViewAttacher(mImageView);
        Glide.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE_URL)).placeholder(R.drawable.header_view).into(mImageView);
      //  mPhotoViewAttacher.setScaleType(ImageView.ScaleType.FIT_XY);
        CLog.e("url",getIntent().getStringExtra(EXTRA_IMAGE_URL));


    }


}
