package com.company.tests;

public class testBoards {

    static char[][] testBoard(int option){

        //got a few different openings to test on this website:
        //http://samsoft.org.uk/reversi/


        //test iago is for black - has 2 best moves to check
        char[][] testBoard;//initialised as empty

        testBoard = new char[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                testBoard[i][j] = ' ';
            }
        }

        //3 early game boards
        if (option == 0){ //Perpendicular  - black move
            testBoard[2][3] = 'b';

            testBoard[3][3] = 'b';
            testBoard[3][4] = 'b';

            testBoard[4][2] = 'w';
            testBoard[4][3] = 'w';
            testBoard[4][4] = 'w';
        }
        else if (option == 1){   //diagonal opening - black move
            testBoard[2][2] = 'w';
            testBoard[2][3] = 'b';

            testBoard[3][3] = 'w';
            testBoard[3][4] = 'b';

            testBoard[4][3] = 'b';
            testBoard[4][4] = 'w';
        }
        else if (option == 2){  //parallel opening - black move
            testBoard[3][3] = 'w';
            testBoard[3][4] = 'w';
            testBoard[3][5] = 'w';

            testBoard[4][3] = 'b';
            testBoard[4][4] = 'b';
            testBoard[4][5] = 'b';

        }

        //3 mid game boards
        else if (option == 3){ //Iago - black move
            testBoard[2][2] = 'w';
            testBoard[2][3] = 'w';
            testBoard[2][4] = 'w';

            testBoard[3][2] = 'w';
            testBoard[3][3] = 'b';
            testBoard[3][4] = 'b';
            testBoard[3][5] = 'b';

            testBoard[4][2] = 'w';
            testBoard[4][3] = 'w';
            testBoard[4][4] = 'b';

            testBoard[5][2] = 'w';
            testBoard[5][3] = 'b';
        }
        else if (option == 4){ //Rotating Flat(Kling Continuaton) - black move

            testBoard[1][3] = 'b';
            testBoard[1][4] = 'b';

            testBoard[2][1] = 'b';
            testBoard[2][2] = 'b';
            testBoard[2][3] = 'b';
            testBoard[2][4] = 'b';
            testBoard[2][6] = 'w';

            testBoard[3][1] = 'b';
            testBoard[3][2] = 'b';
            testBoard[3][3] = 'b';
            testBoard[3][4] = 'b';
            testBoard[3][5] = 'w';

            testBoard[4][1] = 'w';
            testBoard[4][2] = 'w';
            testBoard[4][3] = 'w';
            testBoard[4][4] = 'w';
            testBoard[4][5] = 'w';
            testBoard[4][6] = 'w';

            testBoard[5][2] = 'w';
            testBoard[5][3] = 'w';
            testBoard[5][4] = 'w';
            testBoard[5][5] = 'w';
        }
        else if (option == 5) { //Brightstein - black move

            testBoard[1][3] = 'b';
            testBoard[1][5] = 'b';

            testBoard[2][2] = 'w';
            testBoard[2][3] = 'b';
            testBoard[2][4] = 'b';

            testBoard[3][2] = 'w';
            testBoard[3][3] = 'w';
            testBoard[3][4] = 'w';
            testBoard[3][5] = 'w';
            testBoard[3][6] = 'w';

            testBoard[4][1] = 'w';
            testBoard[4][2] = 'w';
            testBoard[4][3] = 'w';
            testBoard[4][4] = 'b';
            testBoard[4][5] = 'b';

            testBoard[5][2] = 'w';
            testBoard[5][3] = 'w';
            testBoard[5][4] = 'b';
            testBoard[5][5] = 'w';

            testBoard[6][4] = 'b';
        }

