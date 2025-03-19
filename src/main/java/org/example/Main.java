package org.example;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {

    private int size = 6;
    private int dimensions = 6;
    private int delta3DgameBordes = 3; //При визуализации с мерностью больше 3D, это поле отвечает за расстояние между блоками (не между кубами)
    private double percentMinNeighbors = 20;
    private double percentMaxNeighbors = 45;

    @Override
    public void start(Stage primaryStage) {
        LogicGameOfLive logicGameOfLive = new LogicGameOfLive(size, dimensions, delta3DgameBordes);
        logicGameOfLive.randomGameBoard();

        int[] sizes = logicGameOfLive.sizeGameBoardVizual();
        CubeVisibilityHandler cubeVisibilityHandler = new CubeVisibilityHandler(sizes[2], sizes[1], sizes[0]);

        cubeVisibilityHandler.updateCubesVisibility(logicGameOfLive.gameBoardVizual());

        Scene scene = new Scene(cubeVisibilityHandler.initGroup(), 1920, 1080, true);
        CameraTransform cameraTransform = new CameraTransform(cubeVisibilityHandler,logicGameOfLive, percentMinNeighbors, percentMaxNeighbors);
        scene.setCamera(cameraTransform.camera);

        cameraTransform.UpdateTransform(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
