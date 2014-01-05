package monsterEatPhoto;

public class Pixel{

	private int X;
	private int Y;
	
	public int getX() {
		return X;
	}
	
	public int getY() {
		return Y;
	}
	
	public Pixel(int x, int y) {
		X = x;
		Y = y;
	}
	
	@Override
	public int hashCode() {
		return X+Y;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pixel tmp = (Pixel) obj;
		return this.X == tmp.X && this.Y == tmp.Y;
	}
	
}

