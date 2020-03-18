package jThello;

import java.util.LinkedList;
import java.util.List;

public class GameState {
	
	// constants
    public static final int EMPTY = -1;
    public static final int PLAYER1 = 0;
    public static final int PLAYER2 = 1;
    
    private static final int[][] dir = {
 		    {0, 1}, // E
		    {0, -1}, // W
		    {1, 0}, // S
		    {-1, 0}, // N
		    {-1, 1}, // NE
		    {1, 1}, // SE
		    {1, -1}, // SW
		    {-1, -1} // NW
    };
    
    int size = 8;
    int board[][] = null;
    int nextPlayerToMove = PLAYER1;
    
    GameState(int _size) {
        size = _size;
        board = new int[size][size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                board[i][j] = EMPTY;
        // set up the initial position
        board[size/2-1][size/2-1] = PLAYER1;
        board[size/2][size/2] = PLAYER1;
        board[size/2-1][size/2] = PLAYER2;
        board[size/2][size/2-1] = PLAYER2;

    }
    
    // Creates a copy of the game state
    public GameState clone() {
        GameState newState = new GameState(size);
        for(int i = 0;i<size;i++)
            for(int j = 0;j<size;j++)
                newState.board[i][j] = board[i][j];
        newState.nextPlayerToMove = nextPlayerToMove;
        return newState;
    }
    
    // Returns the list of possible moves for the next player
    public List<Move> allMoves() {
        return allMoves(nextPlayerToMove);
    }
    
    // Safe element retrieval
    private int valueAt(int row, int col) {
    	if ((row >= 0) && (row < size) && (col >= 0) && (col < size)) 
    		return board[row][col];
    	return EMPTY;
    }
    // Returns the list of possible moves for a given player
    public List<Move> allMoves(int player) {
        List<Move> moves = new LinkedList<Move>();
        int otherPlayer = (player + 1) % 2;
        for(int i = 0;i<size;i++) {
            for(int j = 0;j<size;j++) {
                if (board[i][j]==EMPTY) {
                	int k = 0;
                	while (k < dir.length) { //iterate over the directions
                		int newRow = i + dir[k][0], newCol = j + dir[k][1];
                		int count = 0;
                		while (otherPlayer == valueAt(newRow, newCol)) {
                            newRow += dir[k][0];
                            newCol += dir[k][1];
                            count++;
                        }
                        if ((count > 0) && (player == valueAt(newRow, newCol))) {
                            moves.add(new Move(player, i, j));
                            break;
                        }
                        k++;
                	}
                }
            }
        }
                
        return moves;
	}
    
	private void makeMove(Move move) {
	    if (move == null) {
	        nextPlayerToMove = (nextPlayerToMove + 1) % 2;
	        return;
	    }
	    int player = move.player;
		int otherPlayer = (move.player + 1) % 2;
		board[move.row][move.col] = player;
	    for (int k = 0; k < dir.length; k++) {
	        int newRow = move.row + dir[k][0], newCol = move.col + dir[k][1];
	        int count = 0;
	        while (otherPlayer == valueAt(newRow, newCol)) {
	            newRow += dir[k][0];
	            newCol += dir[k][1];
	            count++;
	        }
	        if ((count > 0) && (player == valueAt(newRow, newCol))) {
	            newRow -= dir[k][0];
	            newCol -= dir[k][1];
	            while ((newRow != move.row) || (newCol != move.col)) {
	                board[newRow][newCol] = player;
	                newRow -= dir[k][0];
	                newCol -= dir[k][1];
	            }
	        }
	    }
	    this.nextPlayerToMove = otherPlayer;		

	}

	// Returns the final score
	public int finalScore() {
        int score = 0;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) {
                if (board[i][j] == PLAYER1) score++;
                if (board[i][j] == PLAYER2) score--;
            }
        return score;
    }
	
	public int score(int player) {
		int score = 0;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if (board[i][j] == player) score++;
        return score;
	}
    
    public boolean gameOver() {
    	int Nmoves = allMoves(PLAYER1).size() + allMoves(PLAYER2).size();
        return (Nmoves == 0);
    }
    
    public GameState applyMoveAndClone(Move move) {
        GameState newState = clone();
        newState.makeMove(move);
        return newState;
    }
    
    public String toString() {
        StringBuffer output = new StringBuffer();
        for(int j = 0;j<size;j++) {
            for(int i = 0;i<size;i++) {
                if (board[i][j]==PLAYER1) output.append("O");
                else if (board[i][j]==PLAYER2) output.append("X");
                else output.append(".");
            }
            output.append("\n");
        }
        return output.toString();
    }
}
