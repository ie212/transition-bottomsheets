package transform;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by admin on 2016/11/2.
 */
public class MySheetsView extends NestedScrollView {
    public MySheetsView(Context context) {
        super(context);
    }

    public MySheetsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySheetsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTopMargin(float topMargin){
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.setMargins(params.leftMargin,(int)topMargin,params.rightMargin,params.bottomMargin);
        setLayoutParams(params);
    }

    public void setBottomMargin(float bottomMargin){
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.setMargins(params.leftMargin,params.topMargin,params.rightMargin,(int)bottomMargin);
        setLayoutParams(params);
    }

    public float getTopMargin(){
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        return params.topMargin;
    }

    public float getBottomMargin(){
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        return params.bottomMargin;
    }

}
