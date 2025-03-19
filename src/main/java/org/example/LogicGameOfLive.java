package org.example;

import java.util.Random;

public class LogicGameOfLive {

    private Random random = new Random();
    private boolean[][][][][][] gameBoard;
    private boolean[][][][][][] gameBoardUpdate;
    public int size;
    public int dimensions;
    public int delta3DgameBordes;
    private int[] sizeInDimensions = new int[6];
    private int[] deltaNeighbors = new int[6];

    public LogicGameOfLive(int size, int dimensions, int delta3DgameBordes) {
        this.size = size;
        this.delta3DgameBordes = delta3DgameBordes;
        if(dimensions <= 6 && dimensions > 0){
            this.dimensions = dimensions;
        }
        else {
            this.dimensions = 6;
        }
        createGameBoard();
    }

    public boolean[][][] gameBoardVizual(){
        boolean[][][] gameBoardVizual = new boolean[sizeGameBoardVizual()[2]][sizeGameBoardVizual()[1]][sizeGameBoardVizual()[0]];

        for(int a = 0; a < sizeInDimensions[5]; a++){
            for(int b = 0; b < sizeInDimensions[4]; b++){
                for(int c = 0; c < sizeInDimensions[3]; c++){
                    for(int d = 0; d < sizeInDimensions[2]; d++){
                        for(int e = 0; e < sizeInDimensions[1]; e++){
                            for(int f = 0; f < sizeInDimensions[0]; f++){
                                gameBoardVizual[d + (c * (size + delta3DgameBordes))][e + (b * (size + delta3DgameBordes))][f + (a * (size + delta3DgameBordes))] = gameBoard[a][b][c][d][e][f];
                            }
                        }
                    }
                }
            }
        }
        return gameBoardVizual;
    }

    public int[] sizeGameBoardVizual(){
        int[] sizes = new int[3];

        switch (dimensions){
            case 6:
                sizes[2] = (size + delta3DgameBordes) * size;
                sizes[1] = (size + delta3DgameBordes) * size;
                sizes[0] = (size + delta3DgameBordes) * size;
                break;
            case 5:
                sizes[2] = (size + delta3DgameBordes) * size;
                sizes[1] = (size + delta3DgameBordes) * size;
                sizes[0] = size;
                break;
            case 4:
                sizes[2] = (size + delta3DgameBordes) * size;
                sizes[1] = size;
                sizes[0] = size;
                break;
            case 3:
                sizes[2] = size;
                sizes[1] = size;
                sizes[0] = size;
                break;
            case 2:
                sizes[2] = 1;
                sizes[1] = size;
                sizes[0] = size;
                break;
            case 1:
                sizes[2] = 1;
                sizes[1] = 1;
                sizes[0] = size;
                break;
        }
        return sizes;
    }

    private void createGameBoard(){
        if (dimensions <= 6 && dimensions > 0){
            for (int i = 0; i < dimensions; i++){
                sizeInDimensions[i] = size;
                deltaNeighbors[i] = 3;
            }
            for (int i = dimensions; i < 6; i++){
                sizeInDimensions[i] = 1;
                deltaNeighbors[i] = 1;
            }
            gameBoard = new boolean[sizeInDimensions[5]][sizeInDimensions[4]][sizeInDimensions[3]][sizeInDimensions[2]][sizeInDimensions[1]][sizeInDimensions[0]];
            gameBoardUpdate = new boolean[sizeInDimensions[5]][sizeInDimensions[4]][sizeInDimensions[3]][sizeInDimensions[2]][sizeInDimensions[1]][sizeInDimensions[0]];
        }
    }

    public void randomGameBoard(){
        for(int a = 0; a < sizeInDimensions[5]; a++){
            for(int b = 0; b < sizeInDimensions[4]; b++){
                for(int c = 0; c < sizeInDimensions[3]; c++){
                    for(int d = 0; d < sizeInDimensions[2]; d++){
                        for(int e = 0; e < sizeInDimensions[1]; e++){
                            for(int f = 0; f < sizeInDimensions[0]; f++){
                                gameBoard[a][b][c][d][e][f] = random.nextBoolean();
                            }
                        }
                    }
                }
            }
        }
    }


