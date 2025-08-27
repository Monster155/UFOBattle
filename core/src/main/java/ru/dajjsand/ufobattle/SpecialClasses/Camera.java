package ru.dajjsand.ufobattle.SpecialClasses;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import ru.dajjsand.ufobattle.Characters.Player;

import static ru.dajjsand.ufobattle.Utils.Constants.CamScale;
import static ru.dajjsand.ufobattle.Utils.Constants.PPM;

public class Camera {
    public OrthographicCamera camera;
    private Player player;

    public Camera(Player player) {
        this.player = player;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CamScale.x / PPM / 1.75f, CamScale.y / PPM / 1.75f);
    }

    public void update() {
        camera.position.set(new Vector3(player.body.getBody().getPosition(), camera.position.z));
        camera.update();
    }
}
