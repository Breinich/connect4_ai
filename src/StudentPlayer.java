import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class StudentPlayer extends Player {

    private final int[][] heuristicTable = {
            {-130, -110, -100, -70, -100, -110, -130},
            {-110, -80, -60, -50, -60, -80, -110},
            {-100, -60, -40, -30, -40, -60, -100},
            {-100, -60, -40, -30, -40, -60, -100},
            {-110, -80, -60, -50, -60, -80, -110},
            {-130, -110, -100, -70, -100, -110, -130}
    };

    private final int maxdepth = 5;


    public StudentPlayer(int playerIndex, int[] boardSize, int nToConnect) {
        super(playerIndex, boardSize, nToConnect);
    }

    @Override
    public int step(Board board) {
        int alfa = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;


        int val = -Integer.MAX_VALUE;
        int col = -1;

        ArrayList<Integer> valid = board.getValidSteps();


        for (int c : valid) {

            Board myboard = new Board(board);

            myboard.step(playerIndex, c);

            int res = minimax(myboard, maxdepth - 1,alfa,beta, false);
            if (val < res) {
                val = res;
                col = c;
            }
        }
        //System.out.println("\nertek: "+val+"\n");

        return col;
    }

    private int minimax(Board board, int depth, int alfa, int beta, boolean max) {

         if (board.gameEnded()) {
            if (board.getWinner() == playerIndex) {
                return Integer.MAX_VALUE - maxdepth*1000 + depth*100 + heurVal(board.getState(), playerIndex);
            }
            else if (board.getWinner() == 0)
                return heurVal(board.getState(), playerIndex);
            else {
                return Integer.MIN_VALUE + maxdepth*1000 - depth*100 - heurVal(board.getState(), playerIndex);
            }
        }
        else if (depth == 0)
            return calcValue(board.getState(), playerIndex);

        ArrayList<Integer> valid = board.getValidSteps();

        int val;
        if (max) {
            val = Integer.MIN_VALUE;

            for (int c : valid) {
                Board myboard = new Board(board);
                myboard.step(playerIndex, c);

                val = max(val, minimax(myboard, depth - 1,alfa, beta, false));
                alfa = max(alfa, val);
                if (alfa >= beta)
                    break;
            }
        } else {
            val = Integer.MAX_VALUE;

            for (int c : valid) {
                Board myboard = new Board(board);
                myboard.step(3 - playerIndex, c);

                val = min(val, minimax(myboard, depth - 1, alfa, beta, true));
                beta = min(beta, val);
                if (beta <= alfa)
                    break;
            }
        }
        return val;
    }

    private int heurVal(int[][] board, int piece) {
        int sum = 0;
        for (int r = 0; r < boardSize[0]; r++) {
            for (int c = 0; c < boardSize[1]; c++) {
                if (board[r][c] == piece)
                    sum += heuristicTable[r][c];
                else if(board[r][c] == 3-piece)
                    sum += heuristicTable[r][c]*2;
            }
        }
        return sum;
    }


    public int calcValue(int[][] b, int piece){
        int res = 0;
        //horizontal
        for(int r = 0; r < 6; r++){
            ArrayList<Integer> row = new ArrayList<>();
            for(int i : b[r])
                row.add(i);
            for(int c = 0; c < 4; c++){
                res += calcWindow(row.subList(c, c+4), piece);
            }
        }
        //vertical
        for(int c = 0; c < 7; c++){
            ArrayList<Integer> col = new ArrayList<>();
            for(int temp = 0; temp < 6; temp++){
                col.add(b[temp][c]);
            }
            for(int r = 0; r < 3; r++){
                res += calcWindow(col.subList(r, r+4), piece);
            }
        }
        //diagonal
        for(int c = -2; c < 4;c++){
            ArrayList<Integer> diag = new ArrayList<>();
            int tc = c;
            int r = 0;
            while (tc < 7 && r < 6) {
                if(tc >= 0){
                    diag.add(b[r][tc]);
                }
                tc++;
                r++;
            }
            for(int i = 0; i < diag.size()-4; i++){
                res += calcWindow(diag.subList(i, i+4), piece);
            }
        }
        //skewdiagonal
        for(int c = 8; c > 2;c--){
            ArrayList<Integer> skdiag = new ArrayList<>();
            int tc = c;
            int r = 0;
            while (tc >= 0 && r < 6) {
                if(tc < 7){
                    skdiag.add(b[r][tc]);
                }
                tc--;
                r++;
            }
            for(int i = 0; i < skdiag.size()-4; i++){
                res += calcWindow(skdiag.subList(i, i+4), piece);
            }
        }


        return res+heurVal(b, piece);
    }

    private int calcWindow(List<Integer> window, int playerIndex){
        int playerNum = 0;
        int opponentNum = 0;
        int emptyNum = 0;
        for (Integer i : window){
            if(i == playerIndex)
                playerNum++;

            else if(i == 3-playerIndex)
                opponentNum++;

            else
                emptyNum++;
        }

        int value4 = 1000000;
        int value3 = 10000;
        int value2 = 1000;
        switch (playerNum){
            case 4:
                return value4;
            case 3:
                if(emptyNum == 1)
                    return value3;
            case 2:
                if(emptyNum == 2)
                    return value2;
            default:
                if (opponentNum == 3 && emptyNum == 1)
                    return -value3;
        }

        return 0;
    }

}