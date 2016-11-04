package transform;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.drawable.demo.transitionbottomsheets.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/10/28.
 */
public class AnimatorSampleA extends AppCompatActivity{
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.mark_view)
    MarkView mark_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MAdapter());
    }

    @OnClick(R.id.image)
    public void onClick(View v){
        startActivity(new Intent(AnimatorSampleA.this,AnimatorSampleB.class), ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView, "icon").toBundle());
    }

    private class MAdapter extends RecyclerView.Adapter<ViewHoder>{


        @Override
        public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(AnimatorSampleA.this).inflate(R.layout.item_recycler,parent,false);
            return new ViewHoder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHoder holder, final int position) {
            holder.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnimatorSampleA.this, AnimatorSampleC.class);

                    float screenHeight = ScreenUtils.getScreenHeight(AnimatorSampleA.this);
                    float bg_height = holder.background.getHeight();
                    float bg_pivotY = getPiovtY(holder.background.getY(),bg_height,screenHeight);

                    mark_view.setPoint(0,(int)holder.background.getY());

                    ElementValues elementValues = new ElementValues();
                    elementValues.setBgPivotY(bg_pivotY);
                    elementValues.setBgHeight(bg_height);
                    elementValues.setIconPivotX(holder.icon.getX());
                    elementValues.setIconWidth(holder.icon.getWidth());
                    elementValues.setIconPivotY(holder.background.getY() + holder.icon.getY());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("element_value",elementValues);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(-1,-1);
                }
            });
            holder.title.setText(R.string.richbox);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private class ViewHoder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView title;
        private LinearLayout background;
        public ViewHoder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            title = (TextView) itemView.findViewById(R.id.item_title);
            background = (LinearLayout) itemView.findViewById(R.id.background);
        }
    }

    private float getPiovtY(float Y,float height,float screenHeight){
        //缩放动画中心点位置与当前点击的位置为线性递增关系
        return Y + (height * Y) / (screenHeight - height);
    }
}
