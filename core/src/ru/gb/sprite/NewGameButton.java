package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gb.base.ScaledButton;
import ru.gb.math.Rect;

public class NewGameButton extends ScaledButton {

    private static final float HEIGHT = 0.06f;

    public NewGameButton(TextureAtlas atlas) {
        super(atlas.findRegion("button_new_game"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setTop(worldBounds.pos.y  - 0.01f);
    }

    @Override
    protected void action() {

    }
}
