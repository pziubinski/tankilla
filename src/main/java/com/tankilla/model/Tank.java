package com.tankilla.model;

public class Tank {

    private int power;
    private double positionX;
    private double positionY;
    private double barrelMovement;
    private double barrelAngle;

    public Tank(int power, double positionX, double positionY, double barrelAngle) {
        this.power = power;
        this.positionX = positionX;
        this.positionY = positionY;
        this.barrelAngle = barrelAngle;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
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

    public void setBarrelMovement(double barrelMovement) {
        this.barrelMovement = barrelMovement;
        this.barrelAngle += barrelMovement;
    }

    public double getBarrelAngle() {
        return barrelAngle;
    }
}
