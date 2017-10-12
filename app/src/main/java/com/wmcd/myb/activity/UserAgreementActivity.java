package com.wmcd.myb.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.base.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 邓志宏 on 2017/2/20.
 */
public class UserAgreementActivity extends BaseActivity {
    /**
     * The News item content text view.
     */
    @Bind(R.id.news_item_content_text_view)
    TextView news_item_content_text_view;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
        bindView();
    }

    /**
     * Bind view.
     */
    private void bindView() {
        AssetManager am = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = am.open("Noname1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = null;
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        news_item_content_text_view.setText(json);
    }
}
