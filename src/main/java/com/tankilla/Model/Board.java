package com.tankilla.Model;

public class Board {

    private Tank redTank;
    private Tank greenTank;
    private Bullet bullet;
    private GameState state;

    public Board() {
        redTank = new Tank(100, "red", 200, 597, 0);
        greenTank = new Tank(100, "green", 1200, 597, -60);
        bullet = new Bullet();

        state = GameState.STARTED;
    }

    public Tank getRedTank() {
        return redTank;
    }
    public Tank getGreenTank() { return greenTank; }
    public Bullet getBullet() { return bullet; }

}
