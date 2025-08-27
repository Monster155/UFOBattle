package ru.dajjsand.ufobattle.SpecialClasses;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import ru.dajjsand.ufobattle.Screens.ResultsScreen;
import ru.dajjsand.ufobattle.Screens.SettingsScreen;

public class MyDialogWindow extends ApplicationAdapter {

    Dialog dialog;
    Skin skin;
    Stage stage;
    TextureAtlas atlas;
    TextField textField;
    Stage stageAnother;

    public void create(DialogWindowType type, Stage stageAnother) {
        atlas = new TextureAtlas(Gdx.files.internal("skin/skin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas);

        stage = new Stage();
        this.stageAnother = stageAnother;

        createDialog(type);
        dialog.scaleBy(Gdx.graphics.getHeight() / 640f * 1.5f);
        dialog.setPosition(Gdx.graphics.getWidth() / 2f - dialog.getWidth() / 2, Gdx.graphics.getHeight() / 2f - dialog.getWidth() / 2);
        dialog.show(stage);
        stage.setKeyboardFocus(textField);
        textField = (TextField) stage.getKeyboardFocus();
    }

    public void render() {
        Gdx.input.setInputProcessor(stage);

        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    void createDialog(DialogWindowType type) {
        if (type.equals(DialogWindowType.Rename)) {
            SettingsScreen.rend = true;
            textField = new TextField("", skin);
            dialog = new Dialog("Change your name", skin) {
                protected void result(Object object) {
                    if (object.equals(1L)) {
                        System.out.println("OK");
                        Gdx.input.setInputProcessor(stageAnother);
                        textField = (TextField) stage.getKeyboardFocus();
                        ResultsScreen.name = textField.getText(); //Всё работает, кроме этого присваивания
                        SettingsScreen.rend = false;
                    } else {
                        System.out.println("Cancel");
                        Gdx.input.setInputProcessor(stageAnother);
                        SettingsScreen.rend = false;
                    }
                    dispose();
                }
            };
            dialog.text("All your previous records will save by your previous name");
            dialog.add(textField);
        }

        if (type.equals(DialogWindowType.Delete)) {
            SettingsScreen.rend = true;
            dialog = new Dialog("Do you want to reset records?", skin) {
                protected void result(Object object) {
                    if (object.equals(1L)) {
                        System.out.println("Yes");
                        Gdx.input.setInputProcessor(stageAnother);
                        SettingsScreen.rend = false;
                        ResultsScreen.removePrefs();
                    } else {
                        System.out.println("No");
                        Gdx.input.setInputProcessor(stageAnother);
                        SettingsScreen.rend = false;
                    }
                    dispose();
                }
            };
            dialog.text("You can't recover your records!");
        }

        if (type.equals(DialogWindowType.Start)) {
            textField = new TextField("", skin);
            dialog = new Dialog("Enter your name", skin) {
                protected void result(Object object) {
                    if (object.equals(1L)) {
                        System.out.println("OK");
                        Gdx.input.setInputProcessor(stageAnother);
                        ResultsScreen.name = textField.getText();
                        SettingsScreen.rend = false;
                    } else {
                        System.out.println("Cancel");
                        Gdx.input.setInputProcessor(stageAnother);
                        SettingsScreen.rend = false;
                    }
                    dispose();
                }
            };
            dialog.text("All your records will save by this name");
            dialog.addActor(textField);
        }
        dialog.button("OK", 1L);
        dialog.button("Cancel", 2L);
    }

    public enum DialogWindowType{
        Rename,
        Delete,
        Start
    }
}
