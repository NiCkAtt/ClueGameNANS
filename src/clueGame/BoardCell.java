package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	
	public BoardCell(){
		row=0;
		column=0;
	}
	public BoardCell(int x, int y){
		row=y;
		column=x;
	}
	
	public boolean isWalkway(){
		return false;
	}
	
	public boolean isDoorway(){
		return false;
	}

	public boolean isRoom(){
		return false;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	public DoorDirection getDoorDirection() {
		return null;
	}
	public char getInitial() {
		return ' ';
	}
}
