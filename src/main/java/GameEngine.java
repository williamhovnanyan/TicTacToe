import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by william on 10/29/15.
 */
public class GameEngine {
    private static GameCombinations gameCombinations = new GameCombinations();
    private static Random randomGen = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String answer = "y";

        while (answer.equals("y")) {
            boolean isWonByUser = false;
            boolean isFirst = true;

            TicTacToe ticTacToe = new TicTacToe();
            Board boardsLastState = null;
            while (!ticTacToe.checkForWin() && !ticTacToe.isBoardFull()) {
                System.out.println("You are the player X, please insert the position in the form of row,col (indexed are 0 based)");
                ticTacToe.printBoard();
                System.out.print("X = ");
                String[] input = scanner.next().split(",");

                int row = Integer.parseInt(input[0]);
                int col = Integer.parseInt(input[1]);

                Board newBoard = ticTacToe.getBoard();
                newBoard.setElem(row, col, 1);

                gameCombinations.addAsDescendant(ticTacToe.getBoard(), newBoard);

                if(isFirst) {
                    newBoard = gameCombinations.checkAndAdd(newBoard);
                    isFirst = false;
                }

                ticTacToe.setBoard(newBoard);
                boardsLastState = newBoard;

                if (!ticTacToe.checkForWin() && !ticTacToe.isBoardFull()) {
                    boardsLastState = makeComputerMove(ticTacToe);
                } else if (ticTacToe.checkForWin()) {
                    isWonByUser = true;
                }
            }

            if (!ticTacToe.checkForWin() && ticTacToe.isBoardFull()) {
                gameCombinations.updateScores(boardsLastState, TicTacToe.GameResult.DRAW);
            } else if (!isWonByUser) {
                gameCombinations.updateScores(boardsLastState, TicTacToe.GameResult.WIN);
            } else {
                gameCombinations.updateScores(boardsLastState, TicTacToe.GameResult.LOOSE);
            }

            ticTacToe.printBoard();
            System.out.print("The game is ended, do you want to continue? y/n : ");
            answer = scanner.next();
        }

    }

    private static Board makeComputerMove(TicTacToe ticTacToe) {
        TreeMap<Integer, Board> nextBoards = gameCombinations.getNextStates(ticTacToe.getBoard());
        int nextStates = computePossibleNumberOfNextStates(ticTacToe.getBoard());

        //generates new random board only if the current one has no child or
        //it's child score is negative and there are other possible options
        if(nextBoards == null || (nextBoards.lastKey() < 0 && nextStates - nextBoards.size() > 0)) {
            Board nextBoard = null;
            boolean isExistsAlready = (nextBoards == null);

            do {
                nextBoard = new Board(ticTacToe.getBoard().getBoardsView());

                int row = randomGen.nextInt(3);
                int col = randomGen.nextInt(3);
                while (nextBoard.getElem(row, col) != -1) {
                    row = randomGen.nextInt(3);
                    col = randomGen.nextInt(3);
                }
                nextBoard.setElem(row, col, 0);

                if(nextBoards != null) {
                    isExistsAlready = !nextBoards.containsValue(nextBoard);
                }

            } while (!isExistsAlready);  //generates new states until there is one that does not already exists

            gameCombinations.addAsDescendant(ticTacToe.getBoard(), nextBoard);
            ticTacToe.setBoard(nextBoard);
            return nextBoard;
        } else {
            Board nextBoard = new Board(nextBoards.lastEntry().getValue().getBoardsView());
            ticTacToe.setBoard(nextBoard);
            return nextBoard;
        }
    }

    private static int computePossibleNumberOfNextStates(Board board) {
        int freeNumber = 0;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board.getElem(i, j) == -1) {
                    freeNumber++;
                }
            }
        }
        return freeNumber;
    }
}