    public void updateGameBoard(double divisorMinNeighbors, double divisorMaxNeighbors){
        for(int a = 0; a < sizeInDimensions[5]; a++){
            for(int b = 0; b < sizeInDimensions[4]; b++){
                for(int c = 0; c < sizeInDimensions[3]; c++){
                    for(int d = 0; d < sizeInDimensions[2]; d++){
                        for(int e = 0; e < sizeInDimensions[1]; e++){
                            for(int f = 0; f < sizeInDimensions[0]; f++){
                                gameBoardUpdate[a][b][c][d][e][f] = updateCell(a, b, c, d, e, f, divisorMinNeighbors, divisorMaxNeighbors);
                            }
                        }
                    }
                }
            }
        }
        for(int a = 0; a < sizeInDimensions[5]; a++){
            for(int b = 0; b < sizeInDimensions[4]; b++){
                for(int c = 0; c < sizeInDimensions[3]; c++){
                    for(int d = 0; d < sizeInDimensions[2]; d++){
                        for(int e = 0; e < sizeInDimensions[1]; e++){
                            for(int f = 0; f < sizeInDimensions[0]; f++){
                                gameBoard[a][b][c][d][e][f] = gameBoardUpdate[a][b][c][d][e][f];
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean updateCell(int a, int b, int c, int d, int e, int f, double divisorMinNeighbors, double divisorMaxNeighbors){
        int countLiveNeighbors = 0;
        int countDieNeighbors = 0;
        int countAllNeighbours = 0;
        int[] counters;

        for(int a1 = 0; a1 < deltaNeighbors[5]; a1++){
            for(int b1 = 0; b1 < deltaNeighbors[4]; b1++){
                for(int c1 = 0; c1 < deltaNeighbors[3]; c1++){
                    for(int d1 = 0; d1 < deltaNeighbors[2]; d1++){
                        for(int e1 = 0; e1 < deltaNeighbors[1]; e1++){
                            for(int f1 = 0; f1 < deltaNeighbors[0]; f1++){
                                counters = formatCounters(a, b, c, d, e, f, a1, b1, c1, d1, e1, f1);
                                if (counters != null) {
                                    if(counters[5] >= 0 && counters[5] < sizeInDimensions[5] &&
                                            counters[4] >= 0 && counters[4] < sizeInDimensions[4] &&
                                            counters[3] >= 0 && counters[3] < sizeInDimensions[3] &&
                                            counters[2] >= 0 && counters[2] < sizeInDimensions[2] &&
                                            counters[1] >= 0 && counters[1] < sizeInDimensions[1] &&
                                            counters[0] >= 0 && counters[0] < sizeInDimensions[0]){
                                        if(gameBoard[counters[5]][counters[4]][counters[3]][counters[2]][counters[1]][counters[0]]){
                                            countLiveNeighbors++;
                                        }
                                        else {
                                            countDieNeighbors++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        countAllNeighbours = countDieNeighbors + countLiveNeighbors;
        int minNeighbors = (int) (countAllNeighbours/(100.0 /divisorMinNeighbors));
        int maxNeighbors = (int) (countAllNeighbours/(100.0 / divisorMaxNeighbors));

        return !(countLiveNeighbors > maxNeighbors || countLiveNeighbors < minNeighbors);
    }

    private int[] formatCounters(int a, int b, int c, int d, int e, int f, int a1, int b1, int c1, int d1, int e1, int f1){
        int[] counters = new int[6];

        if(a1 + b1 + c1 + d1 + e1 + f1 == 0){
            return null;
        }
        else {
            if(deltaNeighbors[5] == 3){
                counters[5] = a - 1 + a1;
            }
            else{
                counters[5] = 0;
            }
            if(deltaNeighbors[4] == 3){
                counters[4] = b - 1 + b1;
            }
            else{
                counters[4] = 0;
            }
            if(deltaNeighbors[3] == 3){
                counters[3] = c - 1 + c1;
            }
            else{
                counters[3] = 0;
            }
            if(deltaNeighbors[2] == 3){
                counters[2] = d - 1 + d1;
            }
            else{
                counters[2] = 0;
            }
            if(deltaNeighbors[1] == 3){
                counters[1] = e - 1 + e1;
            }
            else{
                counters[1] = 0;
            }
            if(deltaNeighbors[0] == 3){
                counters[0] = f - 1 + f1;
            }
            else{
                counters[0] = 0;
            }

            return counters;
        }
    }
}
