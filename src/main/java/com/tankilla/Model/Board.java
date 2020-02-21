package com.tankilla.model;

public class Board {

    private Tank redTank;
    private Tank greenTank;
    private GameState state;
    private Bullet bullet;

    public Board() {
        redTank = new Tank(100, "red", 200.0, 597, 0);
        greenTank = new Tank(100, "green", 1200, 597, -180);
        bullet = new Bullet(0, 0, 0);
        state = GameState.STARTED;
    }

    public Tank getRedTank() {
        return redTank;
    }
    public Tank getGreenTank() { return greenTank; }
    public Bullet getBullet() { return bullet; }

}
