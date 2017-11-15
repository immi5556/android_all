package com.immi.surfaceview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by Immanuel Raj on 1/30/2016.
 */
public class SpriteBurst {
    int x, y, height, width, frms, sprWidLen, sprHgtLen;
    Bitmap bblbrk;
    Rect src, dst;
    List<SpriteBurst> sprites;
    boolean completed = false;
    public SpriteBurst(List<SpriteBurst> sprtes, MainActivity.OurView ourview, Bitmap img, int xPos, int yPos, int sprwidLen, int sprhgtLen){
        sprites = sprtes;
        bblbrk = img;
        sprWidLen = sprwidLen;
        sprHgtLen = sprhgtLen;
        width = bblbrk.getWidth() / sprwidLen;
        height = bblbrk.getHeight()  / sprhgtLen;
        x  = xPos;
        y = yPos;
    }

    private void Update(){
        frms += 1;
        if (frms >= sprHgtLen) {
            frms = 0;
        }
        if (frms == sprHgtLen - 1){
            completed = true;
        }
    }

    public boolean isCompleted(){
        return completed;
    }

    public void onDraw(Canvas c){
        Update();
        src = new Rect(0, (frms * height)  ,  width, (frms * height) + height);
        dst = new Rect(x, y, x + width, y + height);
        c.drawBitmap(bblbrk, src, dst, null);
    }
}
