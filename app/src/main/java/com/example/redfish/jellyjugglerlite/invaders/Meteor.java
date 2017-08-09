package com.example.redfish.jellyjugglerlite.invaders;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.redfish.jellyjugglerlite.R;

/**
 * Created by TallGuy on 8/8/2017.
 */

public class Meteor extends Invaderstwo {

    public Meteor(Context context, int x) {

        super(context, x);
        this.type = "meteor";
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor);
        this.y_speed = 20;
        this.hitBox = new Rect(this.x, this.y, this.x + bitmap.getWidth(), this.y + bitmap.getHeight());
    }
}
