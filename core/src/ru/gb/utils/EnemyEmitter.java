package ru.gb.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.math.Rect;
import ru.gb.math.Rnd;
import ru.gb.pool.EnemyPool;
import ru.gb.sprite.EnemyShip;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 2.5f;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 1.3f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 1.5f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.2f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1.8f;
    private static final int ENEMY_BIG_HP = 10;

    private static final float GENERATE_INTERVAL_LEVEL_FACTOR = 0.06f;

    private static final float ENEMY_BULLET_VY_LEVEL_FACTOR = 0.01f;
    private static final int ENEMY_DAMAGE_LEVEL_FACTOR = 10;
    private static final float ENEMY_RELOAD_INTERVAL_LEVEL_FACTOR = 0.01f;
    private static final int ENEMY_HP_LEVEL_FACTOR = 15;

    private float generateTimer;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallV;
    private final Vector2 enemyMediumV;
    private final Vector2 enemyBigV;

    private final TextureRegion bulletRegion;

    private final Rect worldBounds;
    private final EnemyPool enemyPool;

    private int level = 1;

    public EnemyEmitter(Rect worldBounds, EnemyPool enemyPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        enemySmallV = new Vector2(0, -0.1f);
        enemyMediumV = new Vector2(0, -0.03f);
        enemyBigV = new Vector2(0, -0.008f);
    }

    public void generate(float delta, int frags) {
        level = frags / 5 + 1;
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL - (level * GENERATE_INTERVAL_LEVEL_FACTOR)) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemyShip.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY - (level * ENEMY_BULLET_VY_LEVEL_FACTOR),
                        ENEMY_SMALL_DAMAGE + (level * ENEMY_SMALL_DAMAGE / ENEMY_DAMAGE_LEVEL_FACTOR),
                        ENEMY_SMALL_RELOAD_INTERVAL + (level * ENEMY_RELOAD_INTERVAL_LEVEL_FACTOR),
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP + (level * ENEMY_SMALL_HP / ENEMY_HP_LEVEL_FACTOR)
                );
            } else if (type < 0.8f) {
                enemyShip.set(
                        enemyMediumRegions,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY - (level * ENEMY_BULLET_VY_LEVEL_FACTOR),
                        ENEMY_MEDIUM_DAMAGE + (level * ENEMY_MEDIUM_DAMAGE / ENEMY_DAMAGE_LEVEL_FACTOR),
                        ENEMY_MEDIUM_RELOAD_INTERVAL + (level * ENEMY_RELOAD_INTERVAL_LEVEL_FACTOR),
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP + (level * ENEMY_MEDIUM_HP / ENEMY_HP_LEVEL_FACTOR)
                );
            } else {
                enemyShip.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY - (level * ENEMY_BULLET_VY_LEVEL_FACTOR),
                        ENEMY_BIG_DAMAGE + (level * ENEMY_BIG_DAMAGE / ENEMY_DAMAGE_LEVEL_FACTOR),
                        ENEMY_BIG_RELOAD_INTERVAL + (level * ENEMY_RELOAD_INTERVAL_LEVEL_FACTOR),
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP + (level * ENEMY_BIG_HP / ENEMY_HP_LEVEL_FACTOR)
                );
            }
            float enemyHalfWidth = enemyShip.getHalfWidth();
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyHalfWidth, worldBounds.getRight() - enemyHalfWidth);
            enemyShip.setBottom(worldBounds.getTop());
            enemyShip.setBulletPos(enemyShip.pos);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
