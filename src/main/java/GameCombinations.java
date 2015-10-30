import java.util.*;

/**
 * Created by william on 10/29/15.
 */
public class GameCombinations {

    //using map in order to retrieve stored state
    // with its children in O(1)
    Map<Board, Board> boardsList = new HashMap<Board, Board>();

    /**
     * adds state to the states map if it is not already
     * there, otherwise returns state from the map
     * that is equal to the specified one
     * @param board board instance
     * @return the board already stored in states map
     */
    public Board checkAndAdd(Board board) {
        if(!boardsList.containsKey(board)) {
            boardsList.put(board, board);
            return board;
        }

        return boardsList.get(board);
    }

    /**
     * finds the state equal to the parent from the states
     * map and connects it with a child, adds child to the
     * states map
     * @param parent parent elem
     * @param child child elem
     */
    public void addAsDescendant(Board parent, Board child) {
        if(child.getParent() != null && child.getParent().equals(parent))
            throw new IllegalArgumentException("The child already has parent");

        if(parent.getChildList().containsKey(child))
            throw new IllegalArgumentException("The parent already has specified object as a child");

        if(boardsList.get(parent) != null) {
            Board parentBoard = boardsList.get(parent);
            parentBoard.addChild(child, 0);
            child.setParent(parentBoard);
            boardsList.put(child, child);
        } else {
            parent.addChild(child, 0);
            child.setParent(parent);
        }
    }

    /**
     * returns next states for the specified one (if any)
     * sorted by the score they have so far
     * @param current the current state
     * @return next states map
     */
    public TreeMap<Integer, Board> getNextStates(Board current) {
        Map<Board, Integer> childrenList = current.getChildList();  //boardsList also could be used here
        if(!childrenList.isEmpty()) {
            TreeMap<Integer, Board> entrySet = new TreeMap<Integer, Board>();
            for (Map.Entry<Board, Integer> child : childrenList.entrySet()) {
                entrySet.put(child.getValue(), child.getKey());
            }

            return entrySet;
        }
        return null;
    }

    public void updateScores(Board lastState, TicTacToe.GameResult result) {
        int increment = 0;
        switch (result) {
            case WIN: increment = 5;
                break;
            case DRAW: increment = 2;
                break;
            case LOOSE: increment = -5;
                break;
        }

        changeRanks(lastState, increment);
    }

    /**
     * changes states ranks after game
     * @param currentState
     * @param increment
     */
    private void changeRanks(Board currentState, int increment) {
        while (currentState.getParent() != null) {
            Board currentChild = currentState ;
            currentState = currentState.getParent();
            currentState.updateChildsBoost(currentChild, increment);
        }
    }
}
