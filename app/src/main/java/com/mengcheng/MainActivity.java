package com.mengcheng;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.core.base.BasePresenterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BasePresenterActivity {

    @BindView(R.id.title_template)
    TextView titleTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        titleTemplate.setOnClickListener(o -> {
            Log.d("TAG", "0_________________________-");
        });
    }
}
