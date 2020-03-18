package jThello;

import java.util.LinkedList;
import java.util.List;

public class GameState {
	
	// constants
    public static final int EMPTY = -1;
    public static final int PLAYER1 = 0;
    public static final int PLAYER2 = 1;
    
    int boardSize = 8;
    int board[][] = null;
    int nextPlayerToMove = PLAYER1;
    
    GameState(int a_boardSize) {
        boardSize = a_boardSize;
        board = new int[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++)
                board[i][j] = EMPTY;
        // set up the initial position
        board[boardSize/2-1][boardSize/2-1] = PLAYER1;
        board[boardSize/2][boardSize/2] = PLAYER1;
        board[boardSize/2-1][boardSize/2] = PLAYER2;
        board[boardSize/2][boardSize/2-1] = PLAYER2;

    }
    
    // Creates a copy of the game state
    public GameState clone() {
        GameState newState = new GameState(boardSize);
        for(int i = 0;i<boardSize;i++)
            for(int j = 0;j<boardSize;j++)
                newState.board[i][j] = board[i][j];
        newState.nextPlayerToMove = nextPlayerToMove;
        return newState;
    }
    
    // Returns the list of possible moves for the next player
    public List<Move> generateMoves() {
        return generateMoves(nextPlayerToMove);
    }
    
    // Returns the list of possible moves for a given player
    public List<Move> generateMoves(int player) {
        List<Move> moves = new LinkedList<Move>();

        // these two arrays encode the 8 possible directions in which a player can capture pieces:
        int offs_x[] = { 0, 1, 1, 1, 0,-1,-1,-1};
        int offs_y[] = {-1,-1, 0, 1, 1, 1, 0,-1};

        for(int i = 0;i<boardSize;i++) {
            for(int j = 0;j<boardSize;j++) {
                if (board[i][j]==EMPTY) {
                    boolean moveFound = false;
                    for(int k = 0;k<offs_x.length && !moveFound;k++) {
                        int current_x = i + offs_x[k];
                        int current_y = j + offs_y[k];
                        while(current_x+offs_x[k]>=0 && current_x+offs_x[k]<boardSize &&
                              current_y+offs_y[k]>=0 && current_y+offs_y[k]<boardSize &&
                              board[current_x][current_y] == otherPlayer(player)) {
                            current_x += offs_x[k];
                            current_y += offs_y[k];
                            if (board[current_x][current_y] == player) {
                                // Legal move:
                                moveFound = true;
                                moves.add(new Move(player, i, j));
                                break;
                            }
                        }
                    }
                }
            }
        }
                
        return moves;
	}
    
	private void applyMove(Move move) {
        nextPlayerToMove = otherPlayer(nextPlayerToMove);
        
        if (move==null) return; // player passes
        
        // set the piece:
        board[move.x][move.y] = move.player;
        
        // these two arrays encode the 8 possible directions in which a player can capture pieces:
        int offs_x[] = { 0, 1, 1, 1, 0,-1,-1,-1};
        int offs_y[] = {-1,-1, 0, 1, 1, 1, 0,-1};
        
        // see if any pieces are captured:
        for(int i = 0;i<offs_x.length;i++) {
            int current_x = move.x + offs_x[i];
            int current_y = move.y + offs_y[i];
            while(current_x+offs_x[i]>=0 && current_x+offs_x[i]<boardSize &&
                  current_y+offs_y[i]>=0 && current_y+offs_y[i]<boardSize &&
                  board[current_x][current_y] == otherPlayer(move.player)) {
                current_x += offs_x[i];
                current_y += offs_y[i];
                if (board[current_x][current_y] == move.player) {
                    // pieces captured!:
                    int reversed_x = move.x + offs_x[i];
                    int reversed_y = move.y + offs_y[i];
                    while(reversed_x!=current_x || reversed_y!=current_y) {
                        board[reversed_x][reversed_y] = move.player;
                        reversed_x += offs_x[i];
                        reversed_y += offs_y[i];
                    }
                    break;
                }
            }
        }
	}

	// Returns the final score
	public int finalScore() {
        int score = 0;
        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++) {
                if (board[i][j] == PLAYER1) score++;
                if (board[i][j] == PLAYER2) score--;
            }
        return score;
    }
    
    public boolean gameOver() {
        if (generateMoves(PLAYER1).isEmpty() && generateMoves(PLAYER2).isEmpty()) return true;
        return false;
    }
    
    public int otherPlayer(int player) {
        if (player==PLAYER1) return PLAYER2;
        return PLAYER1;
    }
    
    public GameState applyMoveAndClone(Move move) {
        GameState newState = clone();
        newState.applyMove(move);
        return newState;
    }
    
    public String toString() {
        StringBuffer output = new StringBuffer();
        for(int j = 0;j<boardSize;j++) {
            for(int i = 0;i<boardSize;i++) {
                if (board[i][j]==PLAYER1) output.append("O");
                else if (board[i][j]==PLAYER2) output.append("X");
                else output.append(".");
            }
            output.append("\n");
        }
        return output.toString();
    }
}
