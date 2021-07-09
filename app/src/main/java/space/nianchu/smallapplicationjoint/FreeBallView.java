package space.nianchu.smallapplicationjoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FreeBallView extends View {
    private static final String TAG = "FreeBallView";
    public float currentX = 40;
    public float currentY = 50;

    public FreeBallView(Context context){
        super(context);
    }
    public FreeBallView(Context context, AttributeSet set) {
        super(context, set);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 15, paint);
    }

    @Override
    public boolean performClick() {
        Log.d(TAG, "performClick: ");
        return super.performClick();
    }
}
