package space.nianchu.smallapplicationjoint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import space.nianchu.smallapplicationjoint.Util.DrawUtil;
// FIXME: 还是有小bug
public class DrawGeometricFiguresFragment extends Fragment implements SurfaceHolder.Callback, View.OnClickListener{
    private static final int FIGURE_CIRCLE = 0;
    private static final int FIGURE_SQUARE = 1;
    private static final int FIGURE_RECT = 2;
    private static final int FIGURE_ROUNDED_RECT = 3;
    private static final int FIGURE_OVAL = 4;
    private static final int FIGURE_TRIANGLE = 5;
    private static final int FIGURE_PENTAGON = 6;
    private boolean isFilled = false;
    private boolean isGradient = false;
    private int currentFigure = 0;
    private int recordedFigure = 0;
    private boolean recordedFilled  = false;
    private boolean recordedGradient = false;
    private static final String TAG = "DrawGeometricFiguresFra";
    private SurfaceView surfaceView;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ChipGroup figureCategory;
    private ChipGroup isFilledCategory;
    private ChipGroup isGradientCategory;
    private Chip chipNotFilled;
    private Chip chipFilled;
    private Chip chipNotGradient;
    private Chip chipGradient;
    private MaterialButton btnUndo;
    private MaterialButton btnDo;
    private Paint notFilledPaint = null;
    private Paint filledPaint = null;
    private boolean isFilledChanged= false;
    private boolean isGradientChanged= false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.draw_geometric_figures, container, false);
        surfaceView = (SurfaceView)view.findViewById(R.id.surface_view);
        figureCategory = (ChipGroup)view.findViewById(R.id.figure_category);
        isFilledCategory = (ChipGroup)view.findViewById(R.id.is_filled);
        isGradientCategory = (ChipGroup)view.findViewById(R.id.is_gradient);
        btnUndo = (MaterialButton)view.findViewById(R.id.draw_undo);
        btnDo = (MaterialButton)view.findViewById(R.id.draw_do);
        chipNotFilled = (Chip)view.findViewById(R.id.not_filled);
        chipFilled = (Chip)view.findViewById(R.id.filled);
        chipNotGradient = (Chip)view.findViewById(R.id.not_gradient);
        chipGradient = (Chip)view.findViewById(R.id.gradient);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notFilledPaint = new Paint();
        notFilledPaint.setAntiAlias(true);
        notFilledPaint.setColor(Color.BLUE);
        notFilledPaint.setStyle(Paint.Style.STROKE);
        notFilledPaint.setStrokeWidth(3);
        filledPaint = new Paint();
        filledPaint.setAntiAlias(true);
        filledPaint.setStyle(Paint.Style.FILL);
        filledPaint.setColor(Color.RED);
        filledPaint.setStrokeWidth(3);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        figureCategory.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chip_circle:
                        recordedFigure = currentFigure;
                        currentFigure = 0;
                        break;
                    case R.id.chip_square:
                        recordedFigure = currentFigure;
                        currentFigure = 1;
                        break;
                    case R.id.chip_rect:
                        recordedFigure = currentFigure;
                        currentFigure = 2;
                        break;
                    case R.id.chip_rounded_rect:
                        recordedFigure = currentFigure;
                        currentFigure = 3;
                        break;
                    case R.id.chip_oval:
                        recordedFigure = currentFigure;
                        currentFigure = 4;
                        break;
                    case R.id.chip_triangle:
                        recordedFigure = currentFigure;
                        currentFigure = 5;
                        break;
                    case R.id.chip_pentagon:
                        recordedFigure = currentFigure;
                        currentFigure = 6;
                        break;
                }
            }
        });
        isFilledCategory.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.not_filled:
                        isFilledChanged = true;
                        recordedFilled = isFilled;
                        isFilled = false;
                        break;
                    case R.id.filled:
                        isFilledChanged = true;
                        recordedFilled = isFilled;
                        isFilled = true;
                        break;

                }
            }
        });
        isGradientCategory.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.not_gradient:
                        isGradientChanged = true;
                        recordedGradient = isGradient;
                        isGradient = false;
                        chipNotFilled.setCheckable(true);
                        chipNotFilled.setChecked(true);
                        break;
                    case R.id.gradient:
                        isGradientChanged = true;
                        recordedGradient = isGradient;
                        isGradient = true;
                        chipNotFilled.setChecked(false);
                        chipNotFilled.setCheckable(false);
                        chipFilled.setChecked(true);
                        break;
                }
            }
        });
        btnDo.setOnClickListener(this);
        btnUndo.setOnClickListener(this);

        Log.d(TAG, "onActivityCreated: is boolean: " + String.valueOf(R.id.chip_circle == figureCategory.getCheckedChipId()));

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d(TAG, "surfaceChanged: width: " + width);
        Log.d(TAG, "surfaceChanged: height: " + height);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        DrawUtil.drawCircle(canvas, surfaceView, notFilledPaint);

        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.draw_do:
                if (!isFilledChanged){
                    recordedFilled = isFilled;
                }
                if (!isGradientChanged){
                    recordedGradient = isGradient;
                }
                if (!isFilled){
                    Log.d(TAG, "recordedFilled: " + recordedFilled);
                    Log.d(TAG, "recordedGradient: " + recordedGradient);
                    Log.d(TAG, "recordedFigure: " + recordedFigure);
                    drawFigure(canvas, surfaceView, 0, currentFigure, 0, 0, notFilledPaint);
                }else if (!isGradient){
                    Log.d(TAG, "recordedFilled: " + recordedFilled);
                    Log.d(TAG, "recordedGradient: " + recordedGradient);
                    Log.d(TAG, "recordedFigure: " + recordedFigure);
                    drawFigure(canvas, surfaceView, 0, currentFigure, 0, 0, filledPaint);
                }else {
                    Log.d(TAG, "recordedFilled: " + recordedFilled);
                    Log.d(TAG, "recordedGradient: " + recordedGradient);
                    Log.d(TAG, "recordedFigure: " + recordedFigure);
                    Shader mShader = new LinearGradient(0, 0, 40, 60, new int[]{
                            Color.GREEN, Color.BLUE
                    }, null, Shader.TileMode.REPEAT);
                    filledPaint.setShader(mShader);
                    drawFigure(canvas, surfaceView, 0, currentFigure, 0, 0, filledPaint);
                }
                isFilledChanged = false;
                isGradientChanged = false;
                break;
            case R.id.draw_undo:
                if (!recordedFilled){
                    drawFigure(canvas, surfaceView, 0, recordedFigure, 0, 0, notFilledPaint);
                    Log.d(TAG, "recordedFilled: " + recordedFilled);
                    Log.d(TAG, "recordedGradient: " + recordedGradient);
                    Log.d(TAG, "recordedFigure: " + recordedFigure);

                }else if (!recordedGradient){
                    Log.d(TAG, "recordedFilled: " + recordedFilled);
                    Log.d(TAG, "recordedGradient: " + recordedGradient);
                    Log.d(TAG, "recordedFigure: " + recordedFigure);
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.RED);
                    drawFigure(canvas, surfaceView, 0, recordedFigure, 0, 0, paint);
                }else {
                    Log.d(TAG, "recordedFilled: " + recordedFilled);
                    Log.d(TAG, "recordedGradient: " + recordedGradient);
                    Log.d(TAG, "recordedFigure: " + recordedFigure);
//                    Shader mShader = new LinearGradient(0, 0, 40, 60, new int[]{
//                            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
//                    }, null, Shader.TileMode.REPEAT);
//                    filledPaint.setShader(mShader);
                    drawFigure(canvas, surfaceView, 0, recordedFigure,  0, 0, filledPaint);
                }
                //                在Undo之后改变figure类型以及填充和渐变的状态
                ((Chip)figureCategory.getChildAt(recordedFigure)).setChecked(true);
                if (recordedFilled){
                    chipFilled.setChecked(true);
                    chipNotFilled.setChecked(false);
                }else {
                    chipFilled.setChecked(false);
                    chipNotFilled.setChecked(true);
                }
                if (recordedGradient){
                    chipGradient.setChecked(true);
                    chipNotGradient.setChecked(false);
                }else {
                    chipGradient.setChecked(false);
                    chipNotGradient.setChecked(true);
                }
                break;
        }
    }


    private void drawFigure(Canvas canvas, SurfaceView surfaceView, float slideLength, int figureState, float width, float height, Paint paint){
        surfaceView.getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        switch (figureState){
            case 0:
                    DrawUtil.drawCircle(canvas, surfaceView, paint);
                break;
            case 1:
                    DrawUtil.drawSquare(canvas, surfaceView, slideLength, paint);
                break;
            case 2:
                    DrawUtil.drawRect(canvas, surfaceView, width, height, paint);
                break;
            case 3:
                    DrawUtil.drawRoundRect(canvas, surfaceView, width, height, paint);
                break;
            case 4:
                    DrawUtil.drawOval(canvas, surfaceView, width, height, paint);
                break;
            case 5:
                    DrawUtil.drawTriangle(canvas, surfaceView, slideLength, paint);
                break;
            case 6:
                    DrawUtil.drawPentagon(canvas, surfaceView, paint);
                break;
        }
        surfaceView.getHolder().unlockCanvasAndPost(canvas);
    }

}
