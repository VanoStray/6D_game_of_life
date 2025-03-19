package org.example;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class CubeVisibilityHandler {
    private Box[][][] cubes;
    private int sizeX, sizeY, sizeZ;
    private final double cubeSize = 100;

    public CubeVisibilityHandler(int sizeX, int sizeY, int sizeZ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.cubes = new Box[sizeX][sizeY][sizeZ];
        createCubes();
    }

    private void createCubes() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    Box cube = new Box(cubeSize, cubeSize, cubeSize);
                    cube.setMaterial(new PhongMaterial(Color.DARKBLUE));
                    cube.setTranslateX(x * cubeSize);
                    cube.setTranslateY(y * cubeSize);
                    cube.setTranslateZ(z * cubeSize);
                    cubes[x][y][z] = cube;
                }
            }
        }
    }

    public void updateCubesVisibility(boolean[][][] visibilityArray) {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    if (visibilityArray[x][y][z]) {
                        cubes[x][y][z].setVisible(true);
                    } else {
                        cubes[x][y][z].setVisible(false);
                    }
                }
            }
        }
    }

    private Box[][][] getCubes() {
        return cubes;
    }

    public Group initGroup(){
        Group group = new Group();

        for (Box[][] layer : getCubes()) {
            for (Box[] row : layer) {
                for (Box cube : row) {
                    group.getChildren().add(cube);
                }
            }
        }
        return group;
    }
}
