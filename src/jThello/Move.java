package jThello;

public class Move {

	int player;
	int row,col;
	
    public Move(int _player, int r, int c) {
        player = _player;
        row = r;
        col = c;
    }
    
    @Override
    public String toString() {
    	return ""+ player + ": (" + row + ", " + col + ")";
    }
    
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Move) {
			Move other = (Move) obj;
			if (player != other.player) return false;
			if (row != other.row) return false;
			if (col != other.col) return false;
			return true;
		}
		return false;
	}
}
