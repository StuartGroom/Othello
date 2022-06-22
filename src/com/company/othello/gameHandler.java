package com.company.othello;

import java.util.ArrayList;


public class gameHandler {

    public static int[][] directions = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

    //placing tiles
    public static int[] pixelToCoord(int xPre, int yPre){
        int x = (xPre/100);   //convert from pixels to coordinate
        int y = (yPre-124)/100;  //need to make more accurate
        return new int[]{x,y};
    }

    public static boolean legalMoveChecker(int x, int y, char[][] grid, boolean blackTurn) {

        boolean validMove = false;
        char mainDisc;
        char oppDisc;

        if (blackTurn) { //black turn
            mainDisc = 'b';
            oppDisc = 'w';
        }
        else {
            mainDisc = 'w';
            oppDisc = 'b';
        }

        if (grid[x][y] == ' ') {   //selected tile is empty
            for (int d = 0; d < 8; d++) {//loop through all directions
                int runLength = 1;  //reset runLength each time we change direction

                while (x + ((runLength) * directions[d][0]) >= 0 && x + ((runLength) * directions[d][0]) <= 7 && y + ((runLength) * directions[d][1]) >= 0  && y + ((runLength) * directions[d][1]) <= 7) {
                    //while we're in bounds
                    if (grid[x + (runLength * directions[d][0])][y + (runLength * directions[d][1])] == oppDisc) {
                        //theres an opposite disc in that direction
                        runLength++; //increment runLength
                    } else{   //no run in that direction
                        break;
                    }
                    //if run++ is in grid and is main disk
                    if (x + (runLength * directions[d][0]) >= 0 && x + (runLength * directions[d][0]) <= 7 && y + (runLength * directions[d][1]) >= 0  && y + (runLength * directions[d][1]) <= 7) {
                        if (grid[x + (runLength * directions[d][0])][y + (runLength * directions[d][1])] == mainDisc) {
                            //if theres a opposite disc in that direction followed by a disc of your colour
                            validMove = true;
                            break;
                        }
                    }
                    else{ //if no longer on board then break
                        break;
                    }
                }
            }
        }
        return validMove;
    }

    public static char[][] flipDiscs(char mainDisc, char oppDisc, int x, int y, char[][] grid) {

        int runLength;
        ArrayList<int[]> flipDiscs = new ArrayList<>() ;

        for (int d = 0; d < 8; d++) {
            runLength = 0;

            while (x + ((runLength+1) * directions[d][0]) >= 0 && x + ((runLength+1) * directions[d][0]) <= 7 && y + ((runLength+1) * directions[d][1]) >= 0 && y + ((runLength+1) * directions[d][1]) <= 7) {
                //theres an opposite disc in that direction
                if (grid[x + ((runLength + 1) * directions[d][0])][y + ((runLength + 1) * directions[d][1])] == oppDisc){

                    runLength++; //increment runLength

                    int xFlip = x + (runLength * directions[d][0]);
                    int yFlip = y + (runLength * directions[d][1]);

                    flipDiscs.add(new int[] {xFlip, yFlip});
                }
                else {
                    //clear flipDisc array as we go onto next direction
                    break;
                }
                if (x + ((runLength+1) * directions[d][0]) >= 0 && x + ((runLength+1) * directions[d][0]) <= 7 && y + ((runLength+1) * directions[d][1]) >= 0 && y + ((runLength+1) * directions[d][1]) <= 7) {
                    if (grid[x + ((runLength + 1) * directions[d][0])][ y + ((runLength + 1) * directions[d][1])] == mainDisc) {


                        for (int[] disc : flipDiscs) {
                            if (grid[disc[0]][disc[1]] == oppDisc){
                                grid[disc[0]][disc[1]] = mainDisc;
                            }
                        }
                    }

                }
            }
            flipDiscs.clear();
        }
        return grid;
    }

    public static ArrayList<int[]> potentialMovesList(char[][] Board, boolean blackTurn){

        ArrayList<int[]> potentialMoves = new ArrayList<>() ;

        for (int i = 0; i <= 7; i++){   //iterate over all tiles on board
            for (int j = 0; j <= 7; j++){
                if (legalMoveChecker(i, j, Board, blackTurn)){ //it is a legal move
                    potentialMoves.add(new int[] {i, j});
                }
            }
        }

        //output legal moves list
//        System.out.println("\n-----legal moves moves-----");
//        for (int[] i : potentialMoves) {
//            System.out.println(i[0] + " " + i[1]);
//        }
//        System.out.println("-------------------------");

        return potentialMoves;
    }


}




