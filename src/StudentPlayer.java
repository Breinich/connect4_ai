import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class StudentPlayer extends Player {


    private int alfa = -Integer.MAX_VALUE;
    private int beta = Integer.MAX_VALUE;

    private final int[][] heuristicTable = {
            {3, 4, 5, 7, 5, 4, 3},
            {4, 6, 8, 10, 8, 6, 4},
            {5, 8, 11, 13, 11, 8, 5},
            {5, 8, 11, 13, 11, 8, 5},
            {4, 6, 8, 10, 8, 6, 4},
            {3, 4, 5, 7, 5, 4, 3}
    };

    private final int value4 = 1000000;
    private final int value3 = 20000;
    private final int value2 = 200;
    private int maxdepth = 3;


    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    @Override
    public int step(Board board) {
        alfa = -Integer.MAX_VALUE;
        beta = Integer.MAX_VALUE;


        int val = -Integer.MAX_VALUE;
        int col = -1;

        ArrayList<Integer> valid = board.getValidSteps();


        for (int c : valid) {

            Board myboard = new Board(board);

            myboard.step(playerIndex, c);


            int res = minimax(myboard, maxdepth - 1, false);
            if (val < res) {
                val = res;
                col = c;

            }
        }
        //System.out.println("\nertek: "+val+"\n");

        return -1;
    }

    private int minimax(Board board, int depth, boolean max) {

         if (board.gameEnded()) {
            if (board.getWinner() == playerIndex) {
                int sum = 0;
                for (int r = 0; r < boardSize[0]; r++) {
                    for (int c = 0; c < boardSize[1]; c++) {
                        if (board.getState()[r][c] == playerIndex)
                            sum += heuristicTable[r][c];
                        else if(board.getState()[r][c] == 3-playerIndex)
                            sum -= heuristicTable[r][c];
                    }
                }
                return Integer.MAX_VALUE - maxdepth*100 + depth + sum;
            }
            else if (board.getWinner() == 0)
                return 0;
            else {
                int sum = 0;
                for (int r = 0; r < boardSize[0]; r++) {
                    for (int c = 0; c < boardSize[1]; c++) {
                        if (board.getState()[r][c] == playerIndex)
                            sum += heuristicTable[r][c];
                        else if(board.getState()[r][c] == 3-playerIndex)
                            sum -= heuristicTable[r][c];
                    }
                }
                return Integer.MIN_VALUE + maxdepth*100 - depth-sum;
            }
        }
        else if (depth == 0)
            return calcValue(board);

        ArrayList<Integer> valid = board.getValidSteps();

        if (max) {
            int val = -Integer.MAX_VALUE;

            for (int c : valid) {
                Board myboard = new Board(board);
                myboard.step(playerIndex, c);

                val = Math.max(val, minimax(myboard, depth - 1, false));
                if (val >= beta)
                    return val;
                alfa = Math.max(alfa, val);
            }
            return val;
        } else {
            int val = Integer.MAX_VALUE;

            for (int c : valid) {
                Board myboard = new Board(board);
                myboard.step(3 - playerIndex, c);

                val = Math.min(val, minimax(myboard, depth - 1, true));
                if (val <= alfa)
                    return val;

                beta = Math.min(beta, val);
            }

            return val;
        }
    }


    private int calcValue(Board b){
        return 0;
    }

}