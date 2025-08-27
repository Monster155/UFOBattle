package ru.dajjsand.ufobattle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ru.dajjsand.ufobattle.Utils.Constants;

public class MenuScreen implements Screen {

    private Texture texture;
    private TextureAtlas atlas;
    private Skin skin;
    public static Stage stage;
    public static int screen;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        texture = new Texture("masterpiece.png");
        Constants.Scale = (float) Gdx.graphics.getHeight() * texture.getWidth() / texture.getHeight();
        Constants.DrawX = (Constants.Scale - Gdx.graphics.getWidth()) / 2 * -1;

        atlas = new TextureAtlas(Gdx.files.internal("buttons/button.pack"));
        skin = new Skin(atlas);

        float btnScale = 5;
        float height = Gdx.graphics.getHeight() / 6f;
        float width = Gdx.graphics.getWidth() / 2f - btnScale * height / 2f;

        createImageButton(width, height * 2f, btnScale * height, height, "play", 1);
        createImageButton(width, height, btnScale * height, height, "records", 2);
        createImageButton(width, 0, btnScale * height, height, "tutorial", 3);
        createImageButton(0, 0, height, height, "settings", 4);

        screen = 0;
    }

    @Override
    public void render(float delta) {
        stage.act();

        Gdx.gl.glClearColor(94f / 256, 63f / 256, 107f / 256, 256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(texture,
            Constants.DrawX,
            0,
            Constants.Scale,
            Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }

    public void createImageButton(float x, float y, float width, float height, String text, final int screen) {
        ImageButton.ImageButtonStyle imageButtonStyle;
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetY = -1;
        imageButtonStyle.up = skin.getDrawable(text);
        ImageButton imageButton = new ImageButton(imageButtonStyle);
        imageButton.setSize(width, height);
        imageButton.setPosition(x, y);

        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.screen = screen;
            }
        });

        stage.addActor(imageButton);
    }
}