        //3 late game boards
        else if (option == 6) {  //Landau corner exchange

            testBoard[0][2] = 'b';
            testBoard[0][3] = 'b';
            testBoard[0][4] = 'b';
            testBoard[0][5] = 'b';

            testBoard[1][0] = 'b';
            testBoard[1][2] = 'w';
            testBoard[1][3] = 'w';
            testBoard[1][4] = 'w';
            testBoard[1][5] = 'b';

            testBoard[2][0] = 'b';
            testBoard[2][1] = 'b';
            testBoard[2][2] = 'w';
            testBoard[2][3] = 'b';
            testBoard[2][4] = 'b';
            testBoard[2][5] = 'w';
            testBoard[2][6] = 'b';

            testBoard[3][0] = 'b';
            testBoard[3][1] = 'b';
            testBoard[3][2] = 'w';
            testBoard[3][3] = 'b';
            testBoard[3][4] = 'w';
            testBoard[3][5] = 'b';
            testBoard[3][6] = 'b';

            testBoard[4][0] = 'b';
            testBoard[4][1] = 'b';
            testBoard[4][2] = 'w';
            testBoard[4][3] = 'w';
            testBoard[4][4] = 'w';
            testBoard[4][5] = 'b';

            testBoard[5][1] = 'w';
            testBoard[5][2] = 'w';
            testBoard[5][3] = 'b';
            testBoard[5][4] = 'w';
            testBoard[5][5] = 'b';

            testBoard[6][0] = 'w';
            testBoard[6][1] = 'w';
            testBoard[6][2] = 'w';
            testBoard[6][3] = 'w';
            testBoard[6][4] = 'w';
            testBoard[6][5] = 'w';

            testBoard[7][2] = 'w';
            testBoard[7][3] = 'w';
            testBoard[7][4] = 'w';
            testBoard[7][5] = 'w';
        }
        else if (option == 7) { //interior sweep
            testBoard[1][0] = 'b';
            testBoard[1][1] = 'w';
            testBoard[1][6] = 'w';
            testBoard[1][7] = 'b';

            testBoard[2][0] = 'b';
            testBoard[2][1] = 'w';
            testBoard[2][2] = 'w';
            testBoard[2][3] = 'w';
            testBoard[2][4] = 'w';
            testBoard[2][5] = 'w';
            testBoard[2][6] = 'w';
            testBoard[2][7] = 'b';

            testBoard[3][0] = 'b';
            testBoard[3][1] = 'w';
            testBoard[3][2] = 'b';
            testBoard[3][3] = 'b';
            testBoard[3][4] = 'b';
            testBoard[3][5] = 'b';
            testBoard[3][6] = 'w';
            testBoard[3][7] = 'b';

            testBoard[4][0] = 'b';
            testBoard[4][1] = 'w';
            testBoard[4][2] = 'w';
            testBoard[4][3] = 'b';
            testBoard[4][4] = 'b';
            testBoard[4][5] = 'b';
            testBoard[4][6] = 'w';
            testBoard[4][7] = 'b';

            testBoard[5][0] = 'b';
            testBoard[5][1] = 'w';
            testBoard[5][2] = 'w';
            testBoard[5][3] = 'w';
            testBoard[5][4] = 'b';
            testBoard[5][5] = 'w';
            testBoard[5][6] = 'w';
            testBoard[5][7] = 'b';

            testBoard[6][0] = 'b';
            testBoard[6][1] = 'w';
            testBoard[6][2] = 'w';
            testBoard[6][3] = 'w';
            testBoard[6][4] = 'w';
            testBoard[6][5] = 'b';
            testBoard[6][6] = 'w';
            testBoard[6][7] = 'b';

            testBoard[7][0] = 'b';
            testBoard[7][1] = 'b';
            testBoard[7][2] = 'b';
            testBoard[7][3] = 'b';
            testBoard[7][4] = 'b';
            testBoard[7][5] = 'b';
            testBoard[7][6] = 'b';
            testBoard[7][7] = 'b';
        }
        else if (option == 8) { //diagonal sacrifice for swindle
            testBoard[0][0] = 'w';
            testBoard[0][1] = 'b';
            testBoard[0][2] = 'b';
            testBoard[0][3] = 'w';
            testBoard[0][4] = 'w';
            testBoard[0][5] = 'w';
            testBoard[0][6] = 'w';

            testBoard[1][0] = 'w';
            testBoard[1][1] = 'b';
            testBoard[1][2] = 'b';
            testBoard[1][3] = 'b';
            testBoard[1][4] = 'w';
            testBoard[1][5] = 'w';
            testBoard[1][6] = 'w';

            testBoard[2][0] = 'w';
            testBoard[2][1] = 'w';
            testBoard[2][2] = 'w';
            testBoard[2][3] = 'w';
            testBoard[2][4] = 'w';
            testBoard[2][5] = 'w';
            testBoard[2][6] = 'w';
            testBoard[2][7] = 'w';

            testBoard[3][0] = 'w';
            testBoard[3][1] = 'w';
            testBoard[3][2] = 'w';
            testBoard[3][3] = 'w';
            testBoard[3][4] = 'b';
            testBoard[3][5] = 'w';
            testBoard[3][6] = 'w';
            testBoard[3][7] = 'b';

            testBoard[4][0] = 'w';
            testBoard[4][1] = 'w';
            testBoard[4][2] = 'w';
            testBoard[4][3] = 'b';
            testBoard[4][4] = 'b';
            testBoard[4][5] = 'w';
            testBoard[4][6] = 'w';
            testBoard[4][7] = 'b';

            testBoard[5][0] = 'w';
            testBoard[5][1] = 'b';
            testBoard[5][2] = 'w';
            testBoard[5][3] = 'b';
            testBoard[5][4] = 'w';
            testBoard[5][5] = 'w';
            testBoard[5][6] = 'w';
            testBoard[5][7] = 'w';

            testBoard[6][0] = 'w';
            testBoard[6][2] = 'b';
            testBoard[6][3] = 'b';
            testBoard[6][4] = 'w';
            testBoard[6][5] = 'w';
            testBoard[6][6] = 'w';

            testBoard[7][1] = 'b';
            testBoard[7][2] = 'b';
            testBoard[7][3] = 'b';
            testBoard[7][4] = 'b';
            testBoard[7][5] = 'b';
            testBoard[7][6] = 'b';
        }

        return testBoard;
    }


}
