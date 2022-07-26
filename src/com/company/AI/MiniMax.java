package com.company.AI;

import com.company.Main;
import com.company.othello.board;
import com.company.othello.gameHandler;
import java.util.ArrayList;
import java.util.Random;

public class MiniMax {

 private static final int[][] priorityBoard = new int[8][8];

    public static int totalCount = 0;
    public static int totalCountAB = 0;
    public static int totalCountGAB = 0;

    public static char[][] Random(char[][] Board, boolean blackTurn){

        long timeInit = System.nanoTime();
        char disc;
        char oppDisc;

        if (blackTurn) {
            disc = 'b';
            oppDisc = 'w';
        } else {
            disc = 'w';
            oppDisc = 'b';
        }

        ArrayList<int[]> legalMoves;
        legalMoves = gameHandler.potentialMovesList(Board, blackTurn);

        //selects a random legal move from the list of legal moves
        Random rand = new Random(); //instance of random class
        int upperbound = legalMoves.size();
        if (upperbound > 0){
            int int_random = rand.nextInt(upperbound);

            int[] coords = legalMoves.get(int_random);
            int x = coords[0];
            int y = coords[1];

            Board = board.setMove(Board, new int[]{x, y}, disc);
            Board = gameHandler.flipDiscs(disc, oppDisc, x, y, Board);
            Main.setTurnFlag(false);
            long decisionTime = System.nanoTime() - timeInit;

            //setting for excel spreadsheet output
            Main.setTimeTaken(decisionTime);
            Main.setChecksDone(1);

        }
        return Board;
    }


    public static ArrayList<char[][]> makeKids(String boardStr, char disc, char oppDisc, ArrayList<int[]> legalMoves){

        ArrayList<char[][]> children = new ArrayList<>();
        for (int[] i : legalMoves) {    //make list of every child
            int stringPos = (i[0]) * 8 + i[1];
            String boardString = replaceChar(boardStr, disc, stringPos); //makes the move
            char[][] boardGrid = board.stringToGrid(boardString);
            gameHandler.flipDiscs(disc, oppDisc, i[0], i[1], boardGrid);
            children.add(boardGrid);
        }
        return children;
    }

