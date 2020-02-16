package com.tankilla.Controller;

import com.tankilla.Model.*;
import com.tankilla.View.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller {

    private static GameState state;
    private boolean barrelUp, barrelDown, right, left, resume, start, pause;
    private static boolean playerFired;
    private boolean keyActive;
    private int dx;
    private int dy;
    private boolean firstPlayerTurn;
    private long lastNanoTime = System.nanoTime();

    private Tank redTank;
    private Tank greenTank;
    private MainView view;
    private Board board;
    private Bullet bullet;

    public static boolean isPlayerFired() {
        return playerFired;
    }

    public Controller() {
        state = GameState.STARTED;
        firstPlayerTurn = true;
        view = new MainView();
        redTank = view.getRedTank();
        greenTank = view.getGreenTank();
        bullet = view.getBullet();
        //board = view.getBoard();
        keyActive = true;
        resume();
    }

    private void resume(){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                // first or second player turn
                if(now > lastNanoTime + 25000000000L) {
                    firstPlayerTurn ^= true;
                    lastNanoTime = now;
                }

                dx = dy = 0;
                redTank.setBarrelAngle(dy);
                greenTank.setBarrelAngle(dy);

                // when movement or angle change
                if(barrelUp) dy = -1;
                if(barrelDown) dy = 1;

                if(left) dx = -1;
                if(right) dx = 1;

                // when game resumed
                if(resume && !pause) {
                    state = GameState.RUNNING;
                    resume = false;
                }
                // when game started or restarted
                if(start && (state == GameState.FINISHED || state == GameState.STARTED)) {
                    restart();
                    start = false;
                }
                // when game finished
                if(state == GameState.FINISHED) {
                    stop();
                }
                // when game is running, make movement
                if(firstPlayerTurn && state == GameState.RUNNING) {
                    move(redTank, dx, dy);
                    keyActive = true; // unlock possibility to press another key after tank made it's move
                }
                if(!firstPlayerTurn && state == GameState.RUNNING) {
                    move(greenTank, dx, dy);
                    keyActive = true; // unlock possibility to press another key after tank made it's move
                }

                // when player fired a bullet
                if(firstPlayerTurn && state == GameState.RUNNING && playerFired) {
                    //fire(redTank);
                    //System.out.println("first player fire");
                    playerFired = true;
                    bullet.setPositionX(redTank.getPositionX());
                    bullet.setPositionY(redTank.getPositionY());
                    keyActive = true;
                }
                if(!firstPlayerTurn && state == GameState.RUNNING && playerFired) {
                    //fire(greenTank);

                    playerFired = true;
                    bullet.setPositionX(greenTank.getPositionX());
                    bullet.setPositionY(greenTank.getPositionY());
                    keyActive = true;
                }

                view.render(); // rendering the scene
                movement(view.getScene()); // handling user key input on actual scene
            }
        }.start(); // starting the timer
    }

    private void move(Tank tank, int dx, int dy) {
        if(dx != 0 || dy != 0) {
            tank.setPositionX(dx);
            tank.setBarrelAngle(dy);
        }
    }

    private void fire(Tank tank) {
        playerFired = true;
        bullet.setPositionX(tank.getPositionX());
        bullet.setPositionY(tank.getPositionY());
        bullet.fireBullet();

        System.out.println("bullet: " + bullet.getPositionX() + ", " + bullet.getPositionY());

        System.out.println("bullet fired ");
        playerFired = false;
    }

    private void movement(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case UP:
                    if(keyActive && state == GameState.RUNNING) {
                        barrelUp = true;
                        keyActive = false;
                    }
                    break;
                case DOWN:
                    if(keyActive && state == GameState.RUNNING) {
                        barrelDown = true;
                        keyActive = false;
                    }
                    break;
                case LEFT:
                    if(keyActive && state == GameState.RUNNING) {
                        left = true;
                        keyActive = false;
                    }
                    break;
                case RIGHT:
                    if(keyActive && state == GameState.RUNNING) {
                        right = true;
                        keyActive = false;
                    }
                    break;
                case SPACE: {
                    if (keyActive && state == GameState.RUNNING) {
                        playerFired = true;
                        keyActive = false;
                    }
                }
                    break;

                case ENTER:{ // start or restart the game
                    if(state == GameState.STARTED)
                        start = true;
                    if(state == GameState.FINISHED) {
                        start = true;
                        resume();
                    }
                }
                    break;
                case ESCAPE: // exit program
                    System.exit(0);
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT: left = false; break;
                case RIGHT: right = false; break;
                case UP: barrelUp = false; break;
                case DOWN: barrelDown = false; break;
            }
        });
    }

    private void restart() {
        state = GameState.RUNNING;
        dx = dy = 0;
        barrelUp = barrelDown = left = right = false;
    }

    public static GameState getState() { return state; }

    public Stage getStage() { return view.getStage(); }

}
