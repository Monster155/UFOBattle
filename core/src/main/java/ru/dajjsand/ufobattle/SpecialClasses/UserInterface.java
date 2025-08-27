package ru.dajjsand.ufobattle.SpecialClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ru.dajjsand.ufobattle.Utils.Constants;

import static ru.dajjsand.ufobattle.Utils.Constants.LIVES;
import static ru.dajjsand.ufobattle.Utils.Constants.MAXLIVES;
import static ru.dajjsand.ufobattle.Utils.Constants.SCORE;

public class UserInterface {

    private Camera camera;
    private Vector3 touchPosLeft, touchPosRight;
    private Vector2 circleSize, cursorSize;

    private float scale; //For different screen sizes

    private final String lifePath = "lifes/hudHeart_";
    private Texture circleTex, cursorTex;
    private Texture[] lifeTextures;
    private Sprite circleSprite, cursorSprite;

    private double time;
    private float xScore;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    public UserInterface(Camera camera) {
        this.camera = camera;
        circleTex = new Texture("Controller/joystick.png");
        cursorTex = new Texture("Controller/button.png");

        circleSprite = new Sprite(circleTex);
        cursorSprite = new Sprite(cursorTex);

        scale = Gdx.graphics.getHeight() / 2f;

        touchPosLeft = new Vector3(scale / 2f, scale / 2f, 0f);
        touchPosRight = new Vector3(Gdx.graphics.getWidth() - scale / 2f, scale / 2f, 0f);

        circleSize = new Vector2(scale, scale);
        cursorSize = new Vector2(scale / ((float) circleTex.getWidth() / cursorTex.getWidth()), scale / ((float) circleTex.getHeight() / cursorTex.getHeight()));

        lifeTextures = new Texture[MAXLIVES / 2];
        for (int i = 0; i < lifeTextures.length; i++)
            lifeTextures[i] = new Texture(lifePath + "full.png");

        //score and time
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(Gdx.graphics.getWidth() / 2560f);

        glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, "Your score: XXX");
        xScore = glyphLayout.width;
    }

    public void act(float delta) {
        touchPosLeft = new Vector3(scale / 2f, scale / 2f, 0f);
        Constants.stickCS = new Vector2(0f, 0f);
        touchPosRight = new Vector3(Gdx.graphics.getWidth() - scale / 2f, scale / 2f, 0f);
        Constants.gunCS = new Vector2(0f, 0f);

        //max num of fingers on the screen
        int fingers = 4;
        for (int i = 0; i < fingers; i++) {
            if (Gdx.input.isTouched(i)) {
                if (Gdx.input.getX(i) < Gdx.graphics.getWidth() / 2f) { //Left joystick
                    touchPosLeft.set(Gdx.input.getX(i), Gdx.input.getY(i), 0f);
                    camera.unproject(touchPosLeft);
                    Vector2 tP = new Vector2(touchPosLeft.x - circleSize.x / 2f, touchPosLeft.y - circleSize.y / 2f);

                    float cosX = (float) (tP.x / Math.hypot(tP.x, tP.y));
                    float sinY = (float) (tP.y / Math.hypot(tP.x, tP.y));

                    if (tP.x * tP.x + tP.y * tP.y > (circleSize.x - cursorSize.x / 1.5f) * (circleSize.x - cursorSize.x / 1.5f) / 4f) {
                        touchPosLeft.x = cosX * (circleSize.x / 2f - cursorSize.x / 3f) + circleSize.x / 2f;
                        touchPosLeft.y = sinY * (circleSize.y / 2f - cursorSize.y / 3f) + circleSize.x / 2f;
                    }

                    Constants.stickCS = new Vector2(cosX, sinY);
                } else { //Right UserInterface
                    touchPosRight.set(Gdx.input.getX(i), Gdx.input.getY(i), 0f);
                    camera.unproject(touchPosRight);

                    Vector2 tP = new Vector2(touchPosRight.x - Gdx.graphics.getWidth() + circleSize.x / 2f, touchPosRight.y - circleSize.y / 2f);

                    float cosX = (float) (tP.x / Math.hypot(tP.x, tP.y));
                    float sinY = (float) (tP.y / Math.hypot(tP.x, tP.y));

                    if (tP.x * tP.x + tP.y * tP.y > (circleSize.x - cursorSize.x / 1.5f) * (circleSize.x - cursorSize.x / 1.5f) / 4f) {
                        touchPosRight.x = cosX * (circleSize.x / 2f - cursorSize.x / 3f) + Gdx.graphics.getWidth() - circleSize.x / 2f;
                        touchPosRight.y = sinY * (circleSize.y / 2f - cursorSize.y / 3f) + circleSize.y / 2f;
                    }

                    Constants.gunCS = new Vector2(cosX, sinY);
                }
            }
        }

        ui(delta);
    }

    public void draw(Batch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.draw(circleSprite, 0f, 0f, circleSize.x, circleSize.y);
        batch.draw(cursorSprite, touchPosLeft.x - cursorSize.x / 2f, touchPosLeft.y - cursorSize.y / 2f, cursorSize.x, cursorSize.y);
        batch.draw(circleSprite, Gdx.graphics.getWidth() - circleSize.x, 0f, circleSize.x, circleSize.y);
        batch.draw(cursorSprite, touchPosRight.x - cursorSize.x / 2f, touchPosRight.y - cursorSize.y / 2f, cursorSize.x, cursorSize.y);
        float x = lifeTextures[0].getWidth() * Gdx.graphics.getWidth() / Constants.CamScale.x,
            y = lifeTextures[0].getHeight() * Gdx.graphics.getHeight() / Constants.CamScale.y;
        for (int i = 0; i < lifeTextures.length; i++)
            batch.draw(lifeTextures[i], i * x, Gdx.graphics.getHeight() - y, x, y);

        glyphLayout.setText(font, "Your time: " + (int) time);
        font.draw(batch, //right position
            glyphLayout,
            Gdx.graphics.getWidth() - xScore,
            Gdx.graphics.getHeight() - glyphLayout.height);

        glyphLayout.setText(font, "Your score: " + SCORE);
        font.draw(batch, //center position (left - lives)
            glyphLayout,
            Gdx.graphics.getWidth() / 2f - glyphLayout.width / 2f,
            Gdx.graphics.getHeight() - glyphLayout.height);
    }

    public void dispose() {
        circleTex.dispose();
        cursorTex.dispose();
        font.dispose();
        glyphLayout.reset();

        for (Texture lifeTexture : lifeTextures)
            lifeTexture.dispose();
    }

    public void ui(float delta) {
        //hearts
        for (int i = 0; i < LIVES / 2; i++) {
            lifeTextures[i].dispose();
            lifeTextures[i] = new Texture(lifePath + "full.png");
        }
        if (LIVES % 2 > 0) {
            lifeTextures[LIVES / 2].dispose();
            lifeTextures[LIVES / 2] = new Texture(lifePath + "half.png");
        }
        for (int i = LIVES / 2 + LIVES % 2; i < MAXLIVES / 2; i++) {
            lifeTextures[i].dispose();
            lifeTextures[i] = new Texture(lifePath + "empty.png");
        }

        //score and time
        time += delta;
    }
}
