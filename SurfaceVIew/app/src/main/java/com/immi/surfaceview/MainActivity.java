package com.immi.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    OurView ov;
    Bitmap ball;
    Bitmap bird;
    Bitmap heli3;
    Bitmap bblbrk;
    Bitmap gpos;
    //float x, y;
    int x, y;
    int surWidth = 0, surHeight = 0;
    Bitmap scaled = null;
    boolean sprinit = false;
    boolean ballinbursting = false;
    Random r = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ov = new OurView(this);
        ov.setOnTouchListener(this);
        ball = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bbl2), 50, 50);
        //bird = BitmapFactory.decodeResource(getResources(), R.drawable.ball_2);
        bird = BitmapFactory.decodeResource(getResources(), R.drawable.heli2);
        heli3 = BitmapFactory.decodeResource(getResources(), R.drawable.heli3);
        bblbrk = BitmapFactory.decodeResource(getResources(), R.drawable.bbbrk);
        gpos = BitmapFactory.decodeResource(getResources(), R.drawable.gpost2);
        x = 0; y = 0;
        setContentView(ov);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ov.pause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ov.resume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException ex){
//            ex.printStackTrace();
//        }
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                x = event.getX();
//                y = event.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                x = event.getX();
//                y = event.getY();
//                if (x > surWidth){
//                    x = surHeight;
//                }
//                if (y > surWidth){
//                    y = surHeight;
//                }
//                break;
//        }
//        return true;
        return false;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if ((x - (int) event.values[0]) >= 0 && (x - (int) event.values[0]) < (surWidth - 50)){
                x -= (int) event.values[0];
            }
            if ((y + (int) event.values[1]) >= 0 && (y + (int) event.values[1]) < (surHeight - 50)) {
                y += (int) event.values[1];
            }
            //Log.d("Log App", "x: " + x + ", Y Val : " + y);
        }
        //Log.d("FFFFFF", "dddddddd - " + x + "     Y " + y);
    }

    public class OurView extends SurfaceView implements Runnable {
        Thread t;
        boolean isItOk;
        SurfaceHolder holder;
        List<Sprite> sprite = new ArrayList<Sprite>();
        List<SpriteBurst> sprBurst = new ArrayList<SpriteBurst>();
        public OurView(Context context){
            super(context);
            holder = getHolder();

        }

        @Override
        public void draw(Canvas c) {
            super.draw(c);
            if (surHeight == 0) {
                Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.bk3);
                surWidth = getWidth(); //Math.round(background.getWidth()/scale);
                surHeight = getHeight(); //Math.round(background.getHeight()/scale);
                scaled = Bitmap.createScaledBitmap(background, surWidth, surHeight, true);
                Sprite sprite1 = createSprite(bird, 0, 80, 4, 2);
                Sprite sprite2 = createSprite(heli3, surWidth, 240, 10, 2);
                //Sprite sprite3 = new Sprite(sprite, OurView.this, bird);
                sprite.add(sprite1);
                sprite.add(sprite2);
                //sprite.add(sprite3);
                y = surHeight - ball.getWidth();
            }
            c.drawBitmap(scaled, 0, 0, null);
            //c.drawBitmap(gpos, (surWidth / 2 - gpos.getWidth() /2), 0, null);
            //c.drawARGB(255, 150,150,150);
            for (int i = sprite.size() - 1; i >= 0; i--) {
                Sprite spr = sprite.get(i);
                if (QollisionUtil.isCollisionDetected(spr, ball, x, y)) {
                    ballinbursting = true;
                    sprBurst.add(new SpriteBurst(sprBurst, OurView.this, bblbrk, x, y, 1, 9));
                    Log.d("Sprite :", "Sprite Removed......");
                }
            }

            for (int i = sprBurst.size() - 1; i >= 0; i--) {
                SpriteBurst spr = sprBurst.get(i);
                if (spr.isCompleted()){
                    sprBurst.remove(spr);
                    ballinbursting = false;
                    x = 0;
                    y = surHeight - ball.getWidth();
                }
            }

            for(Sprite spr : sprite)
                spr.onDraw(c);
            for(SpriteBurst spr : sprBurst)
                spr.onDraw(c);
            if (!ballinbursting) {
                c.drawBitmap(ball, x, y, null);
            }
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(34);
            c.drawText("XXXX", 30, 30, paint);
        }

        public void run(){
            if (!sprinit){
                sprinit = true;
            }
            while (isItOk){
                if(!holder.getSurface().isValid()){
                    continue;
                }
//                try {
//                    //Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Canvas c = holder.lockCanvas();
                draw(c);
                holder.unlockCanvasAndPost(c);
            }
        }

        private Sprite createSprite(Bitmap img, int xPos, int yPos, int sprwidLen, int sprhgtLen){
            return new Sprite(sprite, OurView.this, img, xPos, yPos, sprwidLen, sprhgtLen);
        }

        public void onDraw(Canvas c){

        }

        public void pause(){
            isItOk = false;
            while (true){
                try{
                    t.join();
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                break;
            }
            t = null;
        }
        public  void resume(){
            isItOk = true;
            t = new Thread(this);
            t.start();
        }
    }
}