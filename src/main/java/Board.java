import java.util.*;

/**
 * Created by william on 10/29/15.
 */
public class Board implements Cloneable{
    private int[][] board;
    private Map<Board, Integer> childList = new HashMap<Board, Integer>();
    private Board parent;

    public Board() {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = -1;
            }
        }
    }

    public Board(int[][] board) {
        this.board = board;
    }


    public void addChild(Board child, Integer boost) {
        childList.put(child, boost);
    }

    public void updateChildsBoost(Board child, Integer boost) {
        childList.put(child, childList.get(child) + boost);
    }

    public Map<Board, Integer> getChildList() {
        return childList;
    }

    public Board getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Board))
            return false;

        Board arg = (Board) obj;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(!(board[i][j] == arg.board[i][j]))
                    return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = Arrays.hashCode(board[0]);
        hashCode = 31 * hashCode + Arrays.hashCode(board[1]);
        hashCode = 31 * hashCode + Arrays.hashCode(board[2]);

        return hashCode;
    }


    public int getElem(int i, int j) {
        return board[i][j];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append("| ");
            for (int j = 0; j < 3; j++) {
                char symbol = board[i][j] == 1 ? 'x' : (board[i][j] == 0 ? 'o' : '-');

                sb.append(symbol + " | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void setElem(int row, int col, int currentPlayerMark) {
        board[row][col] = currentPlayerMark;
    }

    public int[][] getBoardsView() {
        int[][] newBoard = new int[3][3];

        for(int i = 0; i < 3; i++) {
            newBoard[i] = board[i].clone();
        }

        return newBoard;
    }

    //Clones only backed matrix of the board
    //parent and childs are the same as in original one
    @Override
    protected Board clone() throws CloneNotSupportedException {
        Board cloneBoard = new Board(this.getBoardsView());
        for(Map.Entry<Board, Integer> child : this.getChildList().entrySet()) {
            cloneBoard.addChild(child.getKey(), child.getValue());
        }
        cloneBoard.setParent(parent);

        return cloneBoard;
    }

    public void setParent(Board parent) {
        this.parent = parent;
    }
}
