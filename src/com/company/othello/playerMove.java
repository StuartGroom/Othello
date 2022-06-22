package com.company.othello;

import com.company.Main;
import com.company.gui.Gui;
import javax.swing.*;
import java.util.ArrayList;

public class playerMove {

    public static ArrayList<int[]> hasMoves(char[][] Board, boolean blackTurn) {

        int[] score = board.getScore(Board);
        int blackScore = score[0];
        int whiteScore = score[1];
        String winner;

        ArrayList<int[]> legalMoves = gameHandler.potentialMovesList(Board, blackTurn);

        if (legalMoves.isEmpty()) {  //no valid moves
            if (Main.noTurnFlag) {//two no turns in a row so game over
                if (!Main.gameEnded){
                    Main.setGameEnded(true);

                    if (whiteScore > blackScore) {
                        winner = "white";
                        Main.setWinner('w');
                    } else if (blackScore > whiteScore) {
                        winner = "black";
                        Main.setWinner('b');
                    } else {
                        winner = "draw";
                        Main.setWinner('d');
                    }
                    System.out.println("***************************");
                    System.out.println("---------game over---------");
                    System.out.println("black: " + blackScore);
                    System.out.println("white: " + whiteScore);
                    System.out.println("winner: " + winner);

                    System.out.println("***************************");

                    if (Gui.getStaticState() == 1 & !Main.MCTS){   //if in game we want it to display play again message
                        int n = JOptionPane.showOptionDialog(new JFrame(), "winner = " + winner + "\nwhite score: " + whiteScore + "\nblack score: " + blackScore,
                                "GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, new Object[] {"PLAY AGAIN"}, JOptionPane.YES_OPTION);

                        if (n == JOptionPane.YES_OPTION) {
                            System.out.println("Back to Menu");
                            Gui.setSTATE(Gui.STATE.MENU);
                            Main.GUI.setPanel();
                            Gui.setMenuSet(false);
                        } else if (n == JOptionPane.CLOSED_OPTION) {
                            System.out.println("close");
                            System.exit(1);
                        }
                    }
                }

                return null;

            } else {
                //set flag to positive and skip turn
                Main.setTurnFlag(true);
                System.out.println("no legal moves - switching turn");
            }
            return null;

        } else{
            Main.setTurnFlag(false);
        }
        return legalMoves;
    }

    public static boolean playermove(ArrayList<int[]> legalMoves) {

        int[] coords = gameHandler.pixelToCoord(Gui.mouseX, Gui.mouseY);
        int x = coords[0];
        int y = coords[1];

        if (Gui.getClickStatus()) {

            for (int[] coordsPrint : legalMoves) {
                if (coordsPrint[0] == x && coordsPrint[1] == y) {//it is a legal move
                    if (Main.blackTurn) {
                        board.setMove(new int[]{x, y}, 'b');
                        gameHandler.flipDiscs('b', 'w', x, y, board.getBoard());
                    } else {
                        board.setMove(new int[]{x, y}, 'w');
                        gameHandler.flipDiscs('w', 'b', x, y, board.getBoard());
                    }

                    Gui.setClickStatus(false);
                    return true;
                }
            }
        }
        Gui.setClickStatus(false);
        return false;
    }
}





