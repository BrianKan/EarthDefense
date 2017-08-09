package com.example.redfish.jellyjugglerlite.invaders;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.redfish.jellyjugglerlite.R;

/**
 * Created by TallGuy on 8/8/2017.
 */

public class Asteroid extends Invaderstwo {

    public Asteroid(Context context, int x){
        super(context, x);
        this.bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.ufo);
        this.y_speed = 4;
        this.hitBox = new Rect(this.x, this.y, this.x + bitmap.getWidth(), this.y + bitmap.getHeight());
    }
}
