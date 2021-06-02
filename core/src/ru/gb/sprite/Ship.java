package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class Ship extends Sprite {

    private static final float V_LEN = 0.15f;
    private static final float HEIGHT = 0.16f;
    private static final float PADDING = 0.03f;

    private Vector2 v;
    private float vx;
    private float vy = 0;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        v = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v,delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < 0){
            vx = -1f;
        } else {
            vx = 1f;
        }
        v.set(vx, vy).setLength(V_LEN);
        return false;
    }

}
