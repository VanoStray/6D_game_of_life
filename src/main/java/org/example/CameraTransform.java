package org.example;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

import java.security.Key;
import java.util.HashSet;
import java.util.Set;

public class CameraTransform {

    private double speedMovement = 20.0;
    private double speedRotation = 0.05;

    private double cameraDeltaX = 1000;
    private double cameraDeltaY = 5500;
    private double cameraDeltaZ = -2500;

    private double cameraRotationX = 53;
    private double cameraRotationY = 0;

    private Rotate rotateCameraX;
    private Rotate rotateCameraY;

    private double initialMouseX;
    private double initialMouseY;
    private Set<KeyCode> pressedKeys = new HashSet<>();
    public Camera camera = new PerspectiveCamera(true);

    private CubeVisibilityHandler cubeVisibilityHandler;
    private LogicGameOfLive logicGameOfLive;

    private double percentMinNeighbors;
    private double percentMaxNeighbors;

    public CameraTransform(CubeVisibilityHandler cubeVisibilityHandler, LogicGameOfLive logicGameOfLive,  double percentMinNeighbors, double percentMaxNeighbors) {
        camera.setFarClip(50000.0);
        camera.setTranslateX(cameraDeltaX);
        camera.setTranslateY(cameraDeltaY);
        camera.setTranslateZ(cameraDeltaZ);

        rotateCameraX = new Rotate(cameraRotationX, Rotate.X_AXIS);
        rotateCameraY = new Rotate(cameraRotationY, Rotate.Y_AXIS);
        camera.getTransforms().addAll(rotateCameraX, rotateCameraY);
        this.cubeVisibilityHandler = cubeVisibilityHandler;
        this.percentMinNeighbors = percentMinNeighbors;
        this.percentMaxNeighbors = percentMaxNeighbors;
        this.logicGameOfLive = logicGameOfLive;
    }

    public void UpdateTransform(Scene scene){
        // Обработчик событий клавиатуры
        scene.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
            handleKeyPress(pressedKeys, (PerspectiveCamera) camera);
        });
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        // Обработчик событий мыши
        scene.setOnMousePressed(event -> {
            initialMouseX = event.getSceneX();
            initialMouseY = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> handleMouseDrag(event, (PerspectiveCamera) camera, rotateCameraX, rotateCameraY));
    }

    private void handleKeyPress(Set<KeyCode> pressedKeys , PerspectiveCamera camera) {
        if (pressedKeys.contains(KeyCode.W)) {
            cameraDeltaY -= speedMovement;
        }
        if (pressedKeys.contains(KeyCode.S)) {
            cameraDeltaY += speedMovement;
        }
        if (pressedKeys.contains(KeyCode.A)) {
            cameraDeltaX -= speedMovement;
        }
        if (pressedKeys.contains(KeyCode.D)) {
            cameraDeltaX += speedMovement;
        }
        if (pressedKeys.contains(KeyCode.Q)) {
            cameraDeltaZ -= speedMovement;
        }
        if (pressedKeys.contains(KeyCode.E)) {
            cameraDeltaZ += speedMovement;
        }
        if (pressedKeys.contains(KeyCode.SPACE)) {
            logicGameOfLive.updateGameBoard(percentMinNeighbors, percentMaxNeighbors);
            cubeVisibilityHandler.updateCubesVisibility(logicGameOfLive.gameBoardVizual());
        }
        if (pressedKeys.contains(KeyCode.R)) {
            logicGameOfLive.randomGameBoard();
            cubeVisibilityHandler.updateCubesVisibility(logicGameOfLive.gameBoardVizual());
        }
        if(pressedKeys.contains(KeyCode.T)){
            logicGameOfLive = new LogicGameOfLive(logicGameOfLive.size, 1, logicGameOfLive.delta3DgameBordes);
            cubeVisibilityHandler.updateCubesVisibility(logicGameOfLive.gameBoardVizual());
        }

        camera.setTranslateX(cameraDeltaX);
        camera.setTranslateY(cameraDeltaY);
        camera.setTranslateZ(cameraDeltaZ);
    }

    private void handleMouseDrag(MouseEvent event, PerspectiveCamera camera, Rotate rotateCameraX, Rotate rotateCameraY) {
        double deltaX = event.getSceneX() - initialMouseX;
        double deltaY = event.getSceneY() - initialMouseY;

        // Обновление углов поворота
        cameraRotationX -= deltaY * speedRotation; // Поворот по X
        cameraRotationY += deltaX * speedRotation; // Поворот по Y

        // Применение вращения
        rotateCameraX.setAngle(cameraRotationX);
        rotateCameraY.setAngle(cameraRotationY);

        // Обновляем начальные координаты мыши
        initialMouseX = event.getSceneX();
        initialMouseY = event.getSceneY();
    }
}
