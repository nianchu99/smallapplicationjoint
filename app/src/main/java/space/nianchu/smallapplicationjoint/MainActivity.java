package space.nianchu.smallapplicationjoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FrameLayout frameLayout;
    private TextView hello_text;
    private DrawerLayout mDrawerLayout;
    private ImageView navHeaderImg;
    private Context mContext;
    private NavigationView navigationView;
    private FragmentManager fManager;
    private AssetsGalleryFragment assetsGalleryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        mContext = MainActivity.this;

        bindViews();
        requestBingPic();
        setNavigationView();
    }

    public void bindViews(){
        Log.d(TAG, "bindViews: first");
        navHeaderImg = (ImageView)findViewById(R.id.nav_header_img);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        hello_text = (TextView)findViewById(R.id.hello_text);
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
                    frameLayout.removeAllViews();
                    FragmentTransaction fragmentTransaction = fManager.beginTransaction();
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
                    }
                    fragmentTransaction.commit();
                    return true;
                }
            });
        }
}
