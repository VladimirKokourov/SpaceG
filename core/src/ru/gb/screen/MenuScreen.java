package ru.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.gb.base.BaseScreen;


public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture background;
    private Vector2 pos;
    private Vector2 v1;
    private Vector2 v2;
    float distance;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        background = new Texture("background.jpg");
        pos = new Vector2();
        v1 = new Vector2();
        v2 = new Vector2(300, 300);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        distance = v2.cpy().sub(pos).len();
        if (distance > 0.5f) {
            pos.add(v1.nor());
        }
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        v1.set(screenX, Gdx.graphics.getHeight() - screenY);
        v1.sub(pos);
        v2.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
