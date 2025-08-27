package ru.dajjsand.ufobattle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import ru.dajjsand.ufobattle.SpecialClasses.MyDialogWindow;

import static ru.dajjsand.ufobattle.Utils.Constants.SCORE;

public class ResultsScreen implements Screen {

    private static Preferences prefs;
    public static String name = "Player 1";
    private static final String[] nums = new String[]{"First", "Second", "Third", "Fourth", "Fifth",
        "Sixth", "Seventh", "Eighth", "Ninth", "Tenth"};

    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    public ResultsScreen() {
        MyDialogWindow myDW = new MyDialogWindow();
        prefs = Gdx.app.getPreferences("Records");
        if ((prefs.getLong(nums[0] + "s", 0)) == 0) {
            myDW.create(MyDialogWindow.DialogWindowType.Start, MenuScreen.stage);
            generatePrefs();
        }
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        font.getData().setScale(Gdx.graphics.getWidth() / 2560f);

        glyphLayout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f / 256, 63f / 256, 107f / 256, 256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (int i = 0; i < nums.length; i++) {
            // player name
            if (i % 2 == 0)
                glyphLayout.setText(font, prefs.getString(nums[i]), Color.YELLOW, 500, 11, false);
            else
                glyphLayout.setText(font, prefs.getString(nums[i]), Color.WHITE, 500, 11, false);

            font.draw(batch,
                glyphLayout,
                Gdx.graphics.getWidth() / 35f,
                Gdx.graphics.getHeight() - glyphLayout.height * (i + 1));

            // player result
            if (i % 2 == 0)
                glyphLayout.setText(font, prefs.getLong(nums[i] + "s") + "", Color.YELLOW, 500, Align.left, false);
            else
                glyphLayout.setText(font, prefs.getLong(nums[i] + "s") + "", Color.WHITE, 500, Align.left, false);

            font.draw(batch,
                glyphLayout,
                Gdx.graphics.getWidth() - glyphLayout.width - Gdx.graphics.getWidth() / 35f,
                Gdx.graphics.getHeight() - glyphLayout.height * (i + 1));
        }
        batch.end();
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
        font.dispose();
        batch.dispose();
    }

    public static void saveResults() {
        int newPlace = 9;
        for (int i = 0; i < nums.length; i++) {
            if (prefs.getLong(nums[i] + "s", 0) < SCORE) {
                newPlace = i;
                break;
            }
        }
        for (int i = nums.length - 1; i > newPlace; i--)
            addToPrefs(i, prefs.getLong(nums[i - 1] + "s"), prefs.getString(nums[i - 1]));
        addToPrefs(newPlace, SCORE, name);
    }

    public static void generatePrefs() {
        int[] scores = new int[]{198, 150, 135, 114, 105, 94, 87, 73, 60};
        String[] names = new String[]{"Damir", "Vadim", "Bulat", "Leonid", "Kirill", "Kamilya", "Ayrat", "Katya", "Amir"};
        for (int i = 0; i < 9; i++)
            addToPrefs(i, scores[i], names[i]);
        addToPrefs(9, 0, name);
    }

    public static void removePrefs() {
        prefs.clear();
        generatePrefs();
    }

    private static void addToPrefs(int i, long score, String name) {
        prefs.putLong(nums[i] + "s", score).flush();
        prefs.putString(nums[i], name).flush();
    }
}
