package com.example.redfish.jellyjugglerlite;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.redfish.jellyjugglerlite.invaders.Alien;
import com.example.redfish.jellyjugglerlite.invaders.Asteroid;
import com.example.redfish.jellyjugglerlite.invaders.Invaderstwo;
import com.example.redfish.jellyjugglerlite.invaders.Meteor;
import com.example.redfish.jellyjugglerlite.invaders.Ufo;

import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Redfish on 8/5/2017.
 */

public class GameView extends SurfaceView implements Runnable {
    SharedPreferences sharedPreferences;
    public final static String PREFS_NAME = "JellyJuggler";
    private Thread gameThread;
    Context context;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Invaderstwo> invadersArrayList;
    private Random rng;
    int screenX;
    int screenY;
    private SoundPool soundPool;
    private int explosion, powerup, nukeSnd;

    private Bitmap health1, health2, health3;
    private Bitmap gameOver;
    public int runOnce;
    boolean playing;

    private HealthPack healthPack;
    private Nuke nuke;
    private boolean isGameOver;
    int score;
    int lives;
    int highScore[] = new int[4];
    private int anim2Start;
    private int anim2LocX, anim2LocY;
    private int animStart;
    private int animLocX, animLocY;
    private int anim3Start;
    private int anim3LocX, anim3LocY;
    private Bitmap poof0, poof1, poof2, poof3, poof4, poof5;
    private Bitmap nuke0, nuke1, nuke2, nuke3, nuke4, nuke5, nuke6, nuke7, nuke8, nuke9, nuke10, nuke11;

    //TODO List
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        this.screenX = screenX;
        this.screenY = screenY;

        animLocX = -500;
        animLocY = -500;
        anim3LocX = -500;
        anim3LocY = -500;
        anim2LocX = -700;
        anim2LocY = -700;
        this.context = context;
        isGameOver = false;
        rng = new Random();
        lives = 3;
        score = 0;
        runOnce = 1;
        sharedPreferences = context.getSharedPreferences(GameView.PREFS_NAME, Context.MODE_PRIVATE);
        highScore[0] = sharedPreferences.getInt("hiscore1", 0);
        highScore[1] = sharedPreferences.getInt("hiscore2", 0);
        highScore[2] = sharedPreferences.getInt("hiscore3", 0);
        highScore[3] = sharedPreferences.getInt("hiscore4", 0);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        explosion = soundPool.load(context, R.raw.explosion, 1);
        powerup = soundPool.load(context, R.raw.powerup, 1);
        healthPack = new HealthPack(context, rng.nextInt(screenX - 100), -200);
        nuke = new Nuke(context, rng.nextInt(screenX - 100), -500);
        nukeSnd = soundPool.load(context, R.raw.nukesnd, 1);

