package ru.gb;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class SpaceG extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Texture background;
	private TextureRegion region;

	private float x;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		background = new Texture("background.jpg");
		region = new TextureRegion(img, 10,10,200,200);
	}

	@Override
	public void render () {
		x++;
		ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
		batch.begin();
		batch.draw(background,0,0);
		batch.draw(img, x, 0);
		batch.draw(region,300,300,150,150);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
