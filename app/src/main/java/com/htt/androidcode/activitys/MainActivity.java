package com.htt.androidcode.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.htt.androidcode.R;
import com.htt.androidcode.views.widgets.FloatingActionButton;
import com.htt.androidcode.views.widgets.TitleBar;
import com.htt.androidcode.views.widgets.popupmenu.PopupMenuLayout;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private PopupMenuLayout popupMenuLayout;
    private TitleBar titleBar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleBar=(TitleBar)this.findViewById(R.id.title_bar);
        titleBar.setRightMenuIcon(R.mipmap.login_tencent,null);
        popupMenuLayout=(PopupMenuLayout)this.findViewById(R.id.popupmenu);
        popupMenuLayout.setTargetView(titleBar.getRightMenuImageView());

    }
}
