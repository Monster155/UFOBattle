package ru.dajjsand.ufobattle.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.dajjsand.ufobattle.Utils.Constants;
import ru.dajjsand.ufobattle.Utils.Utils;

import static ru.dajjsand.ufobattle.Utils.Constants.C_SPEED;
import static ru.dajjsand.ufobattle.Utils.Constants.LIVES;
import static ru.dajjsand.ufobattle.Utils.Constants.MAXLIVES;
import static ru.dajjsand.ufobattle.Utils.Constants.SIZE;

public class Player {
    public Fixture body;
    public Texture texture;

    private final String path = "PNG/blue";

    public Player(World world) {
        body = Utils.createBox(world, 50f, 50f, SIZE.x, SIZE.y, false, "player", (short) -2);
        texture = new Texture(path + "1.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture,
            body.getBody().getPosition().x - SIZE.x / 2f,
            body.getBody().getPosition().y - SIZE.y / 2f,
            SIZE.x,
            SIZE.y);
    }

    public void update(float delta) {
        float x = Constants.stickCS.x, y = Constants.stickCS.y;
        // pc control
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            x--;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            x++;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            y++;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            y--;

        body.getBody().setLinearVelocity(delta * C_SPEED * x, delta * C_SPEED * y);
    }

    public void damaged() {
        LIVES--;
        texture.dispose();
        if (LIVES > MAXLIVES * 3f / 4f) { //Damage level 1
            texture = new Texture(path + "1.png");
        } else if (LIVES > MAXLIVES / 2f) { //Damage level 2
            texture = new Texture(path + "2.png");
        } else if (LIVES > 0) { //Damage level 3
            texture = new Texture(path + "3.png");
        } else { //Game Over
            Gdx.app.log("Game", "Game Over");
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
