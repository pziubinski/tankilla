package com.tankilla.model;

public class Bullet {

    private double positionX;
    private double positionY;
    private double bulletAngle;

    public Bullet(double positionX, double positionY, double bulletAngle) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.bulletAngle = bulletAngle;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getBulletAngle() {
        return bulletAngle;
    }

    public void setBulletAngle(double bulletAngle) {
        this.bulletAngle = bulletAngle;
    }
}
