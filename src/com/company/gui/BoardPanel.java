package com.company.gui;

import com.company.Main;
import com.company.othello.SerializationManager;
import com.company.othello.board;
import com.company.othello.playerMove;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;


public class BoardPanel extends JPanel {

    public static final Color board_green = new Color(0,102,0);
    public static final Color highlight_green = new Color(0,204,0);
    public static final Color legal_gold = new Color(255,204,51);
    static int spacing = 1;
    public static JCheckBox showBlackMoves = new JCheckBox("black");
    public static JCheckBox showWhiteMoves = new JCheckBox("white");

    public void paintComponent(Graphics g){

        g.setColor(Color.lightGray);
        g.fillRect(0,0,1200,1200);
        g.setColor(Color.darkGray);
        g.fillRect(0,100,800,800);

        if (Main.blackTurn){
            g.setColor(Color.black);
            g.fillOval(555,10,80,80);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("black turn", 650, 50);
        }
        else{
            g.setColor(Color.white);
            g.fillOval(555,10,80,80);

            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("white turn", 650, 50);
        }
        //scoreboard
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("black:" + board.getScore(board.getBoard())[0], 400, 50);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("white:" + board.getScore(board.getBoard())[1], 400, 80);

        //title
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.drawString("OTHELLO", 100, 70);

        //print the tiles and discs
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                g.setColor(board_green);

                ArrayList moveList = playerMove.hasMoves(board.getBoard(), Main.blackTurn);
                //to print legal move tiles
                if (moveList != null && showBlackMoves.isSelected() & Main.blackTurn){
                    for (Object o : moveList) {
                        int[] move = (int[]) o;
                        if (move[0] == x && move[1] == y) {
                            if (board.getBoard()[x][y] == ' ') {
                                g.setColor(legal_gold);
                            }
                        }
                    }
                }
                if (moveList != null && showWhiteMoves.isSelected() & !Main.blackTurn){
                    for (Object o : moveList) {
                        int[] move = (int[]) o;
                        if (move[0] == x && move[1] == y) {
                            if (board.getBoard()[x][y] == ' ') {
                                g.setColor(legal_gold);
                            }
                        }
                    }
                }

                int mouseX = Gui.getMouseX();
                int mouseY = Gui.getMouseY();
                //top bar is 26 pixels high
                //highlight what square curser is on
                if (mouseX >= 2*spacing+x*100 && mouseX <= 2*spacing+x*100+100-2*spacing && mouseY >= spacing+y*100+100+26 && mouseY <= spacing+y*100+100+100+26-2*spacing ){
                    g.setColor(highlight_green);
                }

                g.fillRect(spacing+x*100, spacing+y*100+100,100-2*spacing,100-2*spacing);
                g.setColor(board_green);

                //print discs
                if (board.getTile(new int[]{x, y}) == 'b'){
                    g.setColor(Color.black);
                    g.fillOval(2*spacing+x*100,spacing+y*100+100,100-2*spacing,100-2*spacing );
                    g.setColor(board_green);
                }
                else if (board.getTile(new int[]{x, y}) == 'w'){
                    g.setColor(Color.white);
                    g.fillOval(2*spacing+x*100,spacing+y*100+100,100-2*spacing,100-2*spacing );
                    g.setColor(board_green);
                }
            }
        }


        TextField fileName = new TextField();
        fileName.setBounds(900,170, 195,30);
        this.add(fileName);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(900,250,95,30);
        saveBtn.addActionListener(e -> {
            try {
                String filename = fileName.getText();
                char[][] saveBoard = board.getBoard();
                boolean saveTurn = Main.blackTurn;
                board.printBoard(saveBoard);

                //error handling for if filename text field isnt filled
                if (!filename.isEmpty()){

                    SerializationManager.save(saveBoard, filename);
                    SerializationManager.save(saveTurn, filename + ".turn");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        this.add(saveBtn);

        JButton loadBtn= new JButton("Load");
        loadBtn.setBounds(1000,250,95,30);

        loadBtn.addActionListener(e -> {
            try {

                String filename = fileName.getText();
                System.out.println("filename + " + filename);
                if (!filename.isEmpty()) {
                    try{
                        char[][] loadedBoard = (char[][]) SerializationManager.load(filename);
                        board.printBoard(loadedBoard);
                        boolean loadedTurn = (boolean) SerializationManager.load(filename + ".turn");
                        Main.setBoard(loadedBoard);
                        Main.setTurn(loadedTurn);
                    }
                    catch (Exception ignored){

                    }

                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        this.add(loadBtn);

        JButton undoBtn = new JButton("undo move");
        undoBtn.setBounds(900,300,195,30);

        undoBtn.addActionListener(e -> {
            try {
                Deque<String> moves = Main.getMovesList();
                if (moves.size() > 0){
                    String undoString = moves.pop(); //get last board
                    char[][] undoBoard = board.stringToGrid(undoString);
                    board.printBoard(undoBoard);

                    if (Main.turnCount != 0){
                        Main.setTurn(!Main.blackTurn);
                    }

                    Main.setBoard(undoBoard);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        this.add(undoBtn);


        JLabel showLegalMoves = new JLabel("<html><font size='5' color=black>show legal moves for:</font></html>");

        showLegalMoves.setBounds(900, 450, 300, 50);
        showBlackMoves.setBounds(900, 500, 150, 50);
        showBlackMoves.setBackground(Color.lightGray);
        showWhiteMoves.setBounds(900, 550, 150, 50);
        showWhiteMoves.setBackground(Color.lightGray);

        this.add(showLegalMoves);
        this.add(showBlackMoves);
        this.add(showWhiteMoves);


        final int minWait = 0;
        final int maxWait = 5000;
        final int initWait = 200;    //initial value
        JLabel AIWaitLabel = new JLabel("<html><font size='5' color=black>wait time for AI /ms</font></html>");


        JSlider AIWaitTime = new JSlider(JSlider.HORIZONTAL,
                minWait, maxWait, initWait);
        AIWaitTime.setMajorTickSpacing(1000);
        AIWaitTime.setPaintLabels(true);
        AIWaitTime.setPaintLabels(true);
        AIWaitTime.setBackground(Color.lightGray);
        AIWaitTime.setBounds(820, 750, 350, 100);
        AIWaitTime.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            Main.setSleepTime(source.getValue());
        });

        AIWaitLabel.setBounds(820, 700, 200, 50);

        this.add(AIWaitLabel);
        this.add(AIWaitTime);

        JLabel blackPlayerType = new JLabel("<html><font size='5' color=black>" + Main.blackPlayer + " vs " + "</font></html>",  SwingConstants. RIGHT);
        blackPlayerType.setBounds(800, 20, 200, 60);
        JLabel whitePlayerType = new JLabel("<html><font size='5' color=white>" + Main.whitePlayer + "</font></html>");
        whitePlayerType.setBounds(1000, 20, 200, 60);
        this.add(blackPlayerType);
        this.add(whitePlayerType);

    }

}