        //WIP Explosion TODO
        gameOver = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
        health1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.health1);
        health2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.health2);
        health3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.health3);
        invadersArrayList = new ArrayList<Invaderstwo>();

        nuke0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke0);
        nuke1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke1);
        nuke2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke2);
        nuke3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke3);
        nuke4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke4);
        nuke5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke5);
        nuke6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke6);
        nuke7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke7);
        nuke8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke8);
        nuke9 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke9);
        nuke10 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke10);
        nuke11 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuke11);

        nuke0 = Bitmap.createScaledBitmap(nuke0, 700, 700, true);
        nuke1 = Bitmap.createScaledBitmap(nuke1, 700, 700, true);
        nuke2 = Bitmap.createScaledBitmap(nuke2, 700, 700, true);
        nuke3 = Bitmap.createScaledBitmap(nuke3, 700, 700, true);
        nuke4 = Bitmap.createScaledBitmap(nuke4, 700, 700, true);
        nuke5 = Bitmap.createScaledBitmap(nuke5, 700, 700, true);
        nuke6 = Bitmap.createScaledBitmap(nuke6, 700, 700, true);
        nuke7 = Bitmap.createScaledBitmap(nuke7, 700, 700, true);
        nuke8 = Bitmap.createScaledBitmap(nuke8, 700, 700, true);
        nuke9 = Bitmap.createScaledBitmap(nuke9, 700, 700, true);
        nuke10 = Bitmap.createScaledBitmap(nuke10, 700, 700, true);
        nuke11 = Bitmap.createScaledBitmap(nuke11, 700, 700, true);


        poof0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poof0);
        poof1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poof1);
        poof2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poof2);
        poof3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poof3);
        poof4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poof4);
        poof5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.poof5);
        //TODO Enemies
        for (int i = 0; i < 3; i++) {
            invadersArrayList.add(new Asteroid(context, rng.nextInt(screenX - 100)));
        }

    }


    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }

    }

    private void update() {
        healthPack.update();
        nuke.update();

        for (Invaderstwo invaders : invadersArrayList) {
            invaders.move();
        }
        if (score % 10 == 0 && score > 0) {
            score++;
            for (Invaderstwo invaders : invadersArrayList)
                invaders.setY_speed(invaders.getY_speed() + 1);
        }
        if (score % 49 == 0 && score > 0) {
            score++;
            invadersArrayList.add(new Ufo(context, rng.nextInt(screenX - 100), screenX));
        }
        if (score % 25 == 0 && score > 0) {
            score++;
            invadersArrayList.add(new Alien(context, rng.nextInt(screenX - 100), screenX));
            invadersArrayList.add(new Meteor(context, rng.nextInt(screenX - 100)));
        }

    }


    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);

            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(screenX * screenY / 10000);

            canvas.drawText("" + score, canvas.getWidth() / 2, 140, paint);
            //TODO Draw Lives Done
            if (lives == 3)
                canvas.drawBitmap(health3, canvas.getWidth() - health3.getWidth(), Math.round(0.72 * screenY), paint);
            else if (lives == 2)
                canvas.drawBitmap(health2, canvas.getWidth() - health2.getWidth(), Math.round(0.72 * screenY), paint);
            else if (lives == 1)
                canvas.drawBitmap(health2, canvas.getWidth() - health1.getWidth(), Math.round(0.72 * screenY), paint);
            else if (lives < 1) {
                isGameOver = true;
            }
            if (Math.round(healthPack.getY()) > Math.round(screenY / 2)) {
                healthPack.setX(rng.nextInt(screenX - 100));
                healthPack.setY((rng.nextInt(2) + 1) * -400);
                healthPack.getHitbox().set(healthPack.getX(), healthPack.getY(), healthPack.getX() + healthPack.getBitmap().getWidth(), healthPack.getY() + healthPack.getBitmap().getHeight());
            }
            if (Math.round(nuke.getY()) > Math.round(screenY / 2)) {
                nuke.setX(rng.nextInt(screenX - 100));
                nuke.setY((rng.nextInt(2) + 1) * -500);
                nuke.getHitbox().set(nuke.getX(), nuke.getY(), nuke.getX() + nuke.getBitmap().getWidth(), nuke.getY() + nuke.getBitmap().getHeight());
            }
            for (Invaderstwo invaders : invadersArrayList) {
                canvas.drawBitmap(invaders.getBitmap(), invaders.getX(), invaders.getY(), paint);
                if (Math.round(invaders.getY()) > Math.round(screenY / 2)) {
                    lives--;
                    anim3Start = 0;
                    anim3LocX = invaders.getX();
                    anim3LocY = invaders.getY();

                    if (!isGameOver) {
                        if (sharedPreferences.getBoolean("soundEnable", true))
                            soundPool.play(explosion, 1, 1, 0, 0, 1);
                    }
                    invaders.setX(rng.nextInt(screenX - 100));
                    invaders.setY((rng.nextInt(2) + 1) * -200);
                    invaders.getHitBox().set(invaders.getX(), invaders.getY(), invaders.getX() + invaders.getBitmap().getWidth(), invaders.getY() + invaders.getBitmap().getHeight());
                }
            }

            canvas.drawBitmap(healthPack.getBitmap(), healthPack.getX(), healthPack.getY(), paint);
            canvas.drawBitmap(nuke.getBitmap(), nuke.getX(), nuke.getY(), paint);
            anim3Start++;
            if (anim3Start >= 0 && anim3Start <= 2)
                canvas.drawBitmap(poof0, anim3LocX, anim3LocY, paint);
            else if (anim3Start >= 3 && anim3Start <= 5)
                canvas.drawBitmap(poof1, anim3LocX, anim3LocY, paint);
            else if (anim3Start >= 6 && anim3Start <= 8)
                canvas.drawBitmap(poof2, anim3LocX, anim3LocY, paint);
            else if (anim3Start >= 9 && anim3Start <= 11)
                canvas.drawBitmap(poof3, anim3LocX, anim3LocY, paint);
            else if (anim3Start >= 12 && anim3Start <= 14)
                canvas.drawBitmap(poof4, anim3LocX, anim3LocY, paint);
            else if (anim3Start >= 15 && anim3Start <= 17)
                canvas.drawBitmap(poof5, anim3LocX, anim3LocY, paint);

            anim2Start++;
            if (anim2Start >= 0 && anim2Start <= 2)
                canvas.drawBitmap(nuke0, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 3 && anim2Start <= 5)
                canvas.drawBitmap(nuke1, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 6 && anim2Start <= 8)
                canvas.drawBitmap(nuke2, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 9 && anim2Start <= 11)
                canvas.drawBitmap(nuke3, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 12 && anim2Start <= 14)
                canvas.drawBitmap(nuke4, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 15 && anim2Start <= 17)
                canvas.drawBitmap(nuke5, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 18 && anim2Start <= 20)
                canvas.drawBitmap(nuke6, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 21 && anim2Start <= 23)
                canvas.drawBitmap(nuke7, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 24 && anim2Start <= 26)
                canvas.drawBitmap(nuke8, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 27 && anim2Start <= 29)
                canvas.drawBitmap(nuke9, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 30 && anim2Start <= 32)
                canvas.drawBitmap(nuke10, anim2LocX, anim2LocY, paint);
            else if (anim2Start >= 33 && anim2Start <= 35)
                canvas.drawBitmap(nuke11, anim2LocX, anim2LocY, paint);


            animStart++;
            if (animStart >= 0 && animStart <= 2)
                canvas.drawBitmap(poof0, animLocX, animLocY, paint);
            else if (animStart >= 3 && animStart <= 5)
                canvas.drawBitmap(poof1, animLocX, animLocY, paint);
            else if (animStart >= 6 && animStart <= 8)
                canvas.drawBitmap(poof2, animLocX, animLocY, paint);
            else if (animStart >= 9 && animStart <= 11)
                canvas.drawBitmap(poof3, animLocX, animLocY, paint);
            else if (animStart >= 12 && animStart <= 14)
                canvas.drawBitmap(poof4, animLocX, animLocY, paint);
            else if (animStart >= 15 && animStart <= 17)
                canvas.drawBitmap(poof5, animLocX, animLocY, paint);

            if (isGameOver) {
                if (runOnce == 1) {
                    for (int i = 0; i < 4; i++) {
                        if (score > highScore[i]) {
                            highScore[i] = score;
                            i = 4;
                        }
                    }
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    for (int i = 1; i < 5; i++) {
                        e.putInt("hiscore" + i, highScore[i - 1]);
                    }
                    e.apply();
                    BackgroundMusic.muteMusic();
                    runOnce--;
                }
                canvas.drawBitmap(gameOver, canvas.getWidth() / 2 - gameOver.getWidth() / 2, Math.round(0.16 * screenY), paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int touchX = Math.round(motionEvent.getX());
        int touchY = Math.round(motionEvent.getY());
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                System.out.println("Hey touch works");
                for (Invaderstwo invaders : invadersArrayList) {
                    if (invaders.getHitBox().contains(touchX, touchY)) {
//                        System.out.println("Yeah here it is");
                        if (sharedPreferences.getBoolean("soundEnable", true))
                            soundPool.play(explosion, 1, 1, 0, 0, 1);
                        score++;
                        animStart = 0;
                        animLocX = touchX - poof0.getWidth() / 2;
                        animLocY = touchY - poof0.getHeight() / 2;
                        if (invaders.getType().equals("meteor")) {
                            invadersArrayList.remove(invaders);
                        } else {
                            invaders.setX(rng.nextInt(screenX - 100));
                            invaders.setY((rng.nextInt(2) + 1) * -200);
                            invaders.getHitBox().set(invaders.getX(), invaders.getY(), invaders.getX() + invaders.getBitmap().getWidth(), invaders.getY() + invaders.getBitmap().getHeight());
                        }

                    }
                }
                if (healthPack.getHitbox().contains(touchX, touchY)) {
                    if (lives < 3 && lives > 0) {
                        lives++;
                    }
                    if (sharedPreferences.getBoolean("soundEnable", true))
                        soundPool.play(powerup, 1, 1, 0, 0, 1);
                    healthPack.setX(rng.nextInt(screenX - 100));
                    healthPack.setY((rng.nextInt(2) + 1) * -300);
                    healthPack.getHitbox().set(healthPack.getX(), healthPack.getY(), healthPack.getX() + healthPack.getBitmap().getWidth(), healthPack.getY() + healthPack.getBitmap().getHeight());
                }

                if (nuke.getHitbox().contains(touchX, touchY)) {
                    if (sharedPreferences.getBoolean("soundEnable", true))
                        soundPool.play(nukeSnd, 1, 1, 0, 0, 1);
                    for (Invaderstwo invaders : invadersArrayList) {
                        invaders.setX(rng.nextInt(screenX - 100));
                        invaders.setY((rng.nextInt(2) + 1) * -400);
                        invaders.getHitBox().set(invaders.getX(), invaders.getY(), invaders.getX() + invaders.getBitmap().getWidth(), invaders.getY() + invaders.getBitmap().getHeight());
                    }
                    anim2Start = 0;
                    anim2LocX = screenX / 2 - nuke0.getWidth() / 2;
                    anim2LocY = screenY / 4 - nuke0.getHeight() / 2;
                    nuke.setX(rng.nextInt(screenX - 100));
                    nuke.setY((rng.nextInt(2) + 1) * -500);
                    nuke.getHitbox().set(nuke.getX(), nuke.getY(), nuke.getX() + nuke.getBitmap().getWidth(), nuke.getY() + nuke.getBitmap().getHeight());
                }
        }

        if (isGameOver) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                context.startActivity(new Intent(context, MainActivity.class));
                GameActivity activity = (GameActivity) this.context;
                activity.finish();
            }
        }
        return true;
    }


    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}