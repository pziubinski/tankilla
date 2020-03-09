package com.tankilla.controller;

import com.tankilla.model.Board;
import com.tankilla.model.Bullet;
import com.tankilla.model.GameState;
import com.tankilla.model.Tank;
import com.tankilla.view.MainView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
    private static GameState state;
    private boolean barrelUp, barrelDown, right, left;
    private static boolean bulletWasFired;
    private boolean keyActive;
    private int dx;
    private int dy;
    private double inRadians;
    private long timer;
    private static String whoWins;

    private static Tank redTank;
    private static Tank greenTank;
    private MainView view;
    private static Bullet bullet;
    private static Board board;

    public Controller() {
        state = GameState.STARTED;
        board = new Board();
        redTank = board.getRedTank();
        greenTank = board.getGreenTank();
        bullet = new Bullet(0, 0);
        keyActive = true;
        view = new MainView();

        resume();
    }

    private void resume(){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                dx = dy = 0;
                redTank.setBarrelMovement(dy);
                greenTank.setBarrelMovement(dy);

                // when movement or angle change
                if(barrelUp) dy = -1;
                if(barrelDown) dy = 1;

                if(left) dx = -1;
                if(right) dx = 1;

                if(state == GameState.FINISHED) {
                    restart();
                }

                if(state == GameState.RED_PLAYER_TURN) {
                    move(redTank, dx, dy);
                    keyActive = true;
                }
                if(state == GameState.GREEN_PLAYER_TURN) {
                    move(greenTank, dx, dy);
                    keyActive = true;
                }

                if(state == GameState.RED_BULLET_FIRED) {
                    fire(redTank);
                    keyActive = true;
                }
                if(state == GameState.GREEN_BULLET_FIRED) {
                    fire(greenTank);
                    keyActive = true;
                }

                view.render();
                movement(view.getScene()); // handling user key input on actual scene
            }
        }.start();
    }

    private void fire(Tank tank) {
        if(bulletWasFired) {
            inRadians = Math.toRadians(tank.getBarrelAngle());
            bullet.setPositionX(tank.getPositionX()+68 + Math.cos(inRadians)*70);
            bullet.setPositionY(tank.getPositionY()+12 + Math.sin(inRadians)*70);

            timer = System.currentTimeMillis();
            bulletWasFired = false;
        }

        long tick;

        if(bullet.getPositionY() < 670 && bullet.getPositionY() > 0 && bullet.getPositionX() > 0 && bullet.getPositionX() < 1600) {
            tick = System.currentTimeMillis();
            long time = (tick - timer)/70;
            inRadians = Math.toRadians(tank.getBarrelAngle());
            bullet.setPositionX( bullet.getPositionX() + 2 * time * Math.cos(inRadians) );
            bullet.setPositionY( bullet.getPositionY() + time * Math.sin(inRadians) * 2 + (time * time)/10 );
        }
        else
        {
            if(state == GameState.GREEN_BULLET_FIRED) {
                double xdiff = (bullet.getPositionX() - redTank.getPositionX()) * (bullet.getPositionX() - redTank.getPositionX());
                double ydiff = (bullet.getPositionY() - redTank.getPositionY()) * (bullet.getPositionY() - redTank.getPositionY());
                double distance = Math.sqrt(xdiff + ydiff);
                double decreasePower = 100 - 0.2 * distance;

                if(decreasePower > 0)
                    redTank.setPower(redTank.getPower() - (int) decreasePower);

                if(redTank.getPower() <= 0) {
                    whoWins = "GREEN PLAYER WIN THIS BATTLE!";
                    state = GameState.FINISHED;
                }
                else {
                    state = GameState.RED_PLAYER_TURN;
                }
            }

            if(state == GameState.RED_BULLET_FIRED) {
                double xdiff = (bullet.getPositionX() - greenTank.getPositionX()) * (bullet.getPositionX() - greenTank.getPositionX());
                double ydiff = (bullet.getPositionY() - greenTank.getPositionY()) * (bullet.getPositionY() - greenTank.getPositionY());
                double distance = Math.sqrt(xdiff + ydiff);
                double decreasePower = 100 - 0.2 * distance;

                if(decreasePower > 0)
                    greenTank.setPower(greenTank.getPower() - (int) decreasePower);

                if(greenTank.getPower() <= 0) {
                    whoWins = "RED PLAYER WIN THIS BATTLE!";
                    state = GameState.FINISHED;
                }
                else {
                    state = GameState.GREEN_PLAYER_TURN;
                }
            }
        }
    }

    private void move(Tank tank, int dx, int dy) {
        if(dx != 0 || dy != 0) {
            tank.setPositionX(dx);
            tank.setBarrelMovement(dy);
        }
    }

    private void movement(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case UP:
                    if(keyActive && state == GameState.RED_PLAYER_TURN || state == GameState.GREEN_PLAYER_TURN) {
                        barrelUp = true;
                        keyActive = false;
                    }
                    break;
                case DOWN:
                    if(keyActive && state == GameState.RED_PLAYER_TURN || state == GameState.GREEN_PLAYER_TURN) {
                        barrelDown = true;
                        keyActive = false;
                    }
                    break;
                case LEFT:
                    if(keyActive && state == GameState.RED_PLAYER_TURN || state == GameState.GREEN_PLAYER_TURN) {
                        left = true;
                        keyActive = false;
                    }
                    break;
                case RIGHT:
                    if(keyActive && state == GameState.RED_PLAYER_TURN || state == GameState.GREEN_PLAYER_TURN) {
                        right = true;
                        keyActive = false;
                    }
                    break;
                case SPACE: {
                    if (keyActive && state == GameState.RED_PLAYER_TURN) {
                        state = GameState.RED_BULLET_FIRED;
                        bulletWasFired = true;
                        keyActive = false;
                    }
                    else if (keyActive && state == GameState.GREEN_PLAYER_TURN) {
                        state = GameState.GREEN_BULLET_FIRED;
                        bulletWasFired = true;
                        keyActive = false;
                    }
                }
                    break;
                case ENTER: { // start or restart the game
                    if(state == GameState.STARTED) {
                        restart();
                        state = GameState.RED_PLAYER_TURN;
                    }
                    if(state == GameState.FINISHED) {
                        state = GameState.STARTED;
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
        dx = dy = 0;
        barrelUp = barrelDown = left = right = false;

        board = new Board();
        redTank = board.getRedTank();
        greenTank = board.getGreenTank();

        System.out.println("restart + barrel: " + redTank.getBarrelAngle());
        state = GameState.STARTED;
    }

    public static GameState getState() { return state; }

    public Stage getStage() { return view.getStage(); }

    public static String getWinner() { return whoWins; }

    public static Tank getRedTank() {
        return redTank;
    }

    public static Tank getGreenTank() {
        return greenTank;
    }

    public static Bullet getBullet() {
        return bullet;
    }
}
