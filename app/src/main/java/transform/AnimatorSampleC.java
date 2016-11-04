package transform;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drawable.demo.transitionbottomsheets.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/11/1.
 */
public class AnimatorSampleC extends AppCompatActivity {
    private static final String TAG = "BottomSheetsDemo";
    @BindView(R.id.bottom_sheets)
    MySheetsView bottom_sheets;
    @BindView(R.id.overlay_view)
    View overlay_view;
    @BindView(R.id.content_wrapper)
    RelativeLayout content_wrapper;
    @BindView(R.id.icon_trans)
    ImageView icon_trans;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.button1)
    Button button1;
    private BottomSheetBehavior bottomSheetBehavior;
    private static final long SHORT_TIME = 200;
    private static final long LONG_TIME = 500;
    private float statusbarHeight,sheetsHeight,screenHeight,screenWidth = 0;
    private int padding = 0;

    @OnClick(R.id.button1)
    public void onBottonClick(View v) {
        startActivity(new Intent(this, AnimatorSampleC.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_anim_c);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusbarHeight = ScreenUtils.getStatusHeight(this);
        }
        screenWidth = ScreenUtils.getScreenWidth(this);
        screenHeight = ScreenUtils.getScreenHeight(this);
        sheetsHeight = screenHeight / 6 * 5;
        padding = ScreenUtils.dip2px(AnimatorSampleC.this,16);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatorSampleC.this, AnimatorSampleC.class));
            }
        });
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheets);
        //设置初始高度
        //app:behavior_peekHeight="400dp"
        bottomSheetBehavior.setPeekHeight((int) sheetsHeight);
        //设置是否可关闭
        //app:behavior_hideable="false"
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.e(TAG, "state = " + newState);
                switch (newState) {
                    case 5:
                        finish();
                        overridePendingTransition(-1,-1);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.e(TAG, "slideOffset = " + slideOffset);
                overlay_view.setAlpha(1 + slideOffset);
            }
        });

        //獲取到传输值
        ElementValues elementValues = (ElementValues) getIntent().getSerializableExtra("element_value");
        final float pivotY = elementValues.getBgPivotY();
        final float height = elementValues.getBgHeight();
        final float heightPercent = height / screenHeight;
        final float icon_pivotX = elementValues.getIconPivotX();
        final float icon_pivotY = elementValues.getIconPivotY() + statusbarHeight;
        final float icon_width = elementValues.getIconWidth();

        //背景塊漸變
        final AnimatorSet animatorSet = new AnimatorSet();
        //获取到背景位置及大小
        final ObjectAnimator animatorSheetGet = getFloatAnimator(LONG_TIME,bottom_sheets, "scaleY", heightPercent, heightPercent);
        final ObjectAnimator animatorSheetPivotY = getFloatAnimator(LONG_TIME,bottom_sheets, "pivotY", pivotY - screenHeight - statusbarHeight + sheetsHeight, pivotY - screenHeight - statusbarHeight + sheetsHeight);
        //背景需要执行的动画
        final ObjectAnimator animatorSheetAlpha = getFloatAnimator(SHORT_TIME,bottom_sheets, "alpha", 0, 1);
        final ObjectAnimator animatorSheetScale = getFloatAnimator(SHORT_TIME,bottom_sheets, "scaleY", heightPercent, 1);

        //获取到图标位置及大小
        final ObjectAnimator animatorIconGetX = getFloatAnimator(LONG_TIME,icon_trans, "x", icon_pivotX, icon_pivotX);
        final ObjectAnimator animatorIconGetY = getFloatAnimator(LONG_TIME,icon_trans, "y", icon_pivotY, icon_pivotY);
        //图标需要执行的动画
        final float scalePercent = ScreenUtils.dip2px(this,80) / icon_width;
        final float transWidth = (ScreenUtils.dip2px(this,80) - icon_width) / 2;
        final ObjectAnimator animatorIconAlpha = getFloatAnimator(SHORT_TIME,icon_trans, "alpha", 0, 1);
        final ObjectAnimator animatorIconTransX = getFloatAnimator(SHORT_TIME,icon_trans, "x", icon_pivotX, (screenWidth - icon_width) / 2);
        final ObjectAnimator animatorIconTransY = getFloatAnimator(SHORT_TIME,icon_trans, "y", icon_pivotY, screenHeight - sheetsHeight + statusbarHeight + transWidth + padding);
        final ObjectAnimator animatorIconScaleX = getFloatAnimator(SHORT_TIME,icon_trans, "scaleX", 1, scalePercent);
        final ObjectAnimator animatorIconScaleY = getFloatAnimator(SHORT_TIME,icon_trans, "scaleY", 1, scalePercent);
        animatorSet.playTogether(animatorSheetPivotY,animatorIconGetX,animatorIconGetY,animatorIconAlpha,animatorSheetAlpha,animatorSheetGet);
        animatorSet.start();
        animatorSet.addListener(new MyAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                startAnimators(animatorSheetScale,animatorIconScaleX,animatorIconScaleY, animatorIconTransX, animatorIconTransY);
            }
        });

        animatorIconScaleY.addListener(new MyAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                icon_trans.setVisibility(View.GONE);
                icon.setVisibility(View.VISIBLE);
            }
        });

}

    private ObjectAnimator getFloatAnimator(long duration, Object object, String proName, float... values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(object, proName, values);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    private class MyAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    private void startAnimators(ObjectAnimator... animator) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(AnimatorSampleC.this);
                textView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                textView.setText(R.string.long_string);
                textView.setTextColor(Color.BLACK);
                textView.setPadding(padding,padding,padding,padding);
                content_wrapper.addView(textView);
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        closeWindow();
    }

    private void closeWindow() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}