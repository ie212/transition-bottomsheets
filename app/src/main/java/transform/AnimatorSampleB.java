package transform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.drawable.demo.transitionbottomsheets.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/10/28.
 */
public class AnimatorSampleB extends AppCompatActivity{
    @BindView(R.id.image2)
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_anim_b);
        ButterKnife.bind(this);
        //开启过度动画，获取在xml中使用 transitionName属性
        ViewCompat.setTransitionName(imageView, "icon");
    }
}
