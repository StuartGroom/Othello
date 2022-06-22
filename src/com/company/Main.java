package com.company;

import com.company.AI.MiniMax;
import com.company.AI.Node;
import com.company.gui.Gui;
import com.company.othello.board;
import com.company.othello.gameHandler;
import com.company.othello.playerMove;
import com.company.AI.geneticAlgorithm;
import com.company.tests.oneMoveTest;
import com.company.tests.tournament;
import java.util.*;

public class Main implements Runnable {

    //variable declaration
    public static Deque<String> moves = new ArrayDeque<>();
    public static boolean blackTurn = true;
    public static boolean noTurnFlag = false;
    public static boolean gameEnded = false;
    public static int depth = 4; //depth for minimax - above depth 4 becomes too long wait
    public static int depth2 = 7; //depth for AB pruning minimax
    public static int[] minimaxCoords = new int [2];
    public static boolean moveSet = false;
    public static char winner;
    public static int sleepTime = 200;
    public static String blackPlayer = "Player";
    public static String whitePlayer = "Player";
    public static int checksDone;
    public static long timeTaken;
    public static String[] AIList = new String[]{"Random", "Minimax", "MinimaxAB", "MinimaxAB2", "ABGeneticAlgo", "Iterative Minimax"};
    public static int turnCount = 0;
    public static boolean MCTS = false;

    //initialise a new object of Gui
    public static Gui GUI = new Gui();
    //initialise a new object of board
    public static board grid;

