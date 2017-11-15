package com.immi.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * Created by Immanuel Raj on 1/22/2016.
 */
public class Sprite {
    int x,y;
    int xSpeed, ySpeed;
    int height, width;
    Bitmap bird;
    boolean passed = false;
    Rect src, dst;
    MainActivity.OurView ov;
    int frms, direct = 0, sprWidLen, sprHgtLen;
    List<Sprite> sprites;
    public Sprite(List<Sprite> sprite, MainActivity.OurView ourview, Bitmap img, int xPos, int yPos, int sprwidLen, int sprhgtLen){
        bird = img;
        ov = ourview;
        frms = sprwidLen;
        sprWidLen = sprwidLen;
        sprHgtLen = sprhgtLen;
        width = bird.getWidth() / sprwidLen;
        height = bird.getHeight()  / sprhgtLen;
        x  = xPos;
        y = yPos;
        xSpeed = 1;
        ySpeed = 0;
        sprites = sprite;
        Log.d("Sprite :", "Sprite Adedd....");
    }
    private void Update(){
        frms += 1;
        //if (x == 0){
        //    x = r.nextInt(ov.getWidth() - 20) + 20;
        //}

        if (frms >= sprWidLen) {
            frms = 0;
        }
        //right -> left
        if (x > ov.getWidth() - width - xSpeed){
            xSpeed = -5;
            direct = 1;
            //ySpeed = 5;
        }
        //if (y > ov.getHeight() -  height - ySpeed){
//            xSpeed = -5;
//            ySpeed = 0;
            //sprites.remove(this);
            //passed = true;
        //}
        //left -> right
        if(x + xSpeed < 0){
            //x = 0;
            xSpeed = 5;
            direct = 0;
            //ySpeed = -5;
        }
//        if (y + ySpeed < 0){
//            y = 0;
//            xSpeed = 5;
//            ySpeed = 0;
//        }
        x += xSpeed;
        //y += ySpeed;
    }

    public Rect getBounds() {
        return dst == null ? new Rect(x, x + width, y, y + height) : dst;
    }

    public Bitmap getBitmap() {
        return bird;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPassed() {
        return passed;
    }
    public void onDraw(Canvas c){
        Update();
        src = new Rect(frms * width, (direct * height)  , (frms * width) + width, (direct * height) + height);
        dst = new Rect(x, y, x + width, y + height);
        c.drawBitmap(bird, src, dst, null);
    }
}
