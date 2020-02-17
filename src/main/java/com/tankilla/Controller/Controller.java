package com.tankilla.controller;

import com.tankilla.model.Bullet;
import com.tankilla.model.GameState;
import com.tankilla.model.Tank;
import com.tankilla.view.MainView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

    private static GameState state;
    private boolean barrelUp, barrelDown, right, left, resume, start, pause;
    private static boolean bulletWasFired;
    private boolean keyActive;
    private int dx;
    private int dy;
    private boolean firstPlayerTurn;
    private long lastNanoTime = System.nanoTime();
    private double t;

    private Tank redTank;
    private Tank greenTank;
    private MainView view;
    private Bullet bullet;

    public static boolean isBulletWasFired() {
        return bulletWasFired;
    }

    public Controller() {
        state = GameState.STARTED;
        firstPlayerTurn = true;
        view = new MainView();
        redTank = view.getRedTank();
        greenTank = view.getGreenTank();
        bullet = view.getBullet();
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
                redTank.setBarrelMovement(dy);
                greenTank.setBarrelMovement(dy);

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
                    keyActive = true;
                }
                if(!firstPlayerTurn && state == GameState.RUNNING) {
                    move(greenTank, dx, dy);
                    keyActive = true;
                }

                // when player fired a bullet
                if(firstPlayerTurn && state == GameState.BULLET_FIRED) {
                    fire(redTank);
                    keyActive = true;
                }
                if(!firstPlayerTurn && state == GameState.BULLET_FIRED) {
                    fire(greenTank);
                    keyActive = true;
                }

                view.render(); // rendering the scene
                movement(view.getScene()); // handling user key input on actual scene
            }
        }.start(); // starting the timer
    }

    private void fire(Tank tank) {
        if(bulletWasFired) {
            bullet.setPositionX(tank.getPositionX());
            bullet.setPositionY(tank.getPositionY());
            bullet.setBulletAngle(tank.getBarrelAngle());
            System.out.println(tank.getBarrelAngle());
            t = 0;
            bulletWasFired = false;
        }
        System.out.println("bullet position: " + bullet.getPositionX() + ", " + bullet.getPositionY() + ", angle: " + bullet.getBulletAngle() + ", " + Math.sin(-tank.getBarrelAngle()) + ", t: " + t);

        if(bullet.getPositionY() < 680 && bullet.getPositionY() > 200) {

            bullet.setPositionX( (int) (bullet.getPositionX() + Math.cos(-tank.getBarrelAngle()) - 1/t) );
            bullet.setPositionY( (int) (bullet.getPositionY() + Math.sin(-tank.getBarrelAngle()) -10/t) );
            t++;
            System.out.println("bullet position: " + bullet.getPositionX() + ", " + bullet.getPositionY() + ", angle: " + bullet.getBulletAngle() + ", " + Math.sin(-tank.getBarrelAngle()) + ", t: " + t);

        } else {
            System.out.println("done!");
            state = GameState.RUNNING;
        }
    }

    private void move(Tank tank, int dx, int dy) {
        if(dx != 0 || dy != 0) {
            tank.setPositionX(dx);
            tank.setBarrelMovement(dy);
            System.out.println(tank.getBarrelMovement() + ", " + tank.getBarrelAngle());
        }
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
                        state = GameState.BULLET_FIRED;
                        bulletWasFired = true;
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