    //setters
    public static void setMinimaxCoords(int[] coords){
        minimaxCoords = coords;
        moveSet = true;
    }
    public static void setTurnFlag(boolean flag){
        noTurnFlag = flag;
    }
    public static void setGameEnded(boolean status){
        gameEnded = status;
    }
    public static void setWinner(char win){
        winner = win;
    }
    public static void setTurn(boolean turn){
        blackTurn = turn;
    }
    public static void setBoard(char[][] Board){
        grid.setBoard(Board);
    }
    public static void setSleepTime(int sleeptime){
        sleepTime = sleeptime;
    }
    public static void setBlackPlayer(String black){
        blackPlayer = black;
    }
    public static void setWhitePlayer(String white){
        whitePlayer = white;
    }
    public static void setChecksDone(int checks){ checksDone = checks; }
    public static void setTimeTaken(long time){ timeTaken = time; }
    public static Deque<String> getMovesList(){
        return moves;
    }
    public static void incrTurnCount(){
        turnCount++;
    }
    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    //game state decision
    @Override
    public void run() {
        while (true) {
            if (GUI.getState() == 1){
                othello();

            } else if (GUI.getState() == 2){
                try {
                    oneMoveTest.oneMoveTest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gui.setSTATE(Gui.STATE.MENU);

            } else if (GUI.getState() == 3){
                geneticAlgoMain();
                Gui.setSTATE(Gui.STATE.MENU);

            } else if (GUI.getState() == 4){
                tournament.tournament();
                Gui.setSTATE(Gui.STATE.MENU);
            }
            //else it will be in menu
            GUI.setPanel();
            GUI.repaint();
        }
    }

    //ai option menu
    public static void aiList(String AI){

        char disc;
        char oppDisc;

        if (blackTurn){
            disc = 'b';
            oppDisc = 'w';
        }
        else{
            disc = 'w';
            oppDisc = 'b';
        }

        switch (AI) {
            case "Random" -> {
                MiniMax.Random(board.getBoard(), blackTurn);
                noTurnFlag= false;
            }
            case "Minimax" -> {
                MiniMax.minimax(board.getBoard(), blackTurn, depth);
                if (moveSet) {
                    board.setMove(board.getBoard(), new int[]{minimaxCoords[0], minimaxCoords[1]}, disc);
                    gameHandler.flipDiscs(disc, oppDisc, minimaxCoords[0], minimaxCoords[1], board.getBoard());
                    noTurnFlag= false;
                }
            }
            case "MinimaxAB" -> {
                MiniMax.minimaxAB(board.getBoard(), blackTurn, depth2, -100000, 100000);
                if (moveSet) {
                    board.setMove(board.getBoard(), new int[]{minimaxCoords[0], minimaxCoords[1]}, disc);
                    gameHandler.flipDiscs(disc, oppDisc, minimaxCoords[0], minimaxCoords[1], board.getBoard());
                    noTurnFlag= false;
                }
            }
            case "MinimaxAB2" ->{
                MiniMax.minimaxAB2(board.getBoard(), blackTurn, depth2, -100000, 100000);
                if (moveSet) {
                    board.setMove(board.getBoard(), new int[]{minimaxCoords[0], minimaxCoords[1]}, disc);
                    gameHandler.flipDiscs(disc, oppDisc, minimaxCoords[0], minimaxCoords[1], board.getBoard());
                    noTurnFlag= false;
                }
            }
            //minimaxAB with genetic algorithm weights
            case "ABGeneticAlgo" -> {
                //these are weightings got from genetic algorithm 20 pop/6 generation
                int[] weights = {61, 123, 55};
                MiniMax.minimaxABGeneticAlgo(board.getBoard(), blackTurn, depth2, -100000, 100000, weights);
                noTurnFlag= false;
            }
            case "Iterative Minimax" -> {
                for (int itDepth = 1; itDepth <= depth; itDepth++) { //can also set a time constraint here
                    MiniMax.minimax(board.getBoard(), blackTurn, itDepth);
                }
                if (moveSet) {
                    board.setMove(board.getBoard(), new int[]{minimaxCoords[0], minimaxCoords[1]}, disc);
                    gameHandler.flipDiscs(disc, oppDisc, minimaxCoords[0], minimaxCoords[1], board.getBoard());
                    noTurnFlag= false;
                }
            }
            case "MCTS" -> {
                Node start = new Node(board.getBoard(), 0, 0);
                start.selectAction();
                for (int i = 0; i < 5; i++) {
                    blackTurn = true;   //reset board
                    gameEnded = false;
                    grid = new board();
                    board.initialBoard();

                    board.printBoard(board.getBoard());
                    start.selectAction();
                }
                int moveListIndex = Node.getBestChild(start);
                System.out.println("best move index = " + moveListIndex);
                System.out.println("Done");
                //go into child list and get the child at that index to make the move as
            }
        }
    }

    //genetic algo
    private void geneticAlgoMain(){

        geneticAlgorithm.geneticAlgo(20, 1, 100, 6);
        //do a test case to show it works with pop of uneven size
    }

    //main game
    private void othello(){

        grid = new board();
        board.initialBoard();
        board.printBoard(board.getBoard());
        GUI.repaint();

        gameEnded = false;
        try{
            Thread.sleep(1000);
        } catch (InterruptedException ignored){

        }

        String undoMove = board.gridToString(board.getBoard());
        moves.push(undoMove);

        while(!gameEnded) {

            if (blackPlayer.equals("Player") && whitePlayer.equals("Player")){

                playerVsplayer();

            } else if (blackPlayer.equals("Player")) {//player first vs ai
                if (whitePlayer.equals("MCTS")){
                    MCTS = true;
                }
                MCTS = false;
                playerVsAI(1, whitePlayer, sleepTime);

            } else if (whitePlayer.equals("Player")) {//ai first vs player
                if (blackPlayer.equals("MCTS")){
                    MCTS = true;
                }
                MCTS = false;
                playerVsAI(0, blackPlayer, sleepTime);

            } else {  //ai vs ai
                AIvsAI(blackPlayer, whitePlayer, sleepTime);
            }

            GUI.repaint();
        }
    }


    public void AIvsAI(String AIblack, String AIwhite, int sleepTime){

        long init = System.currentTimeMillis();
        GUI.repaint();

        if (playerMove.hasMoves(board.getBoard(), blackTurn) != null) {

            while (System.currentTimeMillis() < init + sleepTime){
                //wait
            }
            aiList(AIblack);
            incrTurnCount();
            GUI.repaint();
        }
        blackTurn = !blackTurn;

        GUI.repaint();

        init = System.currentTimeMillis();

        if (playerMove.hasMoves(board.getBoard(), blackTurn) != null) {

            while (System.currentTimeMillis() < init + sleepTime){ //waits ms to start make move
                //wait
            }
            aiList(AIwhite);
            incrTurnCount();
            GUI.repaint();
        }
        blackTurn = !blackTurn;

        GUI.repaint();
    }


    private void playerVsAI(int AIcolour, String AI, int sleepTime) {
        ArrayList legalMoves;
        long init = System.currentTimeMillis();

        if (AIcolour == 0) { //ai is black

            if (playerMove.hasMoves(board.getBoard(), blackTurn) != null) { //if ai has moves then make move

                while (System.currentTimeMillis() < init + sleepTime){
                    //wait
                }
                aiList(AI);
                incrTurnCount();
            }

            blackTurn = false; //regardless of whether it makes move or skips turn- switch turn tracker
            GUI.repaint();

            String undoMove = board.gridToString(board.getBoard());
            moves.push(undoMove);

            if (playerMove.hasMoves(board.getBoard(), blackTurn) != null) {

                legalMoves = playerMove.hasMoves(board.getBoard(), blackTurn);
                while (!blackTurn) {
                    if (Gui.getClickStatus()) {
                        boolean madeMove = playerMove.playermove(legalMoves);
                        while (!madeMove) {
                            GUI.repaint();
                            madeMove = playerMove.playermove(legalMoves);
                        }
                        incrTurnCount();
                        blackTurn = true;
                        GUI.repaint();
                    }
                    GUI.repaint();
                }
                Gui.setClickStatus(false);
            }


        } else if (AIcolour == 1) { //ai is white
            init = System.currentTimeMillis();
            if (playerMove.hasMoves(board.getBoard(), blackTurn) != null) {
                legalMoves = playerMove.hasMoves(board.getBoard(), blackTurn);

                while (Gui.getClickStatus()) {

                    boolean madeMove = playerMove.playermove(legalMoves);
                    while (!madeMove) {
                        GUI.repaint();
                        madeMove = playerMove.playermove(legalMoves);
                    }
                    incrTurnCount();
                    blackTurn = !blackTurn;
                    GUI.repaint();

                    String undoMove = board.gridToString(board.getBoard());
                    moves.push(undoMove);

                    if (playerMove.hasMoves(board.getBoard(), blackTurn) != null) {
                        legalMoves = playerMove.hasMoves(board.getBoard(), blackTurn);
                        while (System.currentTimeMillis() < init + sleepTime){
                            //wait
                        }
                        aiList(AI);
                        incrTurnCount();
                        blackTurn = !blackTurn;
                        GUI.repaint();

                    }
                }
                GUI.repaint();
                Gui.setClickStatus(false);
            }
        }
    }

    public static boolean pvp = false;

    private void playerVsplayer() {
        ArrayList legalMoves;
        if (playerMove.hasMoves(board.getBoard(), blackTurn) != null){
            legalMoves = playerMove.hasMoves(board.getBoard(), blackTurn);

            pvp = true;
            while (Gui.getClickStatus()){
                String undoMove = board.gridToString(board.getBoard());
                moves.push(undoMove);

                boolean madeMove = playerMove.playermove(legalMoves);
                while (!madeMove){
                    GUI.repaint();
                    madeMove = playerMove.playermove(legalMoves);
                }
                incrTurnCount();
                noTurnFlag= false;
                board.printBoard(board.getBoard());
                setTurn(!blackTurn);
                GUI.repaint();
            }
        }
        Gui.setClickStatus(false);
    }
}
