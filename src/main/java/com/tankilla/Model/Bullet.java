package com.tankilla.model;

public class Bullet {

    private int positionX;
    private int positionY;
    private int bulletAngle;

    public Bullet(int positionX, int positionY, int bulletAngle) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.bulletAngle = bulletAngle;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getBulletAngle() {
        return bulletAngle;
    }

    public void setBulletAngle(int bulletAngle) {
        this.bulletAngle = bulletAngle;
    }
}
