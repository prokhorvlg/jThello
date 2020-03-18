package jThello;

import java.util.List;

public class AIPlayer extends Player {
	private int depth;
	
	AIPlayer(int d) {
		this.depth = d;
	}
	
	public MovePair minimax(GameState state, Move move, boolean isMax, int currDepth) {
		MovePair result = null;
		List<Move> moves = state.allMoves();
		if ((moves.size() == 0) || (currDepth == this.depth) || (state.gameOver())) {
			return new MovePair(move,state.finalScore());
		}
		if (isMax) {
			result = new MovePair(null,Integer.MIN_VALUE);
			for (Move m: moves) {
				MovePair childVal = minimax(state.applyMoveAndClone(m),m,false,currDepth+1);
				if (childVal.getScore() > result.getScore()) result = new MovePair(m,childVal.getScore());
			}
		}
		else {
			result = new MovePair(null,Integer.MAX_VALUE);
			for (Move m: moves) {
				MovePair childVal = minimax(state.applyMoveAndClone(m),m,true,currDepth+1);
				if (childVal.getScore() < result.getScore()) result = new MovePair(m,childVal.getScore());
			}			
		}
		return result;
		
	}
	
	@Override
	public Move getMove(GameState state) {
		boolean maxi = (state.nextPlayerToMove == GameState.PLAYER1);
		return minimax(state,null,maxi,0).getMove();
	}

}
