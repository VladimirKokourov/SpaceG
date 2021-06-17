package ru.gb.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.math.Rect;
import ru.gb.pool.BulletPool;
import ru.gb.pool.ExplosionPool;
import ru.gb.sprite.Bullet;
import ru.gb.sprite.Explosion;
import ru.gb.utils.EnemyEmitter;

public class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private static final int IMPROVE_SHIP_LEVEL = 10;

    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected Vector2 doubleBulletPosLeft;
    protected Vector2 doubleBulletPosRight;
    protected Sound bulletSound;
    protected float bulletHeight;
    protected int damage;
    protected int hp;
    protected int level;

    protected float reloadInterval;
    protected float reloadTimer;

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0;
            if(level < IMPROVE_SHIP_LEVEL) {
                shoot();
            } else {
                doubleShoot();
            }
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public int getDamage() {
        return damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Vector2 getV() {
        return v;
    }

    public void setBulletPos(Vector2 bulletPos) {
        this.bulletPos.set(bulletPos);
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play();
    }

    private void doubleShoot() {
        Bullet bulletLeft = bulletPool.obtain();
        Bullet bulletRight = bulletPool.obtain();
        bulletLeft.set(this, bulletRegion, doubleBulletPosLeft, bulletV, worldBounds, damage, bulletHeight);
        bulletRight.set(this, bulletRegion, doubleBulletPosRight, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.pos, getHeight());
    }
}
