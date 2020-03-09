package com.tankilla.view;

import com.tankilla.controller.Controller;
import com.tankilla.model.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class MainView {

    public final static int WIDTH = 1600;
    public final static int HEIGHT = 900;

    private Image backgroundView = new Image("file:src/main/resources/background.png");
    private ImageView backgroundImageView = new ImageView(backgroundView);

    private Image redTankImage = new Image("file:src/main/resources/tank_red_small.png");
    private ImageView redTankImageView = new ImageView(redTankImage);

    private Image redBarrelImage = new Image("file:src/main/resources/barrel_red_small.png");
    private ImageView redBarrelImageView = new ImageView(redBarrelImage);

    private Image greenTankImage = new Image("file:src/main/resources/tank_green_small.png");
    private ImageView greenTankImageView = new ImageView(greenTankImage);

    private Image greenBarrelImage = new Image("file:src/main/resources/barrel_green_small.png");
    private ImageView greenBarrelImageView = new ImageView(greenBarrelImage);

    private Image bulletImage = new Image("file:src/main/resources/bullet.png");
    private ImageView bulletImageView = new ImageView(bulletImage);

    private Rotate redBarrelRotation;
    private Rotate greenBarrelRotation;

    private Scene scene;
    private Stage stage;
    private Group group;
    private Pane canvas;
    private GridPane grid;
    private StackPane stack;

    public MainView() {
        redBarrelRotation = new Rotate();
        greenBarrelRotation = new Rotate();

        stage = new Stage();
        stage.setTitle("Tankilla");

        canvas = new Pane();
        canvas.setPrefSize(WIDTH,HEIGHT);

        stack = new StackPane();
        grid = new GridPane();

        group = new Group();
        scene = new Scene(group, WIDTH, HEIGHT );

        render();
    }

    public void render() {
        GameState state = Controller.getState();

        switch(state) {
            case STARTED:
                whenStarted();
                break;
            case FINISHED:
                whenFinished();
                break;
            case RED_PLAYER_TURN:
                whenRunning();
                break;
            case GREEN_PLAYER_TURN:
                whenRunning();
                break;
            case RED_BULLET_FIRED:
                whenBulletFired();
                break;
            case GREEN_BULLET_FIRED:
                whenBulletFired();
                break;
            default:
                break;
        }
    }

    private void whenBulletFired() {
        setupAndBackgroundAndStatistics();

        bulletImageView.relocate(Controller.getBullet().getPositionX(), Controller.getBullet().getPositionY());
        canvas.getChildren().add(bulletImageView);

        tankAndGridAndSceneAndStageSetup();
    }

    private void whenRunning() {
        setupAndBackgroundAndStatistics();
        tankAndGridAndSceneAndStageSetup();
    }

    private void setupAndBackgroundAndStatistics() {
        grid.getChildren().clear(); // clear grid
        canvas.getChildren().clear(); // clear canvas
        canvas.getChildren().add(backgroundImageView);

        Text redTankPowerText = new Text(WIDTH * 0.05, 50, Integer.toString(Controller.getRedTank().getPower()));
        redTankPowerText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 36));
        redTankPowerText.setFill(Color.DARKRED);

        Text greenTankPowerText = new Text(WIDTH * 0.9, 50, Integer.toString(Controller.getGreenTank().getPower()));
        greenTankPowerText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 36));
        greenTankPowerText.setFill(Color.GREEN);

        canvas.getChildren().addAll(redTankPowerText, greenTankPowerText);
    }

    private void tankAndGridAndSceneAndStageSetup() {
        redTankImageView.relocate(Controller.getRedTank().getPositionX(), Controller.getRedTank().getPositionY());

        redBarrelRotation.setAngle(Controller.getRedTank().getBarrelAngle());
        redBarrelRotation.setPivotY(redBarrelImage.getHeight()/2);
        redBarrelImageView.getTransforms().setAll(redBarrelRotation);
        redBarrelImageView.relocate(Controller.getRedTank().getPositionX()+redBarrelImage.getWidth(), Controller.getRedTank().getPositionY()+10);

        canvas.getChildren().addAll(redBarrelImageView, redTankImageView);

        greenTankImageView.relocate(Controller.getGreenTank().getPositionX(), Controller.getGreenTank().getPositionY());

        greenBarrelRotation.setAngle(Controller.getGreenTank().getBarrelAngle()); //+- 1
        greenBarrelRotation.setPivotY(greenBarrelImage.getHeight()/2); //5
        greenBarrelImageView.getTransforms().setAll(greenBarrelRotation);
        greenBarrelImageView.relocate(Controller.getGreenTank().getPositionX()+greenBarrelImage.getWidth(), Controller.getGreenTank().getPositionY()+10);

        canvas.getChildren().addAll(greenBarrelImageView, greenTankImageView);

        grid.add(stack, 0, 1);
        grid.add(canvas, 0, 0);

        scene.setRoot(grid);
        stage.setScene(scene);
    }

    private void whenStarted() {
        group = new Group();
        Text largeText = new Text(WIDTH/2.0 - 130, HEIGHT/2.0 - 40, "TANKILLA");
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 50));
        largeText.setFill(Color.INDIANRED);
        Text smallText = new Text(WIDTH/2.0 - 130, HEIGHT/2.0 + 20 , "Press ENTER to play");
        smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        smallText.setFill(Color.DARKGREEN);
        group.getChildren().addAll(smallText, largeText);
        scene.setRoot(group);
        stage.setScene(scene);
    }

    private void whenFinished() {
        group = new Group();

        String whenFinishedString = Controller.getWinner();

        Text largeText = new Text(WIDTH/2.0 - 130, HEIGHT/2.0 - 40, "GAME OVER");
        largeText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 50));
        largeText.setFill(Color.INDIANRED);
        Text smallText = new Text(WIDTH/2.0 - 130, HEIGHT/2.0 + 20 , whenFinishedString);
        smallText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 25));
        smallText.setFill(Color.DARKGREEN);
        group.getChildren().addAll(smallText, largeText);
        scene.setRoot(group);
        stage.setScene(scene);
    }

    public Scene getScene() { return stage.getScene(); }

    public Stage getStage() { return stage; }
}
