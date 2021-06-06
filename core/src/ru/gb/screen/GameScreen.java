package ru.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.gb.base.BaseScreen;
import ru.gb.math.Rect;
import ru.gb.math.Rnd;
import ru.gb.pool.BulletPool;
import ru.gb.pool.EnemyShipPool;
import ru.gb.sprite.Background;
import ru.gb.sprite.EnemyShip;
import ru.gb.sprite.MainShip;
import ru.gb.sprite.Star;
import ru.gb.utils.Regions;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Music music;
    private Sound sound;
    private Texture bg;
    private TextureAtlas atlas;
    private Rect worldBounds;

    private Background background;
    private Star[] stars;

    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private MainShip mainShip;
    private TextureRegion[] enemyShipRegion;

    private Vector2 enemyPos;
    private Vector2 enemyV;
    private float timer;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyShipPool = new EnemyShipPool();
        mainShip = new MainShip(atlas, bulletPool, sound);
        enemyShipRegion = Regions.split(atlas.findRegion("enemy2"),1,2,2);
    }

    @Override
    public void render(float delta) {
        playMusic();
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        music.dispose();
        sound.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        timer -= delta;
        if(timer < 0.0f) {
            timer = Rnd.nextFloat(2f,4f);
            enemyAttack();
        }
        for (Star star : stars) {
            star.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
    }

    private void playMusic() {
        music.play();
        music.setLooping(true);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        enemyShipPool.freeAllDestroyed();
    }

    private void draw() {
        ScreenUtils.clear(0.33f, 0.45f, 0.68f, 1);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    public void enemyAttack() {
        float vx = Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight());
        enemyPos = new Vector2(vx,worldBounds.getHalfHeight());
        enemyV = new Vector2(0,-0.1f);
        EnemyShip enemyShip = enemyShipPool.obtain();
        enemyShip.set(enemyShipRegion[0], enemyPos, enemyV, worldBounds, 0.10f);
    }
}
