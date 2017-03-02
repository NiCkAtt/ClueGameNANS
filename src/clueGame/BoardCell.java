package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDir = null;
	
	public BoardCell(){
		row=0;
		column=0;
	}
	public BoardCell(int x, int y, char c){
		row=y;
		column=x;
		initial = c;
	}
	
	public boolean isWalkway(){
		return this.initial == 'W';
	}
	
	public void makeDoor(char dir){
		if(dir == 'D'){
			this.doorDir = DoorDirection.DOWN;
		}
		if(dir == 'U'){
			this.doorDir = DoorDirection.UP;
		}
		if(dir == 'L'){
			this.doorDir = DoorDirection.LEFT;
		}
		if(dir == 'R'){
			this.doorDir = DoorDirection.RIGHT;
		}
	}
	
	public boolean isDoorway(){
		return !(this.doorDir == null);
	}

	public boolean isRoom(){
		return false;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	public DoorDirection getDoorDirection() {
		return doorDir;
	}
	public char getInitial() {
		return initial;
	}
}
