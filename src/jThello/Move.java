package jThello;

public class Move {

	int player;
	int x,y;
	
    public Move(int _player, int _x, int _y) {
        player = _player;
        x = _x;
        y = _y;
    }
    
    @Override
    public String toString() {
    	return ""+ player + ": (" + x + ", " + y + ")";
    }
    
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Move) {
			Move other = (Move) obj;
			if (player != other.player) return false;
			if (x != other.x) return false;
			if (y != other.y) return false;
			return true;
		}
		return false;
	}
}
