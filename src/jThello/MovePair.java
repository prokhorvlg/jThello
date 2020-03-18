package jThello;

public class MovePair {
	private Move move;
	private int score;
	
	MovePair(Move m, int s) {
		move=m;
		score=s;
	}
	
	public Move getMove() {return move;}
	public int getScore() {return score;}
}
