package ru.dajjsand.ufobattle.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import ru.dajjsand.ufobattle.Utils.Utils;

import static ru.dajjsand.ufobattle.Utils.Constants.B_SIZE;
import static ru.dajjsand.ufobattle.Utils.Constants.B_SPEED;
import static ru.dajjsand.ufobattle.Utils.Constants.SIZE;

public class Bullet {

    public Fixture body;
    public boolean inGame = true;

    private Vector2 rot;
    private World world;
    private Texture texture;

    public Bullet(Vector2 rot, World world, Vector2 pos) {
        this.world = world;
        this.rot = rot;
        body = Utils.createBox(world, check(pos), B_SIZE.x, B_SIZE.y, false, "bullet", (short) -2);
        texture = new Texture("bullets/bullet.png");
    }

    public void update(float delta) {
        body.getBody().setLinearVelocity(delta * B_SPEED * rot.x, delta * B_SPEED * rot.y);
        if (!inGame) {
            world.destroyBody(body.getBody());
            body = null;
            texture.dispose();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture,
            body.getBody().getPosition().x - B_SIZE.x / 2,
            body.getBody().getPosition().y - B_SIZE.y / 2,
            B_SIZE.x,
            B_SIZE.y);
    }

    public Vector2 check(Vector2 pos) {
        return new Vector2(pos.x + SIZE.x / 2f - B_SIZE.x / 2f, pos.y + SIZE.y / 2f - B_SIZE.y / 2f);
    }
}
