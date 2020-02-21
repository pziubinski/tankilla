package com.tankilla.model;

public class Tank {

    public static final int WIDTH = 155;
    public static final int HEIGHT = 86;

    private int power;
    private String team;
    private double positionX;
    private double positionY;
    private double barrelMovement;
    private double barrelAngle;

    public Tank(int power, String team, double positionX, double positionY, double barrelAngle) {
        this.power = power;
        this.team = team;
        this.positionX = positionX;
        this.positionY = positionY;
        this.barrelAngle = barrelAngle;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX += positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getBarrelMovement() {
        return barrelMovement;
    }

    public void setBarrelMovement(double barrelMovement) {
        this.barrelMovement = barrelMovement;
        this.barrelAngle += barrelMovement;
    }

    public double getBarrelAngle() { return barrelAngle; }

    public void setBarrelAngle(double barrelAngle) {


    }


}
