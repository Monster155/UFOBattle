package ru.dajjsand.ufobattle.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public final class Constants {
    public static final int PPM = 32;
    public static final float C_SPEED = 300f;
    public static final float SHOOT_RATE = 3f; //кол-во выстрелов в 1 секунду
    public static long SCORE = 0;
    public static final Vector2 SIZE = new Vector2(32f / PPM, 32f / PPM);
    public static final Vector2 B_SIZE = new Vector2(SIZE.x / 2f, SIZE.y / 2f);
    public static final Vector2 L_SIZE = new Vector2(SIZE.x, SIZE.y / 3f);
    public static final int MAXLIVES = 10;
    public static int LIVES = MAXLIVES; // Всегда должно быть чётным числом!!! И его нужно обновлять в GameScreen
    public static final float E_SPEED = C_SPEED * 1.1f;
    public static final float B_SPEED = C_SPEED * 3f;
    public static final float PM = PPM * 2f;
    public static Vector2 stickCS = new Vector2(0f, 0f);
    public static Vector2 gunCS = new Vector2(0f, 0f);
    public static float DrawX;
    public static float Scale;
    public static Vector2 CamScale = new Vector2(1280f, 1280f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
    public static final int MaxEnemy = 6;
}
