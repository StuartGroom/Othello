package com.company.othello;

import com.company.Main;

import java.io.Serializable;

public class board implements Serializable {

    private static char[][] gameboard;
    private static boolean turn;
    //initializer to make first empty board
    public board() {
        gameboard = new char[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                gameboard[i][j] = ' ';
            }
        }
        turn = Main.blackTurn;
    }

    public static boolean getTurn(){
        return turn;
    }

    public static char[][] getBoard(){
        return gameboard;
    }

    public void setBoard(char[][] Board) {
        gameboard = Board;
    }

    public static char getTile(int[] coordinate) {
        return gameboard[coordinate[0]][coordinate[1]];
    }

    public static void setMove(int[] coord, char discColour){
        gameboard[coord[0]][coord[1]] = discColour;

    }

    public static char[][] setMove(char[][] board, int[] move, char Disc){
        board[move[0]][move[1]] = Disc;
//        System.out.println("x " + move[0]);
//        System.out.println("y " + move[1]);
//        System.out.println("disc " + Disc);
        return board;
    }

    public static void printBoard(char[][] board){
        //prints the logical grid in command line
        for (int i =0; i< 8; i++){
            for (int z =0; z< 8; z++){
                System.out.print(board[z][i]);
                System.out.print("|");
            }
            System.out.println();
            for (int q =0; q< 16; q++) {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println("end of grid");
    }

    public static void printBoard(board Board){
        char[][] board2 = getBoard();
        //prints the logical grid in command line
        for (int i =0; i< 8; i++){
            for (int z =0; z< 8; z++){
                System.out.print(board2[z][i]);
                System.out.print("|");
            }
            System.out.println();
            for (int q =0; q< 16; q++) {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println("end of grid");
    }

    public static void initialBoard(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                int[] yo = {i, j};
                setMove(yo, ' ');
            }
        }
        setMove(new int[]{3, 3}, 'w');
        setMove(new int[]{4, 3}, 'b');
        setMove(new int[]{3, 4}, 'b');
        setMove(new int[]{4, 4}, 'w');
    }


    public static int[] getScore(char[][] board){
        int blackScore = 0;
        int whiteScore = 0;
        for (int i = 0; i <= 7; i++){   //iterate over all tiles on board
            for (int j = 0; j <= 7; j++) {

                if (board[i][j] == 'b'){
                    blackScore++;
                }
                else if(board[i][j] == 'w'){
                    whiteScore++;
                }
            }
        }
        return new int[]{blackScore, whiteScore};
    }


    public static String gridToString(char[][] board){
        StringBuilder boardString = new StringBuilder();
        for (char[] row : board) {
            for (char column : row) {
                boardString.append(column);
            }
        }
        return boardString.toString();
    }

    public static char[][] stringToGrid(String boardString){

        char[][] boardGrid = new char[8][8];
        int counter = 0;
        int y = 0;
        int x = 0;
        for (int i = 0 ; i <= boardString.length()-1; i++){

            if (y == 8){
                y = 0;
                x++;
            }
            boardGrid[x][y] = boardString.charAt(counter);
            counter++;
            y++;
        }

        return boardGrid;
    }

    public static boolean boardFull(char[][] board){
        boolean full = true;
        for (int i =0; i < 8; i++){
            for (int j =0; j < 8; j++){
                if (board[i][j] == ' ') {
                    full = false;
                    break;
                }
            }
        }
        return full;
    }


}
