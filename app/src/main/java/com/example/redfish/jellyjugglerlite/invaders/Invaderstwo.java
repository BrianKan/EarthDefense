package com.example.redfish.jellyjugglerlite.invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by TallGuy on 8/8/2017.
 */

public abstract class Invaderstwo {

    protected String type;
    protected Bitmap bitmap;
    protected int x;
    protected int y;
    protected int x_speed = 0;
    protected int y_speed = 0;
    private Random rng;
    protected Rect hitBox;

    public Invaderstwo(Context context, int x) {
        rng = new Random();
        this.y = (rng.nextInt(2) + 1) * -200;
        this.x = x;
    }

    public void move() {
        this.y += y_speed;
        this.x += x_speed;
        hitBox.set(x, y, x + bitmap.getWidth(), y + getBitmap().getHeight());
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY_speed(int y_speed) {
        this.y_speed = y_speed;
    }

    public int getY_speed() {
        return y_speed;
    }

    public String getType() {
        return type;
    }
}
