package com.company.tests;

import com.company.Main;
import com.company.othello.board;
import com.company.othello.playerMove;

public class tournament {

    public static void tournament(){
        double[] scores = new double[6];  //6 ai - no mcts yet
        double[][][] bracket = new double[6][6][2];   //this is with

        Main.depth2 = 7;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                if (i != j){    //so it doesnt play vs itself- waste of time
                    Main.grid = new board();
                    board.initialBoard();
                    Main.setGameEnded(false);

                    System.out.println("i = " + i + ": : : j = " + j);
                    while (!Main.gameEnded){
                        board.printBoard(board.getBoard());
                        if (playerMove.hasMoves(board.getBoard(), Main.blackTurn) != null) {
                            Main.setTurnFlag(false);
                            Main.aiList(Main.AIList[i]);
                        }
                        Main.setTurn(!Main.blackTurn);
                        if (playerMove.hasMoves(board.getBoard(), Main.blackTurn) != null) {
                            Main.setTurnFlag(false);
                            Main.aiList(Main.AIList[j]);
                        }
                        Main.setTurn(!Main.blackTurn);
                    }

                    board.printBoard(board.getBoard());

                    if (Main.winner == 'b'){ //population i won
                        scores[i]++;
                        bracket[i][j][0]++; //incr i win
                        //bracket[j][i][2]++; //incr j loss
                    }
                    else if (Main.winner == 'w'){ //population j won
                        scores[j]++;
                        //bracket[i][j][2]++; //incr i loss
                        bracket[j][i][0]++; //incr j win
                    }
                    else {  //draw
                        scores[i] = scores[i]+0.5;
                        scores[j] = scores[j]+0.5;
                        bracket[i][j][1]++; //incr draw for both
                        bracket[j][i][1]++;
                    }
                }
            }
        }
        for (int i = 0; i < 6; i++){
            System.out.println("score: " + scores[i]);
        }

        System.out.println("BRACKET");
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++) {
                //System.out.print(bracket[i][j][0] + "," + bracket[i][j][1] + "," + bracket[i][j][2] + " ; ");
                System.out.print(bracket[i][j][0] + "," + bracket[i][j][1] + " ; ");

            }
            System.out.println();
        }
    }

}
