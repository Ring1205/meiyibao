package com.wmcd.myb.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.util.BitmapUtil;
import com.wmcd.myb.util.OnBooleanListener;
import com.wmcd.myb.util.UiUtils;
import com.wmcd.myb.view.MyPopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class CommentActivity extends BaseActivity {
    @Bind(R.id.iv_comment_addpic)
    ImageView ivCommentAddpic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {


    }

    @OnClick(R.id.iv_comment_addpic)
    public void onClick() {
        showPopupWindow();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3011: //从手机相册选择
                    Uri originalUri = data.getData();
                    Bitmap photo = BitmapUtil.compressBitmap(null, null, this, originalUri);
                    ivCommentAddpic.setImageBitmap(photo);
                    break;
                case 3012:
                    break;
                case 3013:


                    break;

            }


        }
    }


    private void showPopupWindow() {
        final MyPopupWindow popupWindow = new MyPopupWindow(this, "拍照", "从手机相册选择");
        popupWindow.setonclick(new MyPopupWindow.SetOnClickLinener() {
            @Override
            public void OnClick01() {   //拍照
                onPermissionRequests(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new OnBooleanListener() {
                    @Override
                    public void onClick(boolean bln) {
                        if (bln) {
                            // UiUtils.getPictureFormCamera(CommentActivity.this, 3012, file);
                        } else {
                            Toast.makeText(CommentActivity.this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
                popupWindow.dismiss();
            }

            @Override
            public void OnClick02() {   //从手机相册选择
                UiUtils.getPictureFromPhoto(CommentActivity.this, 3011);
                popupWindow.dismiss();
            }

            @Override
            public void OnClick03() {
            }
        });
    }
}
