package com.company.AI;

import com.company.Main;
import com.company.othello.board;
import com.company.othello.playerMove;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class geneticAlgorithm {

    private static final Random rand = new Random();

    public static void geneticAlgo(int pop, int lowBound, int upBound, int generations) {
        int[][] population = getPopulation(pop, lowBound, upBound);
        geneticAlgo2(population, pop, generations);

    }

    //pass in population size, upper and lower bounds, number of generations
    public static int[] geneticAlgo2(int[][] population, int pop, int generations) {

        System.out.println("generations" + generations);

        if (generations == 0){
            System.out.println("FINAL FINAL POPULATION RESULT");
            for (int i = 0; i < pop; i++) {
                System.out.println(population[i][0] + " " + population[i][1] + " " + population[i][2]);
            }
            return null;
        }

        System.out.println("population is:");
        for (int i = 0; i < pop; i++) {
            System.out.println(population[i][0] + " " + population[i][1] + " " + population[i][2]);
        }

        int[] fitnessScores = getRPRates(population);

        int[] rpRates = new int[pop];
        System.out.println("times to be selected");
        for (int i = 0; i < pop; i++) {
            rpRates[i] = Math.round(fitnessScores[i] / 2); //cast to type int
            System.out.println(rpRates[i]);
        }

        int[][] nextGen = new int[pop][3];

        //roulette wheel selection of parents
        for (int x = 0; x < pop; x++) {
            float select = rand.nextInt(pop);
            float partialSum = 0;
            int count = 0;
            for (int y = 0; y < pop; y++) {
                if (partialSum >= select) {  //when the total value of fitness >= select, pick that chromosome
                    nextGen[x] = population[count];
                } else {
                    partialSum += rpRates[y];
                    count++;
                }
            }
        }
        System.out.println("next gen");
        for (int i = 0; i < pop; i++) {
            System.out.println(nextGen[i][0] + " " + nextGen[i][1] + " " + nextGen[i][2]);
        }
        int[][] nextGenShuffled = new int[pop][3];

        Random rand = new Random();

        for (int i = 0; i < nextGen.length; i++) {
            int randomIndexToSwap = rand.nextInt(nextGen.length);
            int[] temp = nextGen[randomIndexToSwap];
            nextGen[randomIndexToSwap] = nextGen[i];
            nextGen[i] = temp;
        }

        nextGenShuffled = nextGen;

        System.out.println("next gen shuffled");
        for (int i = 0; i < pop; i++) {
            System.out.println(nextGenShuffled[i][0] + " " + nextGenShuffled[i][1] + " " + nextGenShuffled[i][2]);
        }

        int[][] nextGenCrossed = new int[pop][3];

        for (int i = 0; i < pop; i += 2) {
            int crossover = rand.nextInt(100);

            int[][] crossoverResults;
            nextGenCrossed = nextGenShuffled;

            if (i+1 < pop){ //ensures it doesnt cause error if uneven pop size
                if (crossover > 20) { //crossover 80% chance
                        crossoverResults = crossover(nextGenShuffled[i], nextGenShuffled[i + 1]);
                        nextGenCrossed[i] = crossoverResults[0];
                        nextGenCrossed[i + 1] = crossoverResults[1];
                }
                else{
                    nextGenCrossed[i] = nextGenShuffled[i];
                    nextGenCrossed[i+1] = nextGenShuffled[i+1];
                }
            }
        }
        System.out.println("next gen crossed");
        for (int i = 0; i < pop; i++) {
            System.out.println(nextGenCrossed[i][0] + " " + nextGenCrossed[i][1] + " " + nextGenCrossed[i][2]);
        }

        int[][] nextGenFinal = new int[pop][3];
        for (int i = 0; i < pop; i++) {
            int mutation = rand.nextInt(100);

            if (mutation > 98) { //mutate 1% chance
                int[] mutatedDouble = mutate(nextGenCrossed[i]);
                nextGenFinal[i] = mutatedDouble;
            }
            else{
                nextGenFinal[i] = nextGenCrossed[i];
            }
        }

        System.out.println("next gen final");
        for (int i = 0; i < pop; i++) {
            System.out.println(nextGenFinal[i][0] + " " + nextGenFinal[i][1] + " " + nextGenFinal[i][2]);
        }

        geneticAlgo2(nextGenFinal, pop, generations-1);
        return null;
    }


    public static int[][] getPopulation(int pop, int lowBound, int upBound){
        int weighting;
        int[][] population = new int[pop][3];   //3 weightings per individual

        //makes population of random weights
        for (int i=0; i < pop; i++){
            for (int j = 0; j < 3; j++){
                weighting = (int) Math.floor(Math.random()*(upBound-lowBound+1)+lowBound);
                population[i][j] = weighting;
            }
        }
        return population;
    }

    //pass in all population, make them all play vs eachother in minimax- get the one who won the most
    public static int[] getRPRates(int[][] population){

        int[] scores = new int[population.length];

        for (int i = 0; i < population.length; i++){
            for (int j = 0; j < population.length; j++){
                if (i != j){    //so it doesn't play vs itself
                    Main.grid = new board();
                    board.initialBoard();
                    Main.setGameEnded(false);
                    System.out.println("i = " + i + ": : : j = " + j);
                    while (!Main.gameEnded){
                        board.printBoard(board.getBoard());
                        if (playerMove.hasMoves(board.getBoard(), Main.blackTurn) != null) {
                            Main.setTurnFlag(false);
                            MiniMax.minimaxABGeneticAlgo(board.getBoard(), Main.blackTurn, Main.depth2, -100000, 100000, population[i]);
                        }
                        Main.setTurn(!Main.blackTurn);
                        if (playerMove.hasMoves(board.getBoard(), Main.blackTurn) != null) {
                            Main.setTurnFlag(false);
                            MiniMax.minimaxABGeneticAlgo(board.getBoard(), Main.blackTurn, Main.depth2, -100000, 100000, population[j]);
                        }
                        Main.setTurn(!Main.blackTurn);
                    }
                    if (Main.winner == 'b'){ //population i won
                        scores[i]++;
                    }
                    else if (Main.winner == 'w'){ //population j won
                        scores[j]++;
                    }
                    System.out.println(population[i][0] + " " + population[i][1] + " " + population[i][2]);
                    System.out.println(population[j][0] + " " + population[j][1] + " " + population[j][2]);
                    //see who wins and then increment their scores at[i] or [j]- black win increment i, white j
                }
            }
        }
        System.out.println("scores");
        for (int score : scores) {
            System.out.println(score);
        }
        return scores;
    }

    public static String paddingBinary(String binary){

        int pad = 8 - binary.length();

        //attempt 1
//        for (int i = 0; i < pad; i++){
//            paddingBinary.append("0");
//        }
//        paddingBinary.append(binary);

        //attempt 2
//        StringBuilder paddingBinary = new StringBuilder(); //faster/better than concatination
//        paddingBinary.append("0".repeat(Math.max(0, pad)));
//        paddingBinary.append(binary);

        //attempt 3
//        String paddingBinary = "0".repeat(Math.max(0, pad)) + binary;//faster/better than concatination

        return "0".repeat(Math.max(0, pad)) + binary;
    }

    public static int[] mutate(int[] individual) {

        System.out.println("in mutate");
        System.out.println();


        System.out.println("yo" + individual.length);
        int gene = rand.nextInt(individual.length); //which weighting it effects

        String binary = intToBinary(individual[gene]);

        String paddingBinary = paddingBinary(binary);

        System.out.println("gene: " + gene);
        int locus = rand.nextInt(8);    //point at which mutation occurs- 64 bits in a double

        System.out.println("-------");

        System.out.println("binary: " + paddingBinary);
        System.out.println("binary length: " + paddingBinary.length());
        System.out.println("locus: " + locus);

        char swap = paddingBinary.charAt(locus);
        char swapped;
        System.out.println("preswap: " + swap);
        if (swap == '1'){
            swapped = '0';
        }
        else{
            swapped = '1';
        }

        System.out.println("swapped: " + swapped);
        System.out.println("Binary: " + paddingBinary);

        StringBuilder binarySwap = new StringBuilder(paddingBinary);

        binarySwap.setCharAt(locus, swapped);
        String binaryMutated  =  binarySwap.toString();
        //String mutatedBinary = replaceChar(binary, swap, locus);

        System.out.println("mutated Binary");
        System.out.println("Binary: " + binaryMutated);

        individual[gene] = binaryToInt(binaryMutated);    //replace the selected gene with mutatedGene

        return individual;
    }

    public static int[][] crossover(int[] parent1, int[] parent2) {   //also compare to using 3 parents?
        int gene = rand.nextInt(parent1.length); //which weighting it effects


        System.out.println("in crossover");
        System.out.println();

        int locus = rand.nextInt(8);    //point at which mutation occurs- 8 bits being used
        System.out.println("gene:" + gene);
        System.out.println("locus:" + locus);
        System.out.println();


        System.out.println("p1: " + parent1[0] + " " + parent1[1] + " " + parent1[2]);
        System.out.println("p2: " + parent2[0] + " " + parent2[1] + " " + parent2[2]);
        System.out.println();


        String binaryParent1 = intToBinary(parent1[gene]);
        String binaryParent2 = intToBinary(parent2[gene]);

        System.out.println("binary1: " + binaryParent1);
        System.out.println("binary2: " + binaryParent2);
        System.out.println();

        String paddingBinaryParent1 = paddingBinary(binaryParent1);
        String paddingBinaryParent2 = paddingBinary(binaryParent2);

        String p1start = paddingBinaryParent1.substring(0, locus);
        String p1end = paddingBinaryParent1.substring(locus);
        System.out.println("p1 start: " + p1start );
        System.out.println("p1end: " + p1end );
        System.out.println();

        String p2start = paddingBinaryParent2.substring(0, locus);
        String p2end = paddingBinaryParent2.substring(locus);
        System.out.println("p2 start: " + p2start);
        System.out.println("p2end: " + p2end);
        System.out.println();

        String p1crossover = p1start + p2end;
        String p2crossover = p2start + p1end;
        System.out.println("p1crossed: " + p1crossover);
        System.out.println("p2crossed: " + p2crossover);
        System.out.println();

        int p1weight = binaryToInt(p1crossover);
        int p2weight = binaryToInt(p2crossover);

        int[][] crossoverResults = new int[2][3];
        crossoverResults[0] = parent1;
        crossoverResults[1] = parent2;

        crossoverResults[0][gene] = p1weight;
        crossoverResults[1][gene] = p2weight;

        System.out.println("p1crossedDone: " + crossoverResults[0][0] + " " + crossoverResults[0][1] + " " + crossoverResults[0][2]);
        System.out.println("p2crossedDone: " + crossoverResults[1][0] + " " + crossoverResults[1][1] + " " + crossoverResults[1][2]);

        return crossoverResults;
    }


    public static String intToBinary(int weighting){
        return Integer.toBinaryString(weighting);
    }

    public static int binaryToInt(String binaryValue){

        return Integer.parseInt(binaryValue,2);
    }


}
