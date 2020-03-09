package com.tankilla.model;

public class Board {
    private Tank redTank;
    private Tank greenTank;

    public Board() {
        redTank = new Tank(100,200.0, 597, 0);
        greenTank = new Tank(100,1200, 597, 180);
    }

    public Tank getRedTank() {
        return redTank;
    }

    public Tank getGreenTank() {
        return greenTank;
    }
}
