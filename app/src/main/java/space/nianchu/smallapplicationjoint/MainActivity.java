package space.nianchu.smallapplicationjoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import space.nianchu.smallapplicationjoint.Util.HttpUtil;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private static final String TAG = "MainActivity";
    private static int openTimes = 1;
    private FrameLayout frameLayout;
    private TextView hello_text;
    private DrawerLayout mDrawerLayout;
    private ImageView navHeaderImg;
    private Context mContext;
    private NavigationView navigationView;
    private FragmentManager fManager;
    private AssetsGalleryFragment assetsGalleryFragment;
    private DrawGeometricFiguresFragment drawGeometricFiguresFragment = null;
    private NeonLightsFragment neonLightsFragment = null;
    private SurfaceView surfaceView;
    SurfaceHolder surfaceHolder = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        mContext = MainActivity.this;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        bindViews();
        requestBingPic();
        setNavigationView();
        testSurfaceView();
    }

    public void bindViews(){
        Log.d(TAG, "bindViews: first");
        navHeaderImg = (ImageView)findViewById(R.id.nav_header_img);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        hello_text = (TextView)findViewById(R.id.hello_text);
        surfaceView = (SurfaceView)findViewById(R.id.surface_view);
    }

    public void requestBingPic(){
        HttpUtil.sendOkHttpRequest(HttpUtil.BING_PIC_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "获取每日一图失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final String responseText = response.body().string();
                    Log.d(TAG, "onResponse: " + responseText);
                    if (!TextUtils.isEmpty(responseText.trim())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showBingPic(responseText);
                        }
                    });}
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        }

        public void showBingPic(String picUrl){
//        没搞懂为什么这里不bindViews的话navHeaderImg就没有成功初始化，onCreate()中明明已经调用过bindViews了
            bindViews();
            Glide.with(mContext).load(picUrl).into(navHeaderImg);
        }
        
        public void setNavigationView(){
            Log.d(TAG, "setNavigationView: ");
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(openTimes == 1){
                        frameLayout.removeAllViews();
                    }
                    openTimes = 0;
                    FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                    hideAllFragment(fragmentTransaction);
                    switch (item.getItemId()){
                        case R.id.assets_gallery:
                            if (assetsGalleryFragment == null){
                                assetsGalleryFragment = new AssetsGalleryFragment();
                                fragmentTransaction.add(R.id.frame_layout, assetsGalleryFragment);
                            }else {
                                fragmentTransaction.show(assetsGalleryFragment);
                            }
                            Log.d(TAG, "onNavigationItemSelected: assets gallery");
                            mDrawerLayout.closeDrawers();
                            break;
                        case R.id.draw_geometric_figures:
                            if (drawGeometricFiguresFragment == null){
                                drawGeometricFiguresFragment = new DrawGeometricFiguresFragment();
                                fragmentTransaction.add(R.id.frame_layout, drawGeometricFiguresFragment);
                            }else {
                                fragmentTransaction.show(drawGeometricFiguresFragment);
                            }
                            Log.d(TAG, "onNavigationItemSelected: draw Geometric Figures Fragment");
                            mDrawerLayout.closeDrawers();
                            break;
                        case R.id.neon_lights:
                            if (neonLightsFragment == null){
                                neonLightsFragment = new NeonLightsFragment();
                                fragmentTransaction.add(R.id.frame_layout, neonLightsFragment);
                            }else {
                                fragmentTransaction.show(neonLightsFragment);
                            }
                            Log.d(TAG, "onNavigationItemSelected: Neon Lights Fragment");
                            mDrawerLayout.closeDrawers();
                            break;
                    }
                    fragmentTransaction.commit();
                    return true;
                }
            });
        }

        public static void Test(Canvas canvas){
            Log.d(TAG, "Test: ");
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            canvas.drawCircle(40, 40, 30, paint);
        }

        public void testSurfaceView(){
            surfaceHolder = surfaceView.getHolder();
            Canvas canvas = surfaceHolder.lockCanvas(new Rect(0, 0, getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight()));
            surfaceHolder.addCallback(this);
//            Canvas Action
            Log.d(TAG, "testSurfaceView: surfaceView?:" + String.valueOf(surfaceView == null));
            Log.d(TAG, "testSurfaceView: surfaceHolder?:" + String.valueOf(surfaceHolder == null));
            Log.d(TAG, "testSurfaceView: Canvas?:" + String.valueOf(canvas == null));

        }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        Test(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(assetsGalleryFragment != null)fragmentTransaction.hide(assetsGalleryFragment);
        if(drawGeometricFiguresFragment != null)fragmentTransaction.hide(drawGeometricFiguresFragment);
        if(neonLightsFragment != null)fragmentTransaction.hide(neonLightsFragment);
//        if(fg4 != null)fragmentTransaction.hide(fg4);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
