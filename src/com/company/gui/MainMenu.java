package com.company.gui;


import com.company.Main;

import javax.swing.*;
import java.awt.*;


public class MainMenu extends JPanel {



    public static Rectangle playButton = new Rectangle((int) Gui.OUTER_FRAME_DIMENSION.getWidth() / 2 -50, 250, 200, 100);
    public Rectangle setOneMoveButton = new Rectangle((int) Gui.OUTER_FRAME_DIMENSION.getWidth() / 4 -50, 400, 200, 100);
    public Rectangle rulesButton = new Rectangle((int) Gui.OUTER_FRAME_DIMENSION.getWidth() / 2 -50, 400, 200, 100);
    public Rectangle GAButton = new Rectangle((int) Gui.OUTER_FRAME_DIMENSION.getWidth() / 4 -50, 250, 200, 100);
    public Rectangle tournamentButton = new Rectangle((int) Gui.OUTER_FRAME_DIMENSION.getWidth() / 4 -50, 550, 200, 100);


    public static Color playColor = Color.gray;
    public static Color oneMoveColor = Color.gray;
    public static Color rulesColor = Color.gray;
    public static Color GAColor = Color.gray;
    public static Color tournamentColor = Color.gray;


    public static void setPlayColor(Color red) {
        playColor = red;
    }
    public static void setOneMoveColor(Color red) { oneMoveColor = red; }
    public static void setGAColor(Color red) {
        GAColor = red;
    }
    public static void setRulesColor(Color red) {
        rulesColor = red;
    }
    public static void setTournamentColor(Color red) {
        tournamentColor = red;
    }

    public void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.darkGray);
        g.fillRect(0,0,1200,900);

        Font title = new Font("arial", Font.BOLD, 50);
        g.setFont(title);

        g.setColor(Color.BLACK);
        g.drawString("OTHELLO", (int) Gui.OUTER_FRAME_DIMENSION.getWidth() / 2 -120, 100);

        Font button = new Font("arial", Font.BOLD, 30);
        g.setFont(button);

        g.setColor(playColor);
        g2d.draw(playButton);
        g.setColor(playColor);
        g.fillRect(playButton.x ,playButton.y, 200, 100);
        g.setColor(Color.black);
        g.drawString("play game", playButton.x +2, playButton.y+50);

        g.setColor(oneMoveColor);
        g2d.draw(setOneMoveButton);
        g.setColor(oneMoveColor);
        g.fillRect(setOneMoveButton.x ,setOneMoveButton.y, 200, 100);
        g.setColor(Color.black);
        g.drawString("1 move", setOneMoveButton.x +2, setOneMoveButton.y+50);

        g.setColor(rulesColor);
        g2d.draw(rulesButton);
        g.setColor(rulesColor);
        g.fillRect(rulesButton.x ,rulesButton.y, 200, 100);
        g.setColor(Color.black);
        g.drawString("Rules", rulesButton.x +2, rulesButton.y+50);

        g.setColor(GAColor);
        g2d.draw(GAButton);
        g.setColor(GAColor);
        g.fillRect(GAButton.x ,GAButton.y, 200, 100);
        g.setColor(Color.black);
        g.drawString("GA", GAButton.x +2, GAButton.y+50);

        g.setColor(tournamentColor);
        g2d.draw(tournamentButton);
        g.setColor(tournamentColor);
        g.fillRect(tournamentButton.x ,tournamentButton.y, 200, 100);
        g.setColor(Color.black);
        g.drawString("tournament", tournamentButton.x +2, tournamentButton.y+50);


        JLabel blackLabel = new JLabel("<html><font size='6' color=white>Black player:</font></html>");
        blackLabel.setFont(Font.getFont("ariel"));
        blackLabel.setPreferredSize(new Dimension(200, 50));
        JLabel whiteLabel = new JLabel("<html><font size='6' color=white>White player:</font></html>");
        whiteLabel.setFont(Font.getFont("ariel"));
        whiteLabel.setPreferredSize(new Dimension(200, 50));

        String[] playerTypes = { "Player", "Random", "Minimax", "MinimaxAB", "ABGeneticAlgo", "Iterative Minimax", "MCTS"};
        JComboBox blackPlayer = new JComboBox(playerTypes);
        JComboBox whitePlayer = new JComboBox(playerTypes);

        blackPlayer.setSelectedIndex(0);    //set as first index of player types
        blackPlayer.setBounds(800, 280, 150, 50);
        blackPlayer.addActionListener(e -> {
            try {
                JComboBox bpSource = (JComboBox) e.getSource();
                String option = (String)bpSource.getSelectedItem();
                System.out.println("black " + option);
                Main.setBlackPlayer(option);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        whitePlayer.setSelectedIndex(0);
        whitePlayer.setBounds(800, 450, 150, 50);
        whitePlayer.addActionListener(e -> {
            try {
                JComboBox wpSource = (JComboBox) e.getSource();
                String option = (String)wpSource.getSelectedItem();
                System.out.println("white " + option);
                Main.setWhitePlayer(option);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        blackLabel.setBounds(800, 230, 150, 50);
        whiteLabel.setBounds(800, 400, 150, 50);
        this.add(blackPlayer);
        this.add(whitePlayer);
        this.add(blackLabel);
        this.add(whiteLabel);

    }
}
