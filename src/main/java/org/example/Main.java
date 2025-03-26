package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private int size = 6;
    private int dimensions = 4;
    private int delta3DgameBordes = 3;
    private double percentMinNeighbors = 20;
    private double percentMaxNeighbors = 45;

    @Override
    public void start(Stage primaryStage) {
        // Создание элементов управления
        TextField sizeField = new TextField(String.valueOf(size));
        TextField dimensionsField = new TextField(String.valueOf(dimensions));
        TextField deltaField = new TextField(String.valueOf(delta3DgameBordes));
        TextField minNeighborsField = new TextField(String.valueOf(percentMinNeighbors));
        TextField maxNeighborsField = new TextField(String.valueOf(percentMaxNeighbors));

        Button startButton = new Button("Запустить игру");
        startButton.setOnAction(e -> {
            // Изменение значений на основе введенных данных
            size = Integer.parseInt(sizeField.getText());
            dimensions = Integer.parseInt(dimensionsField.getText());
            delta3DgameBordes = Integer.parseInt(deltaField.getText());
            percentMinNeighbors = Double.parseDouble(minNeighborsField.getText());
            percentMaxNeighbors = Double.parseDouble(maxNeighborsField.getText());

            // Запуск логики игры
            startGame(primaryStage);
        });

        // Создание сетки для размещения элементов управления
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Размерность : "), 0, 0);
        grid.add(sizeField, 1, 0);
        grid.add(new Label("Мерность (от 1 до 6 включительно): "), 0, 1);
        grid.add(dimensionsField, 1, 1);
        grid.add(new Label("Расстояние между блоками : \n *когда измерений больше 3"), 0, 2);
        grid.add(deltaField, 1, 2);
        grid.add(new Label("Мин. соседи (%): "), 0, 3);
        grid.add(minNeighborsField, 1, 3);
        grid.add(new Label("Макс. соседи (%): "), 0, 4);
        grid.add(maxNeighborsField, 1, 4);
        grid.add(startButton, 0, 5, 2, 1);

        // Создание сцены
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Настройки игры");
        primaryStage.show();
    }

    private void startGame(Stage primaryStage) {
        LogicGameOfLive logicGameOfLive = new LogicGameOfLive(size, dimensions, delta3DgameBordes);
        logicGameOfLive.randomGameBoard();

        int[] sizes = logicGameOfLive.sizeGameBoardVizual();
        CubeVisibilityHandler cubeVisibilityHandler = new CubeVisibilityHandler(sizes[2], sizes[1], sizes[0]);
        cubeVisibilityHandler.updateCubesVisibility(logicGameOfLive.gameBoardVizual());

        Scene scene = new Scene(cubeVisibilityHandler.initGroup(), 1920, 1080, true);
        CameraTransform cameraTransform = new CameraTransform(cubeVisibilityHandler, logicGameOfLive, percentMinNeighbors, percentMaxNeighbors);
        scene.setCamera(cameraTransform.camera);
        cameraTransform.UpdateTransform(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Игра");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
