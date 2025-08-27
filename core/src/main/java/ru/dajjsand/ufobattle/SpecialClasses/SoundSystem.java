package ru.dajjsand.ufobattle.SpecialClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

public class SoundSystem {
    private final Music shoot1 = Gdx.audio.newMusic(Gdx.files.internal("Music/shoot1.mp3"));
    private final Music playThemeGame = Gdx.audio.newMusic(Gdx.files.internal("Music/playTheme.mp3"));
    private final Music playThemeMenu = Gdx.audio.newMusic(Gdx.files.internal("Music/mainMenu.mp3"));
    private Preferences prefs;

    public SoundSystem() {
        shoot1.setLooping(false);
        playThemeGame.setLooping(true);
        playThemeMenu.setLooping(true);

        prefs = Gdx.app.getPreferences("Settings");
        if (!prefs.contains("music")) {
            prefs.putBoolean("music", true).flush();
            Gdx.app.log("music", "now is true");
        }
        if (!prefs.contains("sound")) {
            prefs.putBoolean("sound", true).flush();
            Gdx.app.log("sound", "now is true");
        }
    }

    public void playSound(String musicName, boolean isMusic) {
        switch (musicName) {
            case "shoot1":
                if (canPlay(isMusic)) {
                    shoot1.stop();
                    shoot1.play();
                }
                break;
            case "playTheme":
                if (!canPlay(isMusic)) {
                    playThemeMenu.pause();
                    playThemeGame.stop();
                    Gdx.app.log("Test", "Success");
                } else if (prefs.getBoolean("playTheme")) {
                    playThemeMenu.play();
                    playThemeGame.stop();
                } else {
                    playThemeMenu.pause();
                    playThemeGame.play();
                }
                break;
        }
        Gdx.app.log("Sound/Music", musicName);
    }

    public boolean canPlay(boolean isMusic) {
        if (prefs.getBoolean("sound") && !isMusic)
            return true;

        return prefs.getBoolean("music") && isMusic;
    }
}
