package space.nianchu.smallapplicationjoint.Util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceView;

public class DrawUtil {
    private static final String TAG = "DrawUtil";

    public static void drawCircle(Canvas canvas, SurfaceView surfaceView, Paint paint){
        Log.d(TAG, "test: ");
        canvas.drawCircle(surfaceView.getWidth() / 2, surfaceView.getHeight() / 2, surfaceView.getWidth() / 4, paint);
    }

    public static void drawSquare(Canvas  canvas, SurfaceView surfaceView, float slideLength, Paint paint){
        float centerX = surfaceView.getWidth() / 2;
        float centerY = surfaceView.getHeight() / 2;

        //        get the slide length
        if (slideLength == (float)0){
            slideLength = centerX / 2;
        }

        canvas.drawRect(centerX - slideLength, centerY - slideLength, centerX + slideLength, centerY + slideLength, paint);
//        canvas.drawRect(10, 80, 70, 140, paint);

    }

    public static void drawRect(Canvas canvas, SurfaceView surfaceView, float width, float height, Paint paint){
        float centerX = surfaceView.getWidth() / 2;
        float centerY = surfaceView.getHeight() / 2;
        if (width == (float)(0)){
            width = (float) 3 / (float)4 * centerX;
        }
        if (height == (float)0){
            height = centerX / 2;
        }
        canvas.drawRect(centerX - width, centerY - height, centerX + width, centerY + height, paint);
    }

    public static void drawOval(Canvas canvas, SurfaceView surfaceView, float width, float height, Paint paint){
        float centerX = surfaceView.getWidth() / 2;
        float centerY = surfaceView.getHeight() / 2;
        if (width == (float)(0)){
            width = (float) 3 / (float)4 * centerX;
        }
        if (height == (float)0){
            height = centerX / 2;
        }
        RectF rectF = new RectF(centerX - width, centerY - height, centerX + width, centerY + height);
        canvas.drawOval(rectF, paint);
    }

    public static void drawRoundRect(Canvas canvas, SurfaceView surfaceView, float width, float height, Paint paint){
        float centerX = surfaceView.getWidth() / 2;
        float centerY = surfaceView.getHeight() / 2;
        if (width == (float)(0)){
            width = (float) 3 / (float)4 * centerX;
        }
        if (height == (float)0){
            height = centerX / 2;
        }
        RectF rectF = new RectF(centerX - width, centerY - height, centerX + width, centerY + height);
        canvas.drawRoundRect(rectF, 30, 30, paint);
    }
    public  static void drawTriangle(Canvas canvas, SurfaceView surfaceView, float slideLength, Paint paint){
        float centerX = surfaceView.getWidth() / 2;
        float centerY = surfaceView.getHeight() / 2;

        //        get the slide length
        if (slideLength == (float) 0){
            slideLength = centerX / 2;
        }
        Path path = new Path();
        path.moveTo(centerX, (float)( centerY - Math.sqrt(3) / 6 * slideLength));
        path.lineTo((float) (centerX - slideLength * 0.5), centerY + (float)(Math.sqrt(3) / 3 * slideLength));
        path.lineTo((float) (centerX + slideLength * 0.5), centerY + (float)(Math.sqrt(3) / 3 * slideLength));
        path.close();
        canvas.drawPath(path, paint);
    }
    /*
    五边形比较特殊，不提供定制化
     */
    public static void drawPentagon(Canvas canvas, SurfaceView surfaceView, Paint paint){
        float centerX = surfaceView.getWidth() / 2;
        float centerY  = surfaceView.getHeight() / 2;

        canvas.drawColor(Color.WHITE);
        Path path = new Path();
        path.moveTo(centerX  - centerX / 2, centerY - centerY / 2);
        path.lineTo(centerX  + centerX / 2, centerY - centerY / 2);
        path.lineTo(centerX + centerX * (float)3 / 4, centerY + 50);
        path.lineTo(centerX, centerY  + centerY / 2);
        path.lineTo(centerX - centerX * (float)3 / 4, centerY + 50);
        path.close();
        canvas.drawPath(path, paint);
    }

}
