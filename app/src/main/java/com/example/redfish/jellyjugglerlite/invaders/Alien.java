package com.example.redfish.jellyjugglerlite.invaders;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.redfish.jellyjugglerlite.R;

/**
 * Created by TallGuy on 8/8/2017.
 */

public class Alien extends Invaderstwo {

    private int count = 0;
    private int direction = 0;
    private int reverse = 1;
    private int screenX;

    public Alien(Context context, int x, int screenX) {
        super(context, x);
        this.type = "ufo";
        this.screenX = screenX;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.alien);
        this.x_speed = 0;
        this.y_speed = 6;
        this.hitBox = new Rect(this.x, this.y, this.x + bitmap.getWidth(), this.y + bitmap.getHeight());
    }

    @Override
    public void move() {
        this.count += 1;
        if (this.count % 15 == 0 && this.count > 0) {
            if (direction == 0) {
                this.x_speed = 6 * reverse;
                this.y_speed = 0;
                this.direction = 1;
                this.reverse *= -1;
            }
            else {
                this.y_speed = 6;
                this.x_speed = 0;
                this.direction = 0;

            }
        }
        this.y += y_speed;
        this.x += x_speed;
        hitBox = new Rect(this.x, this.y, this.x + bitmap.getWidth(), this.y + bitmap.getHeight());
    }
}
