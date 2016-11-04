package transform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2016/11/1.
 */
public class MarkView extends View{
    private Paint paint = new Paint();
    private Point point = new Point();
    public MarkView(Context context) {
        super(context);
        init();
    }

    public MarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        paint.setAlpha(100);
        paint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(point.x,point.y,20,paint);
        canvas.drawText(point.x + "," + point.y,point.x,point.y,paint);
    }

    public void setPoint(int x, int y){
        this.point.set(x,y);
        invalidate();
    };
}
