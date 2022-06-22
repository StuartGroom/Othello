package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;


public class Gui extends JFrame {

    public static int mouseX;
    public static int mouseY;
    //public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(815,938);
    public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(1200,938);
    public static boolean mouseClick = false;
    public static boolean menuSet = false;

    Container Othello;

    public enum STATE{
        MENU,
        GAME,
        ONEMOVETEST,
        GENETICALGO,
        TOURNAMENT
    }

    public static STATE state = STATE.MENU;
    //public static STATE state = STATE.GAME;

    public static void setMenuSet(boolean bool){
        menuSet = bool;

    }
    public static void setSTATE(STATE stateSet){
        state = stateSet;
    }
    public static int getStaticState(){
        if (state == STATE.MENU){
            return 0;
        }
        else if (state == STATE.GAME){
            return 1;
        }
        else if (state == STATE.ONEMOVETEST){
            return 2;
        }
        else if (state == STATE.GENETICALGO){
            return 3;
        }
        else if (state == STATE.TOURNAMENT){
            return 4;
        }
        return 0;
    }
    public int getState() {
        if (state == STATE.MENU){
            return 0;
        }
        else if (state == STATE.GAME){
            return 1;
        }
        else if (state == STATE.ONEMOVETEST){
            return 2;
        }
        else if (state == STATE.GENETICALGO){
            return 3;
        }
        else if (state == STATE.TOURNAMENT){
            return 4;
        }
        return 0;
    }

    public Gui(){
        Othello=getContentPane();
        this.setTitle("Othello");
        this.setSize(OUTER_FRAME_DIMENSION);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        setPanel();

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

        this.repaint();
    }


    public void setPanel(){
        BoardPanel game = new BoardPanel();
        MainMenu menu = new MainMenu();

        if (state == STATE.GAME){

            Othello.removeAll();
            Othello.add(game);

            this.setContentPane(game);
            validate();
            setVisible(true);
            this.repaint();
        }
        else if(state == STATE.MENU){

            if (!menuSet){
                Othello.removeAll();
                Othello.add(menu);
                this.setContentPane(menu);
                validate();
                setVisible(true);
                this.repaint();

                menuSet = true;
            }


        }
        this.repaint();
    }

    public static int getMouseX(){
        return mouseX;
    }
    public static int getMouseY(){
        return mouseY;
    }

    public static boolean getClickStatus(){
        return mouseClick;
    }
    public static void setClickStatus(boolean click){
        mouseClick = click;
    }

    public class Move implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e) {

        }
        @Override
        public void mouseMoved(MouseEvent e) {
//            System.out.println("move");
            mouseX = e.getX();
            mouseY = e.getY();
//            System.out.println(mouseX);
//            System.out.println(mouseY);
            if (getState() == 0) { //in menu
                //play buttom
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 2 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 2 + 150) {
                    if (mouseY >= 250 && mouseY <= 350) {
                        MainMenu.setPlayColor(Color.RED);
                    } else {
                        MainMenu.setPlayColor(Color.gray);
                    }
                } else {
                    MainMenu.setPlayColor(Color.gray);
                }

                //tutorial button
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 4 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 4 + 150) {
                    if (mouseY >= 400 && mouseY <= 550) {
                        MainMenu.setOneMoveColor(Color.RED);
                    } else {
                        MainMenu.setOneMoveColor(Color.gray);
                    }
                } else {
                    MainMenu.setOneMoveColor(Color.gray);
                }

                //rules button
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 2 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 2 + 150) {
                    if (mouseY >= 400 && mouseY <= 550) {
                        MainMenu.setRulesColor(Color.RED);
                    } else {
                        MainMenu.setRulesColor(Color.gray);
                    }
                } else {
                    MainMenu.setRulesColor(Color.gray);
                }

                //GA button
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 4 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 4 + 150) {
                    if (mouseY >= 250 && mouseY <= 350) {
                        MainMenu.setGAColor(Color.RED);
                    } else {
                        MainMenu.setGAColor(Color.gray);
                    }
                } else {
                    MainMenu.setGAColor(Color.gray);
                }

                //tournament button
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 4 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 4 + 150) {
                    if (mouseY >= 550 && mouseY <= 650) {
                        MainMenu.setTournamentColor(Color.RED);
                    } else {
                        MainMenu.setTournamentColor(Color.gray);
                    }
                } else {
                    MainMenu.setTournamentColor(Color.gray);
                }


            }



        }
    }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) { //both left and right click trigger this
            mouseX = e.getX();
            mouseY = e.getY();

            mouseClick = true;

            if (getState() == 0) { //in menu
                //play button-
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 2 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 2 + 150){
                    if (mouseY >= 250 && mouseY <= 350){
                        setSTATE(STATE.GAME);
                        System.out.println("x" + mouseX + "y" + mouseY);
                        setPanel();
                    }
                }
                //one move test
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 4 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 4 + 150) {
                    if (mouseY >= 400 && mouseY <= 550) {
                        setSTATE(STATE.ONEMOVETEST);
                        setPanel();
                    }
                }

                //rules button
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 2 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 2 + 150) {
                    if (mouseY >= 400 && mouseY <= 550) {
                        try {
                            Desktop.getDesktop().browse(new URL("https://www.worldothello.org/about/about-othello/othello-rules/official-rules/english").toURI());
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }

                //GA button
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 4 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 4 + 150) {
                    if (mouseY >= 250 && mouseY <= 350) {
                        setSTATE(STATE.GENETICALGO);
                        setPanel();
                    }
                }

                //tournament button
                if (mouseX >= OUTER_FRAME_DIMENSION.getWidth() / 4 - 50 && mouseX <= OUTER_FRAME_DIMENSION.getWidth() / 4 + 150) {
                    if (mouseY >= 550 && mouseY <= 650) {
                        //make new game state for tournament and run it when this is pressed
                        setSTATE(STATE.TOURNAMENT);
                        setPanel();
                    }
                }



            }
        }



        @Override
        public void mousePressed(MouseEvent e) {


        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}
