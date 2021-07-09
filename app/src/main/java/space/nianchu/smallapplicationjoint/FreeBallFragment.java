package space.nianchu.smallapplicationjoint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FreeBallFragment extends Fragment {
    private static final String TAG = "FreeBallFragment";
    private FreeBallView freeBallView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.free_ball_frag, container, false);
        freeBallView = (FreeBallView)view.findViewById(R.id.free_ball_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        freeBallView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                freeBallView.currentX = event.getX();
                freeBallView.currentY = event.getY();
//                通知draw组件重绘
                freeBallView.invalidate();
                return true;
            }
        });
    }
}
