package com.example.redfish.jellyjugglerlite.invaders;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.redfish.jellyjugglerlite.R;

import java.util.Random;

/**
 * Created by TallGuy on 8/8/2017.
 */

public class Ufo extends Invaderstwo {

    private int reverse = 0;
    private int screenX;

    public Ufo(Context context, int x, int screenX) {
        super(context, x);
        this.type = "ufo";
        this.screenX = screenX;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ufo);
        this.x_speed = 5;
        this.y_speed = 6;
        this.hitBox = new Rect(this.x, this.y, this.x + bitmap.getWidth(), this.y + bitmap.getHeight());
    }

    @Override
    public void move() {
        this.reverse += 1;
        if (this.reverse % 15 == 0 && this.reverse > 0) {
            this.x_speed *= -1;
        }
        this.y += y_speed;
        this.x += x_speed;
        hitBox = new Rect(this.x, this.y, this.x + bitmap.getWidth(), this.y + bitmap.getHeight());
    }
}
