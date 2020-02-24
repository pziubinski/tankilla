package com.tankilla.model;

public class Board {

    private Tank redTank;
    private Tank greenTank;
    private Bullet bullet;

    public Board() {
        redTank = new Tank(100,200.0, 597, 0);
        greenTank = new Tank(100,1200, 597, 0);
        bullet = new Bullet(0, 0);
    }

    public Tank getRedTank() {
        System.out.println("redTank barrel angle: " + redTank.getBarrelAngle());
        return redTank;
    }

    public Tank getGreenTank() { return greenTank; }
    public Bullet getBullet() { return bullet; }

}
