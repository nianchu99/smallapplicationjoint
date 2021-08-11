package space.nianchu.smallapplicationjoint.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityRequestPreparer;

import androidx.annotation.Nullable;

import space.nianchu.smallapplicationjoint.R;

public class SimpleImageView extends View {
    private Paint mBitmapPaint;
    private Drawable mDrawable;
    private int  mWidth;
    private int mHeight;

    public SimpleImageView(Context context) {
        super(context);
    }

    public SimpleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        初始化
        initAttrs(attrs);
        mBitmapPaint = new Paint();
//        抗锯齿
        mBitmapPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable == null){
            return;
        }
        BitmapDrawable db = (BitmapDrawable)mDrawable;
        canvas.drawBitmap(db.getBitmap(), getLeft(), getTop(), mBitmapPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }
    private void initAttrs(AttributeSet attrs){
        if (attrs != null){
            TypedArray typedArray = null;
            try {
             typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleImageView);
             mDrawable = typedArray.getDrawable(R.styleable.SimpleImageView_src);
             measureDrawable();
            }
            finally {
                if (typedArray != null){
                    typedArray.recycle();
                }
            }
        }
    }
    private void measureDrawable(){
        if (mDrawable == null){
            throw new RuntimeException("drawable 不能为空");
        }
        mWidth = mDrawable.getIntrinsicWidth();
        mHeight = mDrawable.getIntrinsicHeight();
    }
}
