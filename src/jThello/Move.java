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
    	String[] coordsX = {"A", "B", "C", "D", "E", "F", "G", "H"};
		String[] coordsY = {"1", "2", "3", "4", "5", "6", "7", "8"};
    	return "(" + coordsX[row] + ", " + coordsY[col] + ")";
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
