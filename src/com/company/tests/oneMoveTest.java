package com.company.tests;

import com.company.Main;
import com.company.othello.board;
import com.company.othello.playerMove;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class oneMoveTest {

    public static int boardNum = 0;

    public static void oneMoveTest() throws Exception {
        //loop through every ai and do 1 move, output the results+speed+checks done
        Main.grid = new board();

        //also add mcts if it starts working
        String[] AIList2 = new String[]{"Random", "Minimax", "MinimaxAB", "MinimaxAB2", "ABGeneticAlgo", "MinimaxAB", "ABGeneticAlgo", "Iterative Minimax"};
        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        //early game
        // spreadsheet object
        XSSFSheet spreadsheet = workbook.createSheet("oneMoveTestDataEarly");
        // creating a row object
        XSSFRow row;
        // This data needs to be written (Object[])
        Map<String, Object[]> moveData = new TreeMap<>();
        moveData.put("0", new Object[] {"AI type", "1", "2", "3"});

        Main.depth2 = 4;
        long[] timeArray = new long[4];
        long[] checkArray = new long[4];

        for (int j= 0; j < 8; j++){
            if (j == 5){
                Main.depth2 = 7; //set depth = 7 for 2nd run of two AB algorithsm
            }
            for (int i = 0; i < 3; i++){ //loop through each ai (5 options)
                boardNum = i;
                char[][] testBoard = testBoards.testBoard(i);
                Main.grid.setBoard(testBoard);
                oneMoveAI(AIList2[j]);
                timeArray[i] = Main.timeTaken;

                String index = Integer.toString(j+1);
                moveData.put(index, new Object[] {AIList2[j], timeArray[0], timeArray[1], timeArray[2]});//index
            }
        }
        Main.depth2 = 4; //reset depth for checks done run

        Map<String, Object[]> moveData2 = new TreeMap<>();
        moveData2.put("10", new Object[] {"AI type", "1", "2", "3"});
        for (int j= 0; j < 8; j++){
            if (j == 5){
                Main.depth2 = 7; //set depth = 7 for 2nd run of two AB algorithsm
            }
            for (int i = 0; i < 3; i++){ //loop through each board
                boardNum = i;
                char[][] testBoard = testBoards.testBoard(i);
                Main.grid.setBoard(testBoard);
                oneMoveAI(AIList2[j]);
                checkArray[i] = Main.checksDone;

                String index = Integer.toString(j+11);
                moveData2.put(index, new Object[] {AIList2[j], checkArray[0], checkArray[1], checkArray[2]});//index
            }
        }
        Main.depth2 = 4;

        Set<String> keyid = moveData.keySet();
        int rowid = 0;

        // writing the data into the sheet
        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = moveData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }

        Set<String> keyid2 = moveData2.keySet();

        // writing the data into the sheet
        for (String key : keyid2) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = moveData2.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }


        //mid game
        XSSFSheet spreadsheet2 = workbook.createSheet("oneMoveTestDataMid");
        // creating a row object
        XSSFRow row2;

        Map<String, Object[]> moveData3 = new TreeMap<>();
        moveData3.put("0", new Object[] {"AI type", "1", "2", "3"});

        Main.depth2 = 4;
        for (int j= 0; j < 8; j++){ //loop through each ai
            if (j == 5){
                Main.depth2 = 7; //set depth = 7 for 2nd run of two AB algorithsm
            }
            for (int i = 0; i < 3; i++){ //loop through each test board
                boardNum = i+3;
                char[][] testBoard = testBoards.testBoard(i+3);
                Main.grid.setBoard(testBoard);
                oneMoveAI(AIList2[j]);
                timeArray[i] = Main.timeTaken;

                String index = Integer.toString(j+1);
                moveData3.put(index, new Object[] {AIList2[j], timeArray[0], timeArray[1], timeArray[2]});//index
            }
        }
        Main.depth2 = 4; //reset depth for checks done run

        Map<String, Object[]> moveData4 = new TreeMap<>();
        moveData4.put("10", new Object[] {"AI type", "1", "2", "3"});
        for (int j= 0; j < 8; j++){
            if (j == 5){
                Main.depth2 = 7; //set depth = 7 for 2nd run of two AB algorithsm
            }
            for (int i = 0; i < 3; i++){ //loop through each board
                boardNum = i+3;
                char[][] testBoard = testBoards.testBoard(i+3);
                Main.grid.setBoard(testBoard);
                oneMoveAI(AIList2[j]);
                checkArray[i] = Main.checksDone;

                String index = Integer.toString(j+11);
                moveData4.put(index, new Object[] {AIList2[j], checkArray[0], checkArray[1], checkArray[2]});//index
            }
        }
        Main.depth2 = 4;

        Set<String> keyid3 = moveData3.keySet();
        int rowid2 = 0;

        // writing the data into the sheet
        for (String key : keyid3) {

            row2 = spreadsheet2.createRow(rowid2++);
            Object[] objectArr = moveData3.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row2.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }

        Set<String> keyid4 = moveData2.keySet();

        // writing the data into the sheet
        for (String key : keyid4) {

            row2 = spreadsheet2.createRow(rowid2++);
            Object[] objectArr = moveData4.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row2.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }

        //late game
        XSSFSheet spreadsheet3 = workbook.createSheet("oneMoveTestDataLate");
        // creating a row object
        XSSFRow row3;

        Map<String, Object[]> moveData5 = new TreeMap<>();
        moveData5.put("0", new Object[] {"AI type", "1", "2", "3"});

        Main.depth2 = 4;
        for (int j= 0; j < 8; j++){
            if (j == 5){
                Main.depth2 = 7; //set depth = 7 for 2nd run of two AB algorithms
            }
            for (int i = 0; i < 3; i++){ //loop through each testboard
                boardNum = i+6;
                char[][] testBoard = testBoards.testBoard(i+6);
                Main.grid.setBoard(testBoard);
                oneMoveAI(AIList2[j]);
                timeArray[i] = Main.timeTaken;

                String index = Integer.toString(j+1);
                moveData5.put(index, new Object[] {AIList2[j], timeArray[0], timeArray[1], timeArray[2]});//index
            }
        }
        Main.depth2 = 4; //reset depth for checks done run

        Map<String, Object[]> moveData6 = new TreeMap<>();
        moveData6.put("10", new Object[] {"AI type", "1", "2", "3"});
        for (int j= 0; j < 8; j++){
            if (j == 5){
                Main.depth2 = 7; //set depth = 7 for 2nd run of two AB algorithms
            }
            for (int i = 0; i < 3; i++){ //loop through each test board
                boardNum = i+6;
                char[][] testBoard = testBoards.testBoard(i+6);
                Main.grid.setBoard(testBoard);
                oneMoveAI(AIList2[j]);
                checkArray[i] = Main.checksDone;

                String index = Integer.toString(j+11);
                moveData6.put(index, new Object[] {AIList2[j], checkArray[0], checkArray[1], checkArray[2]});//index
            }
        }
        Main.depth2 = 4;

        Set<String> keyid5 = moveData5.keySet();
        int rowid3 = 0;

        // writing the data into the sheet
        for (String key : keyid5) {

            row3 = spreadsheet3.createRow(rowid3++);
            Object[] objectArr = moveData5.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row3.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }

        Set<String> keyid6 = moveData6.keySet();

        // writing the data into the sheet
        for (String key : keyid6) {

            row3 = spreadsheet3.createRow(rowid3++);
            Object[] objectArr = moveData6.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row3.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }


        // writing the workbook into the file- sets file name and location
        FileOutputStream out = new FileOutputStream(new File("C:/3 year/Dissertation/filename.xlsx"));

        workbook.write(out);
        out.close();
    }


    private static void oneMoveAI(String AIblack){

        if (playerMove.hasMoves(board.getBoard(), Main.blackTurn) != null) {
            long init = System.nanoTime();
            Main.aiList(AIblack);
            long timeTaken =  (System.nanoTime() - init);
            System.out.println("--------------------------------------");
            System.out.println("AI = " + AIblack);
            System.out.println("board number = " + boardNum);
            System.out.println("time = " + timeTaken);
            System.out.println("checks = " + Main.checksDone);

            Main.setTurn(true);
            board.printBoard(board.getBoard());
            Main.GUI.repaint();
        }
        Main.GUI.repaint();
    }

}
