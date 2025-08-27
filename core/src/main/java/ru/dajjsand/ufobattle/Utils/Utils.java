package ru.dajjsand.ufobattle.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Utils {
    public static Fixture createBox(World world, float x, float y, float width, float height,
                                    boolean isStatic, String userData, short groupIndex) {
        Filter f = new Filter();
        f.groupIndex = groupIndex;

        Body pBody;
        Fixture fixture;
        BodyDef def = new BodyDef();

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x, y);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);

        fixture = pBody.createFixture(shape, 1f);
        fixture.setUserData(userData);
        fixture.setFilterData(f);
        shape.dispose();

        return fixture;
    }

    public static Fixture createBox(World world, Vector2 pos, float width, float height,
                                    boolean isStatic, String userData, short groupIndex) {
        Filter f = new Filter();
        f.groupIndex = groupIndex;

        Body pBody;
        Fixture fixture;
        BodyDef def = new BodyDef();

        if (isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(pos.x, pos.y);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);

        fixture = pBody.createFixture(shape, 1f);
        fixture.setUserData(userData);
        fixture.setFilterData(f);
        shape.dispose();

        return fixture;
    }
}
