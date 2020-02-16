package com.tankilla.Model;

public class Bullet {

    private int positionX;
    private int positionY;
    private int bulletAngle;
    private boolean isFired;

    public Bullet(int positionX, int positionY, int bulletAngle) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.bulletAngle = bulletAngle;
        this.isFired = true;
    }

    public Bullet() {

    }

    public void fireBullet() {
        // just mockup without math
        int i = 0;
        while (i < 100) {
            this.positionX -= 1;
            this.positionY -= 1;
            i++;
        }
        System.out.println("bullet fired in bullet class");
        isFired = false;
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

    public boolean isFired() {
        return isFired;
    }
}
