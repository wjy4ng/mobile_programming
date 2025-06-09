package com.cookandroid.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton zoomInButton, zoomOutButton, rotateButton, brightButton, darkButton, grayButton;
    private myGraphicView graphicView;
    private static float scaleX=1, scaleY=1, angle=0, color=1, satur=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListener();
    }

    private void initViews() {
        LinearLayout pictureLayout = findViewById(R.id.pictureLayout);
        graphicView = new myGraphicView(this);
        pictureLayout.addView(graphicView);

        zoomInButton = findViewById(R.id.zoomInButton);
        zoomOutButton = findViewById(R.id.zoomOutButton);
        rotateButton = findViewById(R.id.rotateButton);
        brightButton = findViewById(R.id.brightButton);
        darkButton = findViewById(R.id.darkButton);
        grayButton = findViewById(R.id.grayButton);
    }

    private void setupListener(){
        zoomInButton.setOnClickListener(v -> {
            scaleX = scaleX + 0.2f;
            scaleY = scaleY + 0.2f;
            graphicView.invalidate();
        });
        zoomOutButton.setOnClickListener(v -> {
            scaleX = scaleX - 0.2f;
            scaleY = scaleY - 0.2f;
            graphicView.invalidate();
        });
        rotateButton.setOnClickListener(v -> {
            angle = angle + 20;
            graphicView.invalidate();
        });
        brightButton.setOnClickListener(v -> {
            color = color + 0.2f;
            graphicView.invalidate();
        });
        darkButton.setOnClickListener(v -> {
            color = color - 0.2f;
            graphicView.invalidate();
        });
        grayButton.setOnClickListener(v -> {
            if(satur == 0) satur=1;
            else satur=0;
            graphicView.invalidate();
        });
    }

    private static class myGraphicView extends View {
        public myGraphicView(Context context) {
            super(context);
        }
        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            float[] array = {
                    color,0,0,0,0,
                    0,color,0,0,0,
                    0,0,color,0,0,
                    0,0,0,1,0
            };
            ColorMatrix cm = new ColorMatrix(array);
            if (satur==0) cm.setSaturation(satur);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.lena256);
            int picX = (this.getWidth() - picture.getWidth())/2;
            int picY = (this.getHeight() - picture.getHeight())/2;
            int cenX = this.getWidth()/2;
            int cenY = this.getHeight()/2;

            canvas.scale(scaleX, scaleY, cenX, cenY);
            canvas.rotate(angle, cenX, cenY);

            canvas.drawBitmap(picture, picX, picY, paint);
            picture.recycle();
        }
    }
}