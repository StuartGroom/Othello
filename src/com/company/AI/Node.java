package com.company.AI;

import com.company.Main;
import com.company.othello.board;
import com.company.othello.gameHandler;
import com.company.othello.playerMove;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



public class Node {


    private static boolean treeTurn;
    private static final Random r = new Random();
    private static final double epsilon = 1e-6;
    private static final ArrayList<Node> childrenNodes = new ArrayList<>();
    private static int visits = 0;
    private static float value = 0;
    private static char[][] Board = new char[8][8];

    //initializer for Nodes
    public Node(char[][] Board2, int Visits, float Value) {
        visits = Visits;
        String tempboard = board.gridToString(Board2);
        Board = board.stringToGrid(tempboard);
        //issue with board/curNode for 2nd run being set to final state of previous run
        value = Value;
    }

    public Node() {

    }

    public static int getBestChild(Node node){
        float bestValue = 0;
        int nodePos = 0;
        int bestNodePos = 0;

        for (Node child : childrenNodes) {

            if(bestValue < value){
                bestValue = value;
                bestNodePos = nodePos;
            }
            nodePos++;
        }
        return nodePos;
    }

    public void selectAction() { //selection
        treeTurn = Main.blackTurn;  //initialise turn as the main turn
        List<Node> visited = new LinkedList<>(); //list to track all nodes for incr in backpropagation
        Node curNode = this;
        visited.add(this);

        while (!curNode.isLeaf()) { //loops until at leaf
            System.out.println("not a leaf");
            board.printBoard(getBoard(curNode));
            curNode = curNode.select();
            visited.add(curNode);
            System.out.println(curNode);
        }


        curNode.expand();
        Node newNode = curNode.select();
        visited.add(newNode);

        double value = playout(newNode);

        for (Node node : visited) {
            updateStats(value);
            System.out.println("updating " + value);
        }

    }

    public static float getValue(){
        return value;
    }

    public void expand() {  //makes all child boardStates into nodes linked to the curr board

        char disc;
        char oppDisc;
        if (Main.blackTurn) {
            disc = 'b';
            oppDisc = 'w';
        }
        else {
            disc = 'w';
            oppDisc = 'b';
        }

        String boardStr = board.gridToString(getBoard(this));
        ArrayList<int[]> legalMoves;
        ArrayList<char[][]> children;
        legalMoves = gameHandler.potentialMovesList(getBoard(this), treeTurn);
        children = MiniMax.makeKids(boardStr, disc, oppDisc, legalMoves);

        for (Object Child : children){
            childrenNodes.add(new Node((char[][]) Child, 0, 0));
        }
    }

    private Node select() {

        Node selected = null;
        double bestValue = Double.MIN_VALUE;
        //System.out.println("in select v2");
        for (Node child : childrenNodes) {   //selects the best child - UCT decides between exploration/exploitation
            double uctValue = value / (visits + epsilon) +
                    Math.sqrt(Math.log(visits +1) / (visits + epsilon)) +
                    r.nextDouble() * epsilon;
            //random num to break ties in unexpanded nodes
            //nextDouble returns a double between 0 and 1


            if (uctValue > bestValue) {
                //System.out.println("replace la");
                selected = child;
                bestValue = uctValue;
            }
        }
        //System.out.println("best value" + bestValue);
        treeTurn = !treeTurn;
        System.out.println(selected);
        return selected;
    }

    private boolean isLeaf() {
        return childrenNodes.isEmpty();
    }

    public char[][] getBoard(Node node){
        return Board;
    }

    private double playout(Node node) {  //simulation
        char disc;
        if (Main.blackTurn) {
            disc = 'b';
        } else {
            disc = 'w';
        }

        String tempboard = board.gridToString(Board);
        char[][] tempBoard = board.stringToGrid(tempboard);

        //char[][] tempBoard = Board;
        while (!Main.gameEnded){    //play out game until end with random moves
            if (playerMove.hasMoves(getBoard(node), treeTurn) != null) {
                System.out.println("turn "+ treeTurn);
                board.printBoard(tempBoard);
                tempBoard = MiniMax.Random(getBoard(node), treeTurn);
            }
            treeTurn = !treeTurn;
        }

        //if win return 1, loss return 0
        if (Main.winner == disc){
            return 1;
        }
        else{
            return 0;
        }

    }

    private void updateStats(double Value) {   //backpropagation
        visits++;  //increment visits of all visited nodes
        value += Value;    //increment value by the value of the playout phase
    }

}

