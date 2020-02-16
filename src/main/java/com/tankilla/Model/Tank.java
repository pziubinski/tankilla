package com.tankilla.Model;

public class Tank {

    public static final int WIDTH = 155;
    public static final int HEIGHT = 86;

    private int power;
    private String team;
    private int positionX;
    private int positionY;
    private int barrelAngle;

    public Tank(int power, String team, int positionX, int positionY, int barrelAngle) {
        this.power = power;
        this.team = team;
        this.positionX = positionX;
        this.positionY = positionY;
        this.barrelAngle = barrelAngle;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX += positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY += positionY;
    }

    public int getBarrelAngle() { return barrelAngle; }

    public void setBarrelAngle(int barrelAngle) {

        this.barrelAngle = barrelAngle;

        if(this.barrelAngle > 360)
            this.barrelAngle -= 360;

        if(this.barrelAngle < 0)
            this.barrelAngle += 360;
    }
}
