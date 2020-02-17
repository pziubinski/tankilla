package com.tankilla.model;

public class Tank {

    public static final int WIDTH = 155;
    public static final int HEIGHT = 86;

    private int power;
    private String team;
    private int positionX;
    private int positionY;
    private int barrelMovement;
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

    public int getBarrelMovement() {
        return barrelMovement;
    }

    public void setBarrelMovement(int barrelMovement) {
        this.barrelMovement = barrelMovement;
        this.barrelAngle += barrelMovement;
    }

    public int getBarrelAngle() { return barrelAngle; }

    public void setBarrelAngle(int barrelAngle) {


    }


}
