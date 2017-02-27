package experiment;

public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell(){
		row=0;
		column=0;
	}
	public BoardCell(int x, int y){
		row=y;
		column=x;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
}
