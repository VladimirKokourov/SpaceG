package ru.gb.pool;

import ru.gb.base.SpritesPool;
import ru.gb.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }
}
