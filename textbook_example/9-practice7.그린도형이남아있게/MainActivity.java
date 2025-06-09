package com.cookandroid.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.health.connect.datatypes.BloodGlucoseRecord;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static List<myShape> myShapes = new ArrayList<>();
    final static int LINE=1, CIRCLE=2, RECT=3;
    static int shapeType= LINE;
    static int color=Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new myGraphicView(this));

        initViews();
        setupListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"선 그리기");
        menu.add(0,2,0,"원 그리기");
        menu.add(0,3,0,"사각형 그리기");
        SubMenu subMenu = menu.addSubMenu("색상 변경 >>");
        subMenu.add(0,4,0,"RED");
        subMenu.add(0,5,0,"GREEN");
        subMenu.add(0,6,0,"BLUE");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                shapeType = LINE;
                return true;
            case 2:
                shapeType = CIRCLE;
                return true;
            case 3:
                shapeType = RECT;
                return true;
            case 4:
                color = Color.RED;
                return true;
            case 5:
                color = Color.GREEN;
                return true;
            case 6:
                color = Color.BLUE;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {

    }
    private void setupListener(){
    }

    private static class myGraphicView extends View {
        private int startX, startY, stopX, stopY;

        public myGraphicView(Context context) {
            super(context);
        }
        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            for(myShape shape : myShapes) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStrokeWidth(5);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(shape.color);

                switch (shape.shapeType) {
                    case LINE:
                        canvas.drawLine(shape.startX, shape.startY, shape.stopX, shape.stopY, paint);
                        break;
                    case CIRCLE:
                        int radius = (int) Math.sqrt(Math.pow(shape.stopX - shape.startX, 2) + Math.pow(shape.stopY - shape.startY, 2));
                        canvas.drawCircle(shape.startX, shape.startY, radius, paint);
                        break;
                    case RECT:
                        canvas.drawRect(shape.startX, shape.startY, shape.stopX, shape.stopY, paint);
                        break;
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();

                    myShapes.add(new myShape(shapeType, startX, startY, stopX, stopY, color));
                    this.invalidate();
                    break;
            }
            return true;
        }
    }

    private static class myShape{
        int shapeType;
        int startX, startY, stopX, stopY;
        int color;

        private myShape(int shapeType, int startX, int startY, int stopX, int stopY, int color){
            this.shapeType = shapeType;
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
            this.color = color;
        }
    }
}