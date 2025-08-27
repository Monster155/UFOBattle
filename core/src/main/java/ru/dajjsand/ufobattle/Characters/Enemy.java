package ru.dajjsand.ufobattle.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import ru.dajjsand.ufobattle.Utils.Utils;

import static ru.dajjsand.ufobattle.Utils.Constants.E_SPEED;
import static ru.dajjsand.ufobattle.Utils.Constants.L_SIZE;
import static ru.dajjsand.ufobattle.Utils.Constants.SIZE;

public class Enemy {

    public Fixture body;
    public Vector2 rot = new Vector2(0f, 0f);
    public boolean inGame = true;
    public int live = 5;
    public long change = TimeUtils.nanoTime() - 10, timeToChange = (int) (Math.random() * 2 + 2);

    private World world;
    private Texture texture, lifeTexture;
    private String path;

    public Enemy(World world, Vector2 pos) {
        this.world = world;

        body = Utils.createBox(world, rand(pos), SIZE.x, SIZE.y, false, "enemy", (short) -1);

        switch ((int) (Math.random() * 4 + 1)) {
            case 1:
                path = "PNG/green";
                break;
            case 2:
                path = "PNG/pink";
                break;
            case 3:
                path = "PNG/sand";
                break;
            case 4:
                path = "PNG/yellow";
                break;
        }
        Gdx.app.log("Path for Enemy", path + "");

        texture = new Texture(path + "1.png");
        lifeTexture = new Texture("lifes/lifeline.png");
    }

    public void update(float delta, Vector2 pos) {
        if (MathUtils.nanoToSec * (TimeUtils.nanoTime() - change) > timeToChange) {
            calcRot(pos);
            change = TimeUtils.nanoTime();
            timeToChange = (int) (Math.random() * 2 + 2);
        }

        body.getBody().setLinearVelocity(delta * E_SPEED * rot.x, delta * E_SPEED * rot.y);

        if (!inGame) {
            world.destroyBody(body.getBody());
            body = null;
            texture.dispose();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture,
            body.getBody().getPosition().x - SIZE.x / 2f,
            body.getBody().getPosition().y - SIZE.y / 2f,
            SIZE.x,
            SIZE.y);
        batch.draw(lifeTexture,
            body.getBody().getPosition().x - SIZE.x / 2f,
            body.getBody().getPosition().y + SIZE.y * 3f / 4f,
            L_SIZE.x * live / 5f,
            L_SIZE.y);
    }

    public Vector2 rand(Vector2 pos) {
        float ax = 25f, bx = 75f, ay = 25f, by = 75f;

        float x = ax + (float) (Math.random() * (bx - ax));
        while (pos.x + SIZE.x * 2 > x && pos.x - SIZE.x * 2 < x)
            x = ax + (float) (Math.random() * (bx - ax));

        float y = ay + (float) (Math.random() * (by - ay));
        while (pos.y + SIZE.y * 2 > y && pos.y - SIZE.y * 2 < y)
            y = ay + (float) (Math.random() * (by - ay));

        return new Vector2(x, y);
    }

    public void calcRot(Vector2 pos) {
        float x = body.getBody().getPosition().x, y = body.getBody().getPosition().y;
        if (x > pos.x + SIZE.x)
            rot.x = -1;
        else if (x < pos.x - SIZE.x)
            rot.x = 1;
        else
            rot.x = 0;

        if (y > pos.y + SIZE.y)
            rot.y = -1;
        else if (y < pos.y - SIZE.y)
            rot.y = 1;
        else
            rot.y = 0;

        if (rot.x == 0 && rot.y == 0) {
            if (x > pos.x)
                rot.x = -1;
            else if (x < pos.x)
                rot.x = 1;
            else
                rot.x = 0;

            if (y > pos.y)
                rot.y = -1;
            else if (y < pos.y)
                rot.y = 1;
            else
                rot.y = 0;
        }
    }

    public void damaged() {
        live--;
        if (live > 6)
            texture = new Texture(path + "1.png");
        else if (live > 3)
            texture = new Texture(path + "2.png");
        else if (live > 0)
            texture = new Texture(path + "3.png");
        else inGame = false;
    }
}
