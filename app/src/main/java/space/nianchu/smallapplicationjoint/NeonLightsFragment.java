package space.nianchu.smallapplicationjoint;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class NeonLightsFragment extends Fragment {
    private int currentColor = 0;
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
        for (int i = 0; i < 7; i++) {
            textViews[i] = view.findViewById(names[i]);
        }
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                currentColor++;
                if (currentColor >= 6){
                    currentColor = 0;
                }
                changeLightColor();
            }
        }, 0, 100);
    }

    public void changeLightColor(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 7 - currentColor; i++) {
                    textViews[i].setBackgroundResource(colors[i + currentColor]);
                }
                for (int i = 7 - currentColor, j = 0; i < 7; i++, j++) {
                    textViews[i].setBackgroundResource(colors[j]);
                }
            }
        });
    }
}
