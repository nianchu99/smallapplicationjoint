package space.nianchu.smallapplicationjoint;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import java.util.Timer;
import java.util.TimerTask;

public class NeonLightsFragment extends Fragment {
    private int currentColor = 0;
    private MaterialButton btnStart;
    private MaterialButton btnPause;
//    添加一个boolean用于指示btnStart是否已经成功启用，从而避免连续两次按压Start Button的无用操作
    private boolean btnStartChecked = false;
    private int[] colors = new int[]{
           R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7
    };
    private final int[] names = new int[]{
            R.id.view01,
            R.id.view02,
            R.id.view03,
            R.id.view04,
            R.id.view05,
            R.id.view06,
            R.id.view07

    };
    private static final String TAG = "NeonLightsFragment";
    TextView[] textViews = new TextView[7];
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.neon_lights_fg, container, false);
        btnStart = (MaterialButton) view.findViewById(R.id.neon_lights_start);
        btnPause = (MaterialButton)view.findViewById(R.id.neon_lights_pause);
        for (int i = 0; i < 7; i++) {
            textViews[i] = view.findViewById(names[i]);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLightColor();
            }
        });
    }

    private void changeLightColor(){
        if (!btnStartChecked){
            Log.d(TAG, "changeLightColor: ");
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentColor >= 7){
                        currentColor = 0;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 7 - currentColor; i++) {
                                textViews[i].setBackgroundResource(colors[i + currentColor]);
                            }

                            for (int i = 7 - currentColor, j = 0; i < 7; i++, j ++) {
                                try {
                                    textViews[i].setBackgroundResource(colors[j]);}
                                catch (ArrayIndexOutOfBoundsException e){
                                    Log.d(TAG, "run: Error and j = : " + j);
                                }
                            }
                        }
                    });
                    currentColor ++;
                }
            }, 0, 100);
            btnPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Button Pause");
                    timer.cancel();
                    btnStartChecked = false;
                }
            });
        }
        btnStartChecked = true;
    }
}
