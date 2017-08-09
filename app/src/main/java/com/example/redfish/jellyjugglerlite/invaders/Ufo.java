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

    private static int reverse = 0;

    public Ufo(Context context, int x) {
        super(context, x);
        this.bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.ufo);
        this.x_speed = 10;
        this.y_speed = 6;
        this.hitBox = new Rect(this.x, this.y, this.x + bitmap.getWidth(), this.y + bitmap.getHeight());
    }

    @Override
    public void move() {
        this.reverse += 1;
        if (this.reverse % 10 == 0) {
            this.x_speed *= -1;
        }
        this.y += y_speed;
        this.x += x_speed;
        hitBox.set(x, y, x + bitmap.getWidth(), y + getBitmap().getHeight());
    }
}
