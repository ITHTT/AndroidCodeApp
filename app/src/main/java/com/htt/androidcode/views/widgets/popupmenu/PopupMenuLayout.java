package com.htt.androidcode.views.widgets.popupmenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.htt.androidcode.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HTT on 2016/6/30.
 */
public class PopupMenuLayout extends FrameLayout {
    public final static int POPUP_DOWN=1;
    public final static int POPUP_UP=0;
    private Context context;
    /**弹出方向，向上或向下*/
    private int direction=POPUP_UP;
    /**菜单项*/
    private List<View> menuItems=null;
    /**菜单项的大小*/
    private int menuItemWidth=100;
    /**菜单项的高度*/
    private int menuItemHeight=100;
    /**菜单项间的间距*/
    private int itemPadding;
    /**动画时间*/
    private int duration = 500;
    /**延迟单位时间*/
    private int delay=100;
    /**目标视图*/
    private View targetView;
    /**目标视图相对于该视图的位置*/
    private int targetX;
    private int targetY;

    private boolean isShowing;
    private boolean isShowingAnimationPlaying;
    private boolean isHideAnimationPlaying;


    public PopupMenuLayout(Context context) {
        super(context);
        initViews(context);
    }

    public PopupMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public PopupMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PopupMenuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context){
        this.context=context;

    }

    public void setTargetView(View view){
        this.targetView=view;
        this.targetView.post(new Runnable() {
            @Override
            public void run() {
                calculateTargetXY();
                menuItems=new ArrayList<>(5);
                for(int i=0;i<5;i++){
                    final ImageView imageView = new ImageView(context);
                    imageView.setImageResource(R.mipmap.people_icon);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(menuItemWidth, menuItemWidth);
                    layoutParams.leftMargin=targetX+targetView.getWidth()/2-menuItemWidth/2;
                    addView(imageView,layoutParams);
                    imageView.setVisibility(View.INVISIBLE);
                    menuItems.add(imageView);
                }
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShowingAnimationPlaying&&!isHideAnimationPlaying) {
                    if(!isShowing) {
                        showAnimation();
                    }else{
                        hideAnimation();
                    }
                }
            }
        });
    }

    private void calculateTargetXY(){
        View view=targetView;
        while(view!=null&&view!=this){
           targetY+=view.getTop();
           targetX+=view.getLeft();
           view= (View) view.getParent();
        }
    }

    private void setMenuItemLayoutParams(){
        int size=menuItems.size();
        for(int i=0;i<size;i++){
            View view=menuItems.get(i);
            FrameLayout.LayoutParams layoutParams= (LayoutParams) view.getLayoutParams();
            layoutParams.leftMargin=(targetView.getLeft()+targetView.getRight())/2-menuItemWidth;
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 显示动画
     */
    private void showAnimation(){
        isShowing=true;
        isShowingAnimationPlaying=true;
        int size=menuItems.size();
       for(int i=0;i<size;i++){
           final int position=i;
                    View imageView=menuItems.get(i);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.bringToFront();
                    ObjectAnimator yAnimator = ObjectAnimator.ofFloat(imageView, "y", targetY+targetView.getHeight() / 2-20, targetY+targetView.getHeight() + (size-i-1) * (menuItemHeight + 30));
                    yAnimator.setDuration(duration);
                    yAnimator.setStartDelay(i*delay);
                    yAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    yAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if(position==4){
                                isShowingAnimationPlaying=false;
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    yAnimator.start();

                    float scaleW = 0;
                    float scaleH = 0;
                    imageView.setScaleX(scaleW);
                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX",
                            scaleW,
                            1f).setDuration(duration);
                    scaleXAnimator.setStartDelay(i*delay);
                    scaleXAnimator.start();

                    imageView.setScaleY(scaleH);
                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY",
                            scaleH,
                            1f).setDuration(duration);
                    scaleYAnimator.setStartDelay(i*delay);
                    scaleYAnimator.start();
       }
    }


    private void hideAnimation(){
        isShowing=false;
        isHideAnimationPlaying=true;
        int size=menuItems.size();
        for(int i=0;i<size;i++){
            final int position=i;
            View imageView=menuItems.get(i);
            imageView.bringToFront();
            ObjectAnimator yAnimator = ObjectAnimator.ofFloat(imageView, "y",targetY+targetView.getHeight() + (size-i-1) * (menuItemHeight + 50),targetY+targetView.getHeight() / 2-30);
            yAnimator.setDuration(duration);
            yAnimator.setStartDelay((size-i-1)*delay);
            yAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            yAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if(position==0){

                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(position==4){
                        isHideAnimationPlaying=false;
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            yAnimator.start();

            float scaleW = 1f;
            float scaleH = 1f;
            imageView.setScaleX(scaleW);
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX",
                    scaleW,
                    0f).setDuration(duration);
            scaleXAnimator.setStartDelay((size-i-1)*delay);
            scaleXAnimator.start();

            imageView.setScaleY(scaleH);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY",
                    scaleH,
                    0f).setDuration(duration);
            scaleYAnimator.setStartDelay((size-i-1)*delay);
            scaleYAnimator.start();
        }
    }

    private void setViewLocation(){

    }




}
