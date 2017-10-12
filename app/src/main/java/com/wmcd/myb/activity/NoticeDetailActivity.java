package com.wmcd.myb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.base.BaseActivity;
import com.wmcd.myb.model.SystemMsgBean;

/**
 * Created by Administrator on 2017/4/12 0012. 消息详情
 */
public class NoticeDetailActivity extends BaseActivity {
    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        final SystemMsgBean.ListBean system = (SystemMsgBean.ListBean) getIntent().getSerializableExtra("system");
        ((TextView) findViewById(R.id.title_tv)).setText(system.getSubject());
        ((TextView) findViewById(R.id.content_tv)).setText(system.getBody());
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (system.getRead() == 0) {
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
    }
}
