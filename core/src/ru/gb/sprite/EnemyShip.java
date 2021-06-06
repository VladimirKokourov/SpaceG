package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;
import ru.gb.math.Rnd;

public class EnemyShip extends Sprite {

    private Vector2 v;
    private Rect worldBounds;

    public EnemyShip() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    public void set(
                    TextureRegion region,
                    Vector2 pos0,
                    Vector2 v0,
                    Rect worldBounds,
                    float height
    ) {
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        this.worldBounds = worldBounds;
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }
}
