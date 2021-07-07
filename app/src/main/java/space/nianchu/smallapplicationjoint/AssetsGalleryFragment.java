package space.nianchu.smallapplicationjoint;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebHistoryItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

public class AssetsGalleryFragment extends Fragment {
    private LinearLayout linearLayout;
    private ImageView imageView;
    private Button btnNext;
    private Button btnPre;
    String[] images = null;
    AssetManager assets = null;
    InputStream assetFile = null;
    int currentImg = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content, container,false);
        linearLayout = (LinearLayout) view.findViewById(R.id.fg_layout);
//        Create a imageView
        imageView = new ImageView(getContext());
        LinearLayout.LayoutParams imageViewParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        imageViewParam.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageViewParam);
        LinearLayout buttonLayout = new LinearLayout(getContext());
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams buttonLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayout.setLayoutParams(buttonLayoutParam);
//        Create btnPre
        LinearLayout.LayoutParams btnPreViewParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnPreViewParam.weight = 1;
        btnPreViewParam.gravity = Gravity.CENTER;
        btnPre = new MaterialButton(getContext());
        btnPre.setLayoutParams(btnPreViewParam);
        btnPre.setText("Previous");
//        Create btnNext
        LinearLayout.LayoutParams btnNextViewParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnNextViewParam.weight = 1;
        btnNextViewParam.gravity = Gravity.CENTER;
        btnNext = new MaterialButton(getContext());
        btnNext.setLayoutParams(btnNextViewParam);
        btnNext.setText("Next");
//        Add to the linear layout
        buttonLayout.addView(btnPre);
        buttonLayout.addView(btnNext);
        linearLayout.addView(imageView);
        linearLayout.addView(buttonLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            assets = getActivity().getAssets();
            images = assets.list("");
            currentImg = getNextPng();
            assetFile = assets.open(images[currentImg]);
            setNewImage(assetFile);
        }catch (IOException e){
            e.printStackTrace();
        }
//        给btnPre设置点击事件
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImg -= 1;
                //                处理越界
                currentImg = escapeIndexError();
                currentImg = getPrePng();
                assetFile = openAssetFile();
                BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
                recycleBitmapDrawable(bitmapDrawable);
                setNewImage(assetFile);
            }
        });
//        给btnNext设置点击事件
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImg += 1;
                //                处理越界
                currentImg = escapeIndexError();
                currentImg = getNextPng();
                assetFile = openAssetFile();
                BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
                recycleBitmapDrawable(bitmapDrawable);
                setNewImage(assetFile);
            }
        });
    }
    private int escapeIndexError(){
        if (currentImg > images.length - 1){
            currentImg = 0;
        }
        if (currentImg < 0){
            currentImg = images.length - 1;
        }
        return currentImg;
    }
    /*
    从当前位置寻找下一张图片
     */
    private int getNextPng(){
        while (!images[currentImg].endsWith(".png") && !images[currentImg].endsWith(".jpg") && !images[currentImg].endsWith(".gif") && !images[currentImg].endsWith(".webp")){
            currentImg ++;
            currentImg = escapeIndexError();
        }
        return currentImg;
    }
    /*
  从当前位置寻找上一张图片
   */
    private int getPrePng(){
        while (!images[currentImg].endsWith(".png") && !images[currentImg].endsWith(".jpg") && !images[currentImg].endsWith(".gif") && !images[currentImg].endsWith(".webp")){
            currentImg --;
            currentImg = escapeIndexError();
        }
        return currentImg;
    }
    private InputStream openAssetFile(){
        try {
            assetFile = assets.open(images[currentImg]);
            return assetFile;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private void recycleBitmapDrawable(BitmapDrawable bitmapDrawable){
        if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()){
            bitmapDrawable.getBitmap().recycle();
        }
    }

    private void setNewImage(InputStream inputStream){
        imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
    }
}