    public static long minimax(char[][] Board, boolean blackTurn, int depth) {

        char disc;
        char oppDisc;
        if (blackTurn) {
            disc = 'b';
            oppDisc = 'w';
        }
        else {
            disc = 'w';
            oppDisc = 'b';
        }

        long timeInit = System.nanoTime();
        String boardStr = board.gridToString(Board);
        ArrayList<int[]> legalMoves;
        legalMoves = gameHandler.potentialMovesList(Board, blackTurn);
        ArrayList children = makeKids(boardStr, disc, oppDisc, legalMoves);

        if (depth == 0){
            totalCount++;
            return evalFunction(Board, blackTurn);
        }

        if (blackTurn) {    //max
            long maxEval = -10000;
            int maxChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimax((char[][]) child, false, depth - 1);
                if (maxEval < eval){
                    maxChild = count;
                }
                maxEval = Math.max(maxEval, eval);
                count++;
            }
            if (depth == Main.depth){
                int x = legalMoves.get(maxChild)[0];
                int y = legalMoves.get(maxChild)[1];

                Main.setMinimaxCoords(new int[]{x, y});
                Main.setTurnFlag(false);
                long decisionTime = System.nanoTime() - timeInit;
                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCount);

                totalCount = 0;

            }
            return maxEval;

        } else {  //min
            long minEval = 10000;
            int minChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimax((char[][]) child, true, depth - 1);
                if (minEval > eval){
                    minChild = count;
                }
                minEval = Math.min(minEval, eval);
                count++;
            }
            if (depth == Main.depth){
                int x = legalMoves.get(minChild)[0];
                int y = legalMoves.get(minChild)[1];

                Main.setMinimaxCoords(new int[]{x, y});
                Main.setTurnFlag(false);
                long decisionTime = System.nanoTime() - timeInit;

                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCount);

                totalCount = 0;
            }
            return minEval;
        }
    }

    public static long minimaxAB(char[][] Board, boolean blackTurn, int depth, long alpha, long beta) {

        long timeInit = System.nanoTime();

        char disc;
        char oppDisc;
        if (blackTurn) {
            disc = 'b';
            oppDisc = 'w';
        } else {
            disc = 'w';
            oppDisc = 'b';
        }

        String boardStr = board.gridToString(Board);
        ArrayList<int[]> legalMoves;
        legalMoves = gameHandler.potentialMovesList(Board, blackTurn);
        ArrayList children = makeKids(boardStr, disc, oppDisc, legalMoves);

        if (depth == 0){
            totalCountAB++;
            return evalFunction(Board, blackTurn);
        }

        if (blackTurn) { //max
            long maxEval = -10000;
            int maxChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimaxAB((char[][]) child, false, depth - 1, alpha, beta);
                if (maxEval < eval){
                    maxChild = count;
                }
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha){
                    break;
                }
                count++;
            }
            if (depth == Main.depth2){
                if (!legalMoves.isEmpty()){
                    int x = legalMoves.get(maxChild)[0];
                    int y = legalMoves.get(maxChild)[1];
                    Main.setMinimaxCoords(new int[]{x, y});
                    Main.setTurnFlag(false);
                }
                long decisionTime = System.nanoTime() - timeInit;
                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCountAB);
                totalCountAB = 0;
            }
            return maxEval;

        } else { //min
            long minEval = 10000;
            int minChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimaxAB((char[][]) child, true, depth - 1, alpha, beta);
                if (minEval > eval){
                    minChild = count;
                }
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta >= alpha){
                    break;
                }
                count++;
            }
            if (depth == Main.depth2){
                if (!legalMoves.isEmpty()){
                    int x = legalMoves.get(minChild)[0];
                    int y = legalMoves.get(minChild)[1];
                    Main.setMinimaxCoords(new int[]{x, y});
                    Main.setTurnFlag(false);
                }
                long decisionTime = System.nanoTime() - timeInit;
                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCountAB);
                totalCountAB = 0;
            }
            return minEval;
        }
    }

    public static long minimaxAB2(char[][] Board, boolean blackTurn, int depth, long alpha, long beta) {

        long timeInit = System.nanoTime();

        char disc;
        char oppDisc;
        if (blackTurn) {
            disc = 'b';
            oppDisc = 'w';
        } else {
            disc = 'w';
            oppDisc = 'b';
        }

        String boardStr = board.gridToString(Board);
        ArrayList<int[]> legalMoves;
        legalMoves = gameHandler.potentialMovesList(Board, blackTurn);
        ArrayList children = makeKids(boardStr, disc, oppDisc, legalMoves);

        if (depth == 0){
            totalCountAB++;
            return evalFunction2(Board, blackTurn);
        }

        if (blackTurn) { //max
            long maxEval = -10000;
            int maxChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimaxAB2((char[][]) child, false, depth - 1, alpha, beta);
                if (maxEval < eval){
                    maxChild = count;
                }
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha){
                    break;
                }
                count++;
            }
            if (depth == Main.depth2){
                if (!legalMoves.isEmpty()){
                    int x = legalMoves.get(maxChild)[0];
                    int y = legalMoves.get(maxChild)[1];
                    Main.setMinimaxCoords(new int[]{x, y}); //attempting to make iterative possible
                    Main.setTurnFlag(false);
                }

                long decisionTime = System.nanoTime() - timeInit;

                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCountAB);

                totalCountAB = 0;

            }
            return maxEval;
        } else { //min
            long minEval = 10000;
            int minChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimaxAB2((char[][]) child, true, depth - 1, alpha, beta);
                if (minEval > eval){
                    minChild = count;
                }
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta >= alpha){
                    break;
                }
                count++;
            }
            if (depth == Main.depth2){

                if (!legalMoves.isEmpty()){
                    int x = legalMoves.get(minChild)[0];
                    int y = legalMoves.get(minChild)[1];
                    Main.setMinimaxCoords(new int[]{x, y}); //attempting to make iterative possible
                    Main.setTurnFlag(false);
                }

                long decisionTime = System.nanoTime() - timeInit;

                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCountAB);

                totalCountAB = 0;

            }
            return minEval;
        }
    }



    public static String replaceChar(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

    public static int evalFunction(char[][] Board, boolean blackTurn){

        //number of black vs white
        int[] scores = board.getScore(Board);

        int blackDiscScore = scores[0];
        int whiteDiscScore = scores[1];
        int blackBoardScore = 0;

        int blackDiscDiff = blackDiscScore - whiteDiscScore;

        int mobility;

        mobility = gameHandler.potentialMovesList(Board, blackTurn).size();

        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                if (Board[x][y] == 'b'){
                    blackBoardScore = priorityBoard(x, y);
                }
            }
        }
        int blackScore = 2*mobility + 10*(blackDiscDiff) + blackBoardScore;

        return blackScore; //returning only blackScore; because white just wants to minimise blackScore
    }


    public static int evalFunction2(char[][] Board, boolean blackTurn) {

        int[] scores = board.getScore(Board);

        char disc;
        char oppDisc;
        if (Main.blackTurn) {
            disc = 'b';
            oppDisc = 'w';
        } else {
            disc = 'w';
            oppDisc = 'b';
        }

        int blackScore = 0;
        if (Main.turnCount <= 8){

            blackScore = earlyGame(Board, scores, blackTurn);
        }
        if (Main.turnCount > 8){
            blackScore = midGame(Board, scores, blackTurn);
        }

        return blackScore; //returning only blackScore; because white just wants to minimise blackScore
    }

    public static int earlyGame(char[][] Board, int[] scores, boolean blackTurn){
        //want to flip less while also not losing
        //want to stay in center
        //want to maximise mobility
        int blackDiscScore = scores[0];
        int whiteDiscScore = scores[1];
        int blackDiscDiff = blackDiscScore - whiteDiscScore;
        int mobility;
        
        mobility = gameHandler.potentialMovesList(Board, blackTurn).size();
        int blackBoardScore = 0;
                
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                if (Board[x][y] == 'b'){
                    blackBoardScore = priorityBoard(x, y);
                }
            }
        }
        
        if (blackDiscScore != 0){   //want to maximise number of opposite in early game to increase mobility
            blackDiscDiff = whiteDiscScore - blackDiscScore;
        }

        return 100*mobility + 5*(blackDiscDiff) + blackBoardScore;
    }

    public static int midGame(char[][] Board, int[] scores, boolean blackTurn){

        int blackDiscScore = scores[0];
        int whiteDiscScore = scores[1];
        int blackBoardScore = 0;
        int blackDiscDiff = blackDiscScore - whiteDiscScore;
        int mobility;
        int corners = 0;

        mobility = gameHandler.potentialMovesList(Board, blackTurn).size();

        //blackBoardScore
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                if (Board[x][y] == 'b'){
                    blackBoardScore = priorityBoard(x, y);
                }
            }
        }

        //corner score
        if (Board[0][0] == 'b'){
            corners++;
            priorityBoard[0][1] = 750;    //if you capture the corner these tiles are no longer so bad
            priorityBoard[1][1] = 750;    //as they become stable
            priorityBoard[1][0] = 750;
        }
        if (Board[0][7] == 'b'){
            corners++;
            priorityBoard[0][6] = 750;
            priorityBoard[1][6] = 750;
            priorityBoard[1][7] = 750;
        }
        if (Board[7][0] == 'b'){
            corners++;
            priorityBoard[6][0] = 750;
            priorityBoard[6][1] = 750;
            priorityBoard[7][1] = 750;
        }
        if (Board[7][7] == 'b'){
            corners++;
            priorityBoard[7][6] = 750;
            priorityBoard[6][6] = 750;
            priorityBoard[6][7] = 750;
        }
        if (Board[0][0] == 'w'){
            corners--;
            priorityBoard[0][1] = -750;
            priorityBoard[1][1] = -750;
            priorityBoard[1][0] = -750;
        }
        if (Board[0][7] == 'w'){
            corners--;
            priorityBoard[0][6] = -750;
            priorityBoard[1][6] = -750;
            priorityBoard[1][7] = -750;
        }
        if (Board[7][0] == 'w'){
            corners--;
            priorityBoard[6][0] = -750;
            priorityBoard[6][1] = -750;
            priorityBoard[7][1] = -750;
        }
        if (Board[7][7] == 'w'){
            corners--;
            priorityBoard[7][6] = -750;
            priorityBoard[6][6] = -750;
            priorityBoard[6][7] = -750;
        }

        return 2*mobility + 10*(blackDiscDiff) + blackBoardScore + 100*corners;
    }


    public static long minimaxABGeneticAlgo(char[][] Board, boolean blackTurn, int depth, long alpha, long beta, int[] weights) {

        long timeInit = System.nanoTime();

        char disc;
        char oppDisc;
        if (blackTurn) {
            disc = 'b';
            oppDisc = 'w';
        } else {
            disc = 'w';
            oppDisc = 'b';
        }

        if (depth == 0){
            totalCountGAB++;
            return genAlgoEval(Board, weights, blackTurn);
        }

        String boardStr = board.gridToString(Board);
        ArrayList<int[]> legalMoves;
        legalMoves = gameHandler.potentialMovesList(Board, blackTurn);
        ArrayList children = makeKids(boardStr, disc, oppDisc, legalMoves);

        if (blackTurn) { //max
            long maxEval = -10000;
            int maxChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimaxABGeneticAlgo((char[][]) child, false, depth-1, alpha, beta, weights);
                if (maxEval < eval){
                    maxChild = count;
                }
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha){
                    break;
                }
                count++;
            }
            if (depth == Main.depth2){
                int x = legalMoves.get(maxChild)[0];
                int y = legalMoves.get(maxChild)[1];


                board.setMove(Board, new int[]{x, y}, disc);
                gameHandler.flipDiscs(disc, oppDisc, x, y, Board);
                Main.setTurnFlag(false);

                long decisionTime = System.nanoTime() - timeInit;

                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCountGAB);
                totalCountGAB = 0;

            }
            return maxEval;
        } else { //min
            long minEval = 10000;
            int minChild = 0;
            int count = 0;
            for (Object child : children) {
                long eval = minimaxABGeneticAlgo((char[][]) child, true, depth-1, alpha, beta, weights);
                if (minEval > eval){
                    minChild = count;
                }
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta >= alpha){
                    break;
                }
                count++;
            }
            if (depth == Main.depth2){
                int x = legalMoves.get(minChild)[0];
                int y = legalMoves.get(minChild)[1];

                board.setMove(Board, new int[]{x, y}, disc);
                gameHandler.flipDiscs(disc, oppDisc, x, y, Board);
                Main.setTurnFlag(false);

                long decisionTime = System.nanoTime() - timeInit;


                //setting for excel spreadsheet output
                Main.setTimeTaken(decisionTime);
                Main.setChecksDone(totalCountGAB);

                totalCountGAB = 0;

            }
            return minEval;
        }
    }


    public static int genAlgoEval(char[][] Board, int[] weights, boolean blackTurn){

        //number of black vs white
        int[] scores = board.getScore(Board);

        int blackDiscScore = scores[0];
        int whiteDiscScore = scores[1];
        int whiteBoardScore = 0;
        int blackBoardScore = 0;

        int blackDiscDiff = blackDiscScore - whiteDiscScore;

        int mobility;

        mobility = gameHandler.potentialMovesList(Board, blackTurn).size();

        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                if (Board[x][y] == 'b'){
                    blackBoardScore = priorityBoard(x, y);
                }
            }
        }

        float blackScore = weights[0]*mobility + weights[1]*(blackDiscDiff) + weights[2]*blackBoardScore;

        return (int) blackScore;
        //returning only blackScore; because white just wants to minimise blackScore
        //do player score - opposite score, higher is better. (min will be better for min player)
    }

    public static int priorityBoard(int x, int y) {
        priorityBoard[0][0] = 7500;
        priorityBoard[0][1] = -1500;
        priorityBoard[0][2] = 500;
        priorityBoard[0][3] = 400;
        priorityBoard[0][4] = 400;
        priorityBoard[0][5] = 500;
        priorityBoard[0][6] = -1500;
        priorityBoard[0][7] = 7500;
        priorityBoard[1][0] = -1500;
        priorityBoard[1][1] = -2500;
        priorityBoard[1][2] = -225;
        priorityBoard[1][3] = -250;
        priorityBoard[1][4] = -250;
        priorityBoard[1][5] = -225;
        priorityBoard[1][6] = -2500;
        priorityBoard[1][7] = -1500;
        priorityBoard[2][0] = 500;
        priorityBoard[2][1] = -225;
        priorityBoard[2][2] = 15;
        priorityBoard[2][3] = 5;
        priorityBoard[2][4] = 5;
        priorityBoard[2][5] = 15;
        priorityBoard[2][6] = -225;
        priorityBoard[2][7] = 500;
        priorityBoard[3][0] = 400;
        priorityBoard[3][1] = -250;
        priorityBoard[3][2] = 5;
        priorityBoard[3][3] = 25;
        priorityBoard[3][4] = 25;
        priorityBoard[3][5] = 5;
        priorityBoard[3][6] = -250;
        priorityBoard[3][7] = 400;
        priorityBoard[4][0] = 400;
        priorityBoard[4][1] = -250;
        priorityBoard[4][2] = 5;
        priorityBoard[4][3] = 25;
        priorityBoard[4][4] = 25;
        priorityBoard[4][5] = 5;
        priorityBoard[4][6] = -250;
        priorityBoard[4][7] = 400;
        priorityBoard[5][0] = 500;
        priorityBoard[5][1] = -225;
        priorityBoard[5][2] = 15;
        priorityBoard[5][3] = 5;
        priorityBoard[5][4] = 5;
        priorityBoard[5][5] = 15;
        priorityBoard[5][6] = -225;
        priorityBoard[5][7] = 500;
        priorityBoard[6][0] = -1500;
        priorityBoard[6][1] = -2500;
        priorityBoard[6][2] = -225;
        priorityBoard[6][3] = -250;
        priorityBoard[6][4] = -250;
        priorityBoard[6][5] = -225;
        priorityBoard[6][6] = -2500;
        priorityBoard[6][7] = -1500;
        priorityBoard[7][0] = 7500;
        priorityBoard[7][1] = -1500;
        priorityBoard[7][2] = 500;
        priorityBoard[7][3] = 400;
        priorityBoard[7][4] = 400;
        priorityBoard[7][5] = 500;
        priorityBoard[7][6] = -1500;
        priorityBoard[7][7] = 7500;

        return priorityBoard[x][y];
    }

}
