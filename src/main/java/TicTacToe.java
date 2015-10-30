/**
 * Created by william on 10/29/15.
 */
public class TicTacToe {

    private Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    public enum GameResult {WIN, DRAW, LOOSE}

    public TicTacToe() {
        board = new Board() ;
    }

    public void printBoard() {
        System.out.println("-------------");
        System.out.println(board);
        System.out.println("-------------");
    }

    public boolean isBoardFull() {
        boolean isFull = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getElem(i, j) == -1) {
                    isFull = false;
                }
            }
        }

        return isFull;
    }

    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board.getElem(i, 0), board.getElem(i, 1), board.getElem(i, 2))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board.getElem(0, i), board.getElem(1, i), board.getElem(2, i))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(board.getElem(0, 0), board.getElem(1, 1), board.getElem(2, 2))) ||
                (checkRowCol(board.getElem(0, 2), board.getElem(1, 1), board.getElem(2, 0))));
    }

    private boolean checkRowCol(int c1, int c2, int c3) {
        return ((c1 != -1) && (c1 == c2) && (c2 == c3));
    }


    public Board getBoard() {
        try {
            return board.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
